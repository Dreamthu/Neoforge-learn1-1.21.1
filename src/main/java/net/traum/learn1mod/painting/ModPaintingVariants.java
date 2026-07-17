package net.traum.learn1mod.painting;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.traum.learn1mod.Learn1Mod;

public class ModPaintingVariants {
    public static final ResourceKey<PaintingVariant> SAW_THEM = createKey("saw_them");
    public static final ResourceKey<PaintingVariant> SHRIMP = createKey("shrimp");
    public static final ResourceKey<PaintingVariant> WORLD = createKey("world");

    public static void bootstrap(BootstrapContext<PaintingVariant> context) {
        register(context, SAW_THEM, 2, 2);
        register(context, SHRIMP, 2, 1);
        register(context, WORLD, 2, 2);
    }

    private static void register(BootstrapContext<PaintingVariant> ctx, ResourceKey<PaintingVariant> key,
                                  int width, int height) {
        ctx.register(key, new PaintingVariant(width, height, key.location()));
    }

    private static ResourceKey<PaintingVariant> createKey(String name) {
        return ResourceKey.create(Registries.PAINTING_VARIANT,
                ResourceLocation.fromNamespaceAndPath(Learn1Mod.MOD_ID, name));
    }
}
