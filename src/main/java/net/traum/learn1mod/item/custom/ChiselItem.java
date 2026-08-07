package net.traum.learn1mod.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.traum.learn1mod.block.ModBlocks;
import net.traum.learn1mod.component.ModDatacomponents;
import net.traum.learn1mod.particle.ModParticles;
import net.traum.learn1mod.sound.ModSounds;

import java.util.List;
import java.util.Map;

public class ChiselItem extends Item {
    private static final Map<Block, Block> CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.GOLD_BLOCK,
                    Blocks.DIRT, ModBlocks.BISMUTH_BLOCK.get()
            );

    public ChiselItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();

        if(CHISEL_MAP.containsKey(clickedBlock)) {
            if(!level.isClientSide()) {
                level.setBlockAndUpdate(context.getClickedPos(), CHISEL_MAP.get(clickedBlock).defaultBlockState());

                context.getItemInHand().hurtAndBreak(1, ((ServerLevel) level),context.getPlayer(),
                        item -> context.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

                level.playSound(null, context.getClickedPos(), ModSounds.CHISEL_USE.get(), SoundSource.BLOCKS);

                ((ServerLevel) level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, clickedBlock.defaultBlockState()),
                        context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1.0,
                        context.getClickedPos().getZ() + 0.5, 5, 0, 0, 0, 1);

                ((ServerLevel) level).sendParticles(ParticleTypes.FIREWORK,
                        context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1.5,
                        context.getClickedPos().getZ() + 0.5, 8, 0, 0, 0, 2);

                ((ServerLevel) level).sendParticles(ModParticles.BISMUTH_PARTICLES.get(),
                        context.getClickedPos().getX() + 0.5, context.getClickedPos().getY() + 1.0,
                        context.getClickedPos().getZ() + 0.5, 8, 0, 0, 0, 2);


                context.getItemInHand().set(ModDatacomponents.COORDINATES, context.getClickedPos());
            }
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.learn1mod.chisel.shift_down"));
        }
        else {
            tooltipComponents.add(Component.translatable("tooltip.learn1mod.chisel"));
        }

        if (stack.get(ModDatacomponents.COORDINATES) != null) {
            tooltipComponents.add(Component.literal("Last Block changed at " + stack.get(ModDatacomponents.COORDINATES)));
        }

        super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
    }
}
