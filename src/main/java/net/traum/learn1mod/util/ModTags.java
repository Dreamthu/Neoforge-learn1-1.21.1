package net.traum.learn1mod.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.traum.learn1mod.Learn1Mod;

public class ModTags {
    public static class Blocks {
        public static final TagKey<Block> NEEDS_BISMUTH_TOOL = createTag("needs_bismuth_tool");
        public static final TagKey<Block> INCORRECT_FOR_BISMUTH_TOOL = createTag("incorrect_for_bismuth_tool");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(Learn1Mod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> TRANSFORMABLE_ITEMS = createTag("transformable_items");
        public static final TagKey<Item> HORSE_ARMOR_ENCHANTABLE = createTag("enchantable/horse_armor");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(Learn1Mod.MOD_ID, name));
        }
    }
}
