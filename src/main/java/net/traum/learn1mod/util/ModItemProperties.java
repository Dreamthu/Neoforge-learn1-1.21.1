package net.traum.learn1mod.util;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.MossBlock;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.component.ModDatacomponents;
import net.traum.learn1mod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.CHISEL.get(), ResourceLocation.fromNamespaceAndPath(Learn1Mod.MOD_ID, "used"),
                (stack, level, entity, seed) -> stack.get(ModDatacomponents.COORDINATES) != null ? 1f : 0f);

        makeCustomBow(ModItems.KAUPEN_BOW.get());
    }

    private static void makeCustomBow(Item item) {
        ItemProperties.register(item, ResourceLocation.withDefaultNamespace("pull"), (ClampedItemPropertyFunction)((p_344163_, p_344164_, p_344165_, p_344166_) -> {
            if (p_344165_ == null) {
                return 0.0F;
            } else {
                return p_344165_.getUseItem() != p_344163_ ? 0.0F : (float)(p_344163_.getUseDuration(p_344165_) - p_344165_.getUseItemRemainingTicks()) / 20.0F;
            }
        }));

        ItemProperties.register(item,
                ResourceLocation.withDefaultNamespace("pulling"),
                (ClampedItemPropertyFunction)((p_174630_, p_174631_, p_174632_, p_174633_) -> p_174632_ != null && p_174632_.isUsingItem() && p_174632_.getUseItem() == p_174630_ ? 1.0F : 0.0F));

    }
}
