package net.traum.learn1mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.block.ModBlocks;
import net.traum.learn1mod.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Learn1Mod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.BISMUTH_BLOCK.get())
                .add(ModBlocks.BISMUTH_STAIRS.get())
                .add(ModBlocks.BISMUTH_SLAB.get())
                .add(ModBlocks.BISMUTH_PRESSURE_PLATE.get())
                .add(ModBlocks.BISMUTH_BUTTON.get())
                .add(ModBlocks.BISMUTH_FENCE.get())
                .add(ModBlocks.BISMUTH_FENCE_GATE.get())
                .add(ModBlocks.BISMUTH_WALL.get())
                .add(ModBlocks.BISMUTH_DOOR.get())
                .add(ModBlocks.BISMUTH_TRAPDOOR.get())
                .add(ModBlocks.MAGIC_BLOCK.get())
                .add(ModBlocks.BISMUTH_LAMP.get());

        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.BISMUTH_BLOCK.get())
                .add(ModBlocks.BISMUTH_STAIRS.get())
                .add(ModBlocks.BISMUTH_SLAB.get())
                .add(ModBlocks.BISMUTH_PRESSURE_PLATE.get())
                .add(ModBlocks.BISMUTH_BUTTON.get())
                .add(ModBlocks.BISMUTH_FENCE.get())
                .add(ModBlocks.BISMUTH_FENCE_GATE.get())
                .add(ModBlocks.BISMUTH_WALL.get())
                .add(ModBlocks.BISMUTH_DOOR.get())
                .add(ModBlocks.BISMUTH_TRAPDOOR.get());

        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.MAGIC_BLOCK.get())
                .add(ModBlocks.BISMUTH_LAMP.get());

        tag(BlockTags.WALLS).add(ModBlocks.BISMUTH_WALL.get());
        tag(BlockTags.FENCES).add(ModBlocks.BISMUTH_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.BISMUTH_FENCE_GATE.get());

        tag(ModTags.Blocks.NEEDS_BISMUTH_TOOL)
                .addTag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.BISMUTH_LAMP.get());

        tag(ModTags.Blocks.INCORRECT_FOR_BISMUTH_TOOL)
                .addTag(BlockTags.INCORRECT_FOR_IRON_TOOL)
                .remove(ModTags.Blocks.NEEDS_BISMUTH_TOOL);
    }
}
