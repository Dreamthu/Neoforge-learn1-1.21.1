package net.traum.learn1mod.event;


import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import net.neoforged.neoforge.event.village.VillagerTradesEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.enchantment.ModEnchantments;
import net.traum.learn1mod.item.ModItems;
import net.traum.learn1mod.item.custom.HammerItem;
import net.traum.learn1mod.potion.ModPotions;
import net.traum.learn1mod.villager.ModVillagers;

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
    public static void onLightningStrikerDamage(LivingDamageEvent.Post event) {
        if (!(event.getEntity().level() instanceof ServerLevel serverLevel)) {
            return;
        }

        ItemStack weaponStack = getLightningStrikerWeapon(event);
        if (weaponStack == null || weaponStack.isEmpty()) {
            return;
        }

        int enchantmentLevel = weaponStack.getEnchantments().getLevel(
                serverLevel.registryAccess().holderOrThrow(ModEnchantments.LIGHTNING_STRIKER));
        for (int i = 0; i < enchantmentLevel; i++) {
            EntityType.LIGHTNING_BOLT.spawn(serverLevel, event.getEntity().getOnPos(), MobSpawnType.TRIGGERED);
        }
    }

    private static ItemStack getLightningStrikerWeapon(LivingDamageEvent.Post event) {
        Entity directEntity = event.getSource().getDirectEntity();

        if (directEntity instanceof AbstractArrow arrow) {
            return arrow.getWeaponItem();
        }

        if (event.getSource().isDirect() && event.getSource().getEntity() instanceof LivingEntity attacker) {
            ItemStack weaponStack = attacker.getWeaponItem();
            return weaponStack.getItem() instanceof ProjectileWeaponItem ? ItemStack.EMPTY : weaponStack;
        }

        return ItemStack.EMPTY;
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

    @SubscribeEvent
    public static void addCustomTrades(VillagerTradesEvent event) {
        if(event.getType() == ModVillagers.KAUPENGER.value()) {
            Int2ObjectMap<List<VillagerTrades.ItemListing>> trades = event.getTrades();

            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.EMERALD, 3),
                    new ItemStack(ModItems.GOJI_BERRIES.get(), 18), 6, 3, 0.05f));

            trades.get(1).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.DIAMOND, 12),
                    new ItemStack(ModItems.RADISH.get(), 1), 6, 3, 0.05f));

            trades.get(2).add((entity, randomSource) -> new MerchantOffer(
                    new ItemCost(Items.ENDER_PEARL, 1),
                    new ItemStack(ModItems.RADISH_SEEDS.get(), 1), 2, 8, 0.05f));
        }
    }

    @SubscribeEvent
    public static void addWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        genericTrades.add((entity, randomSource) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 16),
                new ItemStack(ModItems.KAUPEN_SMITHING_TEMPLATE.get(), 1), 1, 10, 0.2f));

        rareTrades.add((entity, randomSource) -> new MerchantOffer(
                new ItemCost(Items.NETHERITE_INGOT, 1),
                new ItemStack(ModItems.BAR_BRAWL_MUSIC_DISC.get(), 1), 1, 10, 0.2f));
    }
}
