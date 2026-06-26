package net.traum.learn1mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DataMapProvider;
import net.neoforged.neoforge.registries.datamaps.builtin.Compostable;
import net.neoforged.neoforge.registries.datamaps.builtin.FurnaceFuel;
import net.neoforged.neoforge.registries.datamaps.builtin.NeoForgeDataMaps;
import net.traum.learn1mod.item.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModDataMapProvider extends DataMapProvider {
    protected ModDataMapProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    protected void gather(HolderLookup.Provider provider) {
        this.builder(NeoForgeDataMaps.FURNACE_FUELS)
                .add(ModItems.FROSTFIRE_ICE.getId(), new FurnaceFuel(1200), false)
                .add(ModItems.STARLIGHT_ASHES.getId(), new FurnaceFuel(2400), false);

        this.builder(NeoForgeDataMaps.COMPOSTABLES)
                .add(ModItems.RADISH.getId(), new Compostable(0.65F), false);
    }
}
