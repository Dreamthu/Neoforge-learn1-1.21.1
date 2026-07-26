package net.traum.learn1mod.worldgen.tree;

import net.minecraft.world.level.block.grower.TreeGrower;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.worldgen.ModConfiguredFeatures;

import java.util.Optional;

public class ModTreeGrowers {
    public static final TreeGrower BLOODWOOD = new TreeGrower(Learn1Mod.MOD_ID + ":bloodwood",
            Optional.empty(), Optional.of(ModConfiguredFeatures.BLOODWOOD_KEY), Optional.empty());

}
