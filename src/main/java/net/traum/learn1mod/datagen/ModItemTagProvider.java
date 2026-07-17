package net.traum.learn1mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.item.ModItems;
import net.traum.learn1mod.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagsProvider.TagLookup<Block>> blockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, Learn1Mod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Items.TRANSFORMABLE_ITEMS)
                .add(ModItems.BISMUTH.get())
                .add(Items.COAL);

        tag(ModTags.Items.HORSE_ARMOR_ENCHANTABLE)
                .add(ModItems.BISMUTH_HORSE_ARMOR.get())
                .add(Items.LEATHER_HORSE_ARMOR)
                .add(Items.IRON_HORSE_ARMOR)
                .add(Items.GOLDEN_HORSE_ARMOR)
                .add(Items.DIAMOND_HORSE_ARMOR);

        tag(ModTags.Items.LIGHTNING_STRIKER_ENCHANTABLE)
                .addTag(ItemTags. SHARP_WEAPON_ENCHANTABLE)
                .add(ModItems.BISMUTH_HAMMER.get());

        tag(ItemTags.SWORDS).add(ModItems.BISMUTH_SWORD.get());
        tag(ItemTags.PICKAXES).add(ModItems.BISMUTH_PICKAXE.get());
        tag(ItemTags.AXES).add(ModItems.BISMUTH_AXE.get());
        tag(ItemTags.SHOVELS).add(ModItems.BISMUTH_SHOVEL.get());
        tag(ItemTags.HOES).add(ModItems.BISMUTH_HOE.get());

        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.BISMUTH_HELMET.get())
                .add(ModItems.BISMUTH_CHESTPLATE.get())
                .add(ModItems.BISMUTH_LEGGINGS.get())
                .add(ModItems.BISMUTH_BOOTS.get());

        this.tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.BISMUTH.get());

        this.tag(ItemTags.TRIM_TEMPLATES)
                .add(ModItems.KAUPEN_SMITHING_TEMPLATE.get());
    }
}
