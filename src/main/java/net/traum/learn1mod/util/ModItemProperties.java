package net.traum.learn1mod.util;

import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.component.ModDatacomponents;
import net.traum.learn1mod.item.ModItems;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;

public class ModItemProperties {
    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.CHISEL.get(), ResourceLocation.fromNamespaceAndPath(Learn1Mod.MOD_ID, "used"),
                (stack, level, entity, seed) -> stack.get(ModDatacomponents.COORDINATES) != null ? 1f : 0f);

    }
}
