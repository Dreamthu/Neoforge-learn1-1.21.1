package net.traum.learn1mod.item;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.block.ModBlocks;
import net.traum.learn1mod.block.custom.MagicBlock;

import java.util.function.Supplier;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Learn1Mod.MOD_ID);

    public static final Supplier<CreativeModeTab> BISMUTH_ITEMS_TAB = CREATIVE_MODE_TAB.register(
            "bismuth_items_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModItems.BISMUTH.get()))
                    .title(Component.translatable("creativetab.learn1mod.bismuth_items"))
                    .displayItems((params, output) -> {
                        output.accept(ModItems.BISMUTH);
                        output.accept(ModItems.CHISEL);
                        output.accept(ModItems.RADISH);
                        output.accept(ModItems.FROSTFIRE_ICE);
                        output.accept(ModItems.STARLIGHT_ASHES);
                    }).build());

    public static final Supplier<CreativeModeTab> BISMUTH_BLOCKS_TAB = CREATIVE_MODE_TAB.register(
            "bismuth_blocks_tab", () -> CreativeModeTab.builder()
                    .icon(() -> new ItemStack(ModBlocks.BISMUTH_BLOCK.get()))
                    .withTabsBefore(ResourceLocation.fromNamespaceAndPath(Learn1Mod.MOD_ID, "bismuth_items_tab"))
                    .title(Component.translatable("creativetab.learn1mod.bismuth_blocks"))
                    .displayItems((params, output) -> {
                        output.accept(ModBlocks.BISMUTH_BLOCK);
                        output.accept(ModBlocks.MAGIC_BLOCK);
                    }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
