package net.traum.learn1mod.datagen;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Learn1Mod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(ModItems.BISMUTH.get());
        basicItem(ModItems.CHISEL.get());
        basicItem(ModItems.RADISH.get());
        basicItem(ModItems.STARLIGHT_ASHES.get());
        basicItem(ModItems.FROSTFIRE_ICE.get());

        handheldItem(ModItems.BISMUTH_SWORD.get());
        handheldItem(ModItems.BISMUTH_PICKAXE.get());
        handheldItem(ModItems.BISMUTH_AXE.get());
        handheldItem(ModItems.BISMUTH_SHOVEL.get());
        handheldItem(ModItems.BISMUTH_HOE.get());
    }
}
