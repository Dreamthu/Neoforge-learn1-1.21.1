package net.traum.learn1mod.event;


import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.enchantment.ModEnchantments;
import net.traum.learn1mod.item.custom.HammerItem;
import net.traum.learn1mod.potion.ModPotions;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = Learn1Mod.MOD_ID)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
            }
            HARVESTED_BLOCKS.clear();
        }
    }

    @SubscribeEvent
    public static void livingDamage(LivingDamageEvent.Pre event) {
        if (event.getEntity() instanceof Sheep sheep && event.getSource().getDirectEntity() instanceof Player player) {
            if (player.getMainHandItem().getItem() == Items.END_ROD) {
                player.sendSystemMessage(Component.literal(player.getName().getString() +
                        "just hit a sheep with an END ROD? YOU SICK FRICK!"));
                sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 0));
                player.getMainHandItem().shrink(1);
                List.of(
                        new MobEffectInstance(MobEffects.WEAKNESS, 600, 1),
                        new MobEffectInstance(MobEffects.BLINDNESS, 200, 0),
                        new MobEffectInstance(MobEffects.CONFUSION, 300, 0)
                ).forEach(player::addEffect);
            }
        }
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(RegisterBrewingRecipesEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.SLIME_BALL, ModPotions.SLIMEY_POTION);
    }

    @SubscribeEvent
    public static void onEntityTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof LivingEntity entity
                && !(entity instanceof Player)
                && entity.onGround()
                && entity.level() instanceof ServerLevel serverLevel) {
            ItemStack bodyArmor = entity.getItemBySlot(EquipmentSlot.BODY);
            int level = bodyArmor.getEnchantments().getLevel(
                    serverLevel.registryAccess().holderOrThrow(ModEnchantments.FROST_HOOVES));
            if (level > 0) {
                freezeWaterAround(entity, serverLevel, level);
            }
        }
    }

    private static void freezeWaterAround(LivingEntity entity, ServerLevel level, int enchantmentLevel) {
        BlockState frostedIce = Blocks.FROSTED_ICE.defaultBlockState();
        BlockPos center = entity.blockPosition();
        int radius = Math.min(16, 2 + enchantmentLevel);

        for (BlockPos pos : BlockPos.betweenClosed(center.offset(-radius, -1, -radius),
                center.offset(radius, -1, radius))) {
            double xDistance = pos.getX() + 0.5D - entity.getX();
            double zDistance = pos.getZ() + 0.5D - entity.getZ();
            if (xDistance * xDistance + zDistance * zDistance > radius * radius) {
                continue;
            }

            if (level.getBlockState(pos.above()).isAir()
                    && level.getBlockState(pos).is(Blocks.WATER)
                    && frostedIce.canSurvive(level, pos)
                    && level.isUnobstructed(frostedIce, pos, CollisionContext.empty())) {
                level.setBlockAndUpdate(pos, frostedIce);
                level.scheduleTick(pos, Blocks.FROSTED_ICE, Mth.nextInt(entity.getRandom(), 60, 120));
            }
        }
    }

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        PotionContents contents = stack.get(DataComponents.POTION_CONTENTS);
        if (contents != null && contents.is(ModPotions.SLIMEY_POTION)) {
            event.getToolTip().add(
                    Component.translatable("tooltip.learn1mod.slimey_potion")
                            .withStyle(ChatFormatting.DARK_GREEN));
        }
    }
}
