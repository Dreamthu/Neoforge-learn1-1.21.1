package net.traum.learn1mod.datagen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.block.ModBlocks;
import net.traum.learn1mod.block.custom.BismuthLampBlock;
import net.traum.learn1mod.block.custom.GojiBerryBushBlock;
import net.traum.learn1mod.block.custom.RadishCropBlock;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Learn1Mod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        var texture = blockTexture(ModBlocks.BISMUTH_BLOCK.get());

        blockWithItem(ModBlocks.BISMUTH_BLOCK);
        blockWithItem(ModBlocks.BISMUTH_ORE);
        blockWithItem(ModBlocks.BISMUTH_DEEPSLATE_ORE);
        blockWithItem(ModBlocks.BISMUTH_END_ORE);
        blockWithItem(ModBlocks.BISMUTH_NETHER_ORE);
        blockWithItem(ModBlocks.MAGIC_BLOCK);

        stairsBlock(ModBlocks.BISMUTH_STAIRS.get(), texture);
        slabBlock(ModBlocks.BISMUTH_SLAB.get(), texture, texture);
        pressurePlateBlock(ModBlocks.BISMUTH_PRESSURE_PLATE.get(), texture);
        buttonBlock(ModBlocks.BISMUTH_BUTTON.get(), texture);
        models().withExistingParent("block/bismuth_button_inventory", mcLoc("block/button_inventory"))
                .texture("texture", texture);
        fenceBlock(ModBlocks.BISMUTH_FENCE.get(), texture);
        models().withExistingParent("block/bismuth_fence_inventory", mcLoc("block/fence_inventory"))
                .texture("texture", texture);
        fenceGateBlock(ModBlocks.BISMUTH_FENCE_GATE.get(), texture);
        wallBlock(ModBlocks.BISMUTH_WALL.get(), texture);
        models().withExistingParent("block/bismuth_wall_inventory", mcLoc("block/wall_inventory"))
                .texture("wall", texture);
        doorBlockWithRenderType(ModBlocks.BISMUTH_DOOR.get(), modLoc("block/bismuth_door_bottom"), modLoc("block/bismuth_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.BISMUTH_TRAPDOOR.get(), modLoc("block/bismuth_trapdoor"), true, "cutout");
        var lampOff = modLoc("block/bismuth_lamp_off");
        var lampOn = modLoc("block/bismuth_lamp_on");
        customLamp(ModBlocks.BISMUTH_LAMP, BismuthLampBlock.CLICKED, lampOff, lampOn);

        blockItem(ModBlocks.BISMUTH_STAIRS);
        blockItem(ModBlocks.BISMUTH_SLAB);
        blockItem(ModBlocks.BISMUTH_PRESSURE_PLATE);
        blockItem(ModBlocks.BISMUTH_BUTTON, "bismuth_button_inventory");
        blockItem(ModBlocks.BISMUTH_FENCE, "bismuth_fence_inventory");
        blockItem(ModBlocks.BISMUTH_FENCE_GATE);
        blockItem(ModBlocks.BISMUTH_WALL, "bismuth_wall_inventory");
        itemModels().basicItem(ModBlocks.BISMUTH_DOOR.getId());
        blockItem(ModBlocks.BISMUTH_TRAPDOOR, "bismuth_trapdoor_bottom");

        makeCrop((CropBlock) ModBlocks.RADISH_CROP.get(), RadishCropBlock.AGE, "radish_crop_stage", "radish_crop_stage");
        makeBush((SweetBerryBushBlock) ModBlocks.GOJI_BERRY_BUSH.get(), "goji_berry_bush_stage", "goji_berry_bush_stage");

        logBlock(((RotatedPillarBlock) ModBlocks.BLOODWOOD_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.BLOODWOOD_WOOD.get()), blockTexture(ModBlocks.BLOODWOOD_LOG.get()), blockTexture(ModBlocks.BLOODWOOD_LOG.get()));
        logBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_BLOODWOOD_LOG.get()));
        axisBlock(((RotatedPillarBlock) ModBlocks.STRIPPED_BLOODWOOD_WOOD.get()), blockTexture(ModBlocks.STRIPPED_BLOODWOOD_LOG.get()), blockTexture(ModBlocks.STRIPPED_BLOODWOOD_LOG.get()));

        blockItem(ModBlocks.BLOODWOOD_LOG);
        blockItem(ModBlocks.BLOODWOOD_WOOD);
        blockItem(ModBlocks.STRIPPED_BLOODWOOD_LOG);
        blockItem(ModBlocks.STRIPPED_BLOODWOOD_WOOD);

        blockWithItem(ModBlocks.BLOODWOOD_PLANKS);

        leavesBlock(ModBlocks.BLOODWOOD_LEAVES);
        saplingBlock(ModBlocks.BLOODWOOD_SAPLING);
    }

    private void saplingBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void leavesBlock(DeferredBlock<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(BuiltInRegistries.BLOCK.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void makeBush(SweetBerryBushBlock block, String modelName, String textureName) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, modelName, textureName);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, String modelName, String textureName) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(GojiBerryBushBlock.AGE),
                ResourceLocation.fromNamespaceAndPath(Learn1Mod.MOD_ID, "block/" + textureName + state.getValue(GojiBerryBushBlock.AGE))).renderType("cutout"));

        return models;
    }

    public void makeCrop(CropBlock block, IntegerProperty ageProperty, String modelName, String textureName) {
        getVariantBuilder(block).forAllStates(state -> {
            int age = state.getValue(ageProperty);
            return new ConfiguredModel[]{
                    new ConfiguredModel(models().crop(modelName + age,
                            ResourceLocation.fromNamespaceAndPath(Learn1Mod.MOD_ID, "block/" + textureName + age))
                            .renderType("cutout"))
            };
        });
    }

    private void customLamp(DeferredBlock<?> block, BooleanProperty property, ResourceLocation offTexture, ResourceLocation onTexture) {
        String name = block.getId().getPath();
        var offModel = models().cubeAll(name + "_off", offTexture);
        var onModel = models().cubeAll(name + "_on", onTexture);
        getVariantBuilder(block.get())
                .partialState().with(property, false).modelForState().modelFile(offModel).addModel()
                .partialState().with(property, true).modelForState().modelFile(onModel).addModel();
        simpleBlockItem(block.get(), onModel);
    }

    private void blockItem(DeferredBlock<?> deferredBlock) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + deferredBlock.getId().getPath())));
    }

    private void blockItem(DeferredBlock<?> deferredBlock, String appendix) {
        simpleBlockItem(deferredBlock.get(), new ModelFile.UncheckedModelFile(modLoc("block/" + appendix)));
    }

    private void blockWithItem(DeferredBlock<?> deferredBlock) {
        simpleBlockWithItem(deferredBlock.get(), cubeAll(deferredBlock.get()));
    }
}
