package net.traum.learn1mod.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.block.ModBlocks;
import net.traum.learn1mod.item.ModItems;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {

    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        List<ItemLike> BISMUTH_SMELTABLES = List.of(ModItems.RADISH);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.BISMUTH_BLOCK.get())
                .pattern("BBB")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.BISMUTH.get())
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BISMUTH.get(), 9)
                .requires(ModBlocks.BISMUTH_BLOCK)
                .unlockedBy("has_bismuth_block", has(ModBlocks.BISMUTH_BLOCK)).save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BISMUTH.get(), 18)
                .requires(ModBlocks.MAGIC_BLOCK)
                .unlockedBy("has_magic_block", has(ModBlocks.MAGIC_BLOCK))
                .save(recipeOutput, "learn1mod:bismuth_from_magic_block");

        oreSmelting(recipeOutput, BISMUTH_SMELTABLES, RecipeCategory.MISC, ModItems.BISMUTH.get(), 0.25F, 200, "bismuth");
        oreBlasting(recipeOutput, BISMUTH_SMELTABLES, RecipeCategory.MISC, ModItems.BISMUTH.get(), 0.25F, 100, "bismuth");

        var blockIngredient = Ingredient.of(ModBlocks.BISMUTH_BLOCK.get());
        var bismuthIngredient = Ingredient.of(ModItems.BISMUTH.get());

        stairBuilder(ModBlocks.BISMUTH_STAIRS.get(), blockIngredient)
                .unlockedBy("has_bismuth_block", has(ModBlocks.BISMUTH_BLOCK)).save(recipeOutput);
        slab(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_SLAB.get(), ModBlocks.BISMUTH_BLOCK.get());
        wall(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_WALL.get(), ModBlocks.BISMUTH_BLOCK.get());

        pressurePlate(recipeOutput, ModBlocks.BISMUTH_PRESSURE_PLATE.get(), ModItems.BISMUTH.get());
        buttonBuilder(ModBlocks.BISMUTH_BUTTON.get(), bismuthIngredient)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);

        fenceBuilder(ModBlocks.BISMUTH_FENCE.get(), bismuthIngredient)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);
        fenceGateBuilder(ModBlocks.BISMUTH_FENCE_GATE.get(), bismuthIngredient)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);

        doorBuilder(ModBlocks.BISMUTH_DOOR.get(), bismuthIngredient)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);
        trapdoorBuilder(ModBlocks.BISMUTH_TRAPDOOR.get(), bismuthIngredient)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BISMUTH_SWORD.get())
                .pattern("B")
                .pattern("B")
                .pattern("S")
                .define('B', ModItems.BISMUTH.get())
                .define('S', Items.STICK)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BISMUTH_PICKAXE.get())
                .pattern("BBB")
                .pattern(" S ")
                .pattern(" S ")
                .define('B', ModItems.BISMUTH.get())
                .define('S', Items.STICK)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BISMUTH_AXE.get())
                .pattern("BB")
                .pattern("BS")
                .pattern(" S")
                .define('B', ModItems.BISMUTH.get())
                .define('S', Items.STICK)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BISMUTH_SHOVEL.get())
                .pattern("B")
                .pattern("S")
                .pattern("S")
                .define('B', ModItems.BISMUTH.get())
                .define('S', Items.STICK)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BISMUTH_HOE.get())
                .pattern("BB")
                .pattern(" S")
                .pattern(" S")
                .define('B', ModItems.BISMUTH.get())
                .define('S', Items.STICK)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BISMUTH_HELMET.get())
                .pattern("BBB")
                .pattern("B B")
                .define('B', ModItems.BISMUTH.get())
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BISMUTH_CHESTPLATE.get())
                .pattern("B B")
                .pattern("BBB")
                .pattern("BBB")
                .define('B', ModItems.BISMUTH.get())
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BISMUTH_LEGGINGS.get())
                .pattern("BBB")
                .pattern("B B")
                .pattern("B B")
                .define('B', ModItems.BISMUTH.get())
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);
        ShapedRecipeBuilder.shaped(RecipeCategory.COMBAT, ModItems.BISMUTH_BOOTS.get())
                .pattern("B B")
                .pattern("B B")
                .define('B', ModItems.BISMUTH.get())
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.TOOLS, ModItems.BISMUTH_HAMMER.get())
                .pattern(" B ")
                .pattern(" SB")
                .pattern("S  ")
                .define('B', ModBlocks.BISMUTH_BLOCK.get())
                .define('S', Items.STICK)
                .unlockedBy("has_bismuth", has(ModItems.BISMUTH)).save(recipeOutput);

        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_STAIRS.get(), ModBlocks.BISMUTH_BLOCK.get());
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_SLAB.get(), ModBlocks.BISMUTH_BLOCK.get(), 2);
        stonecutterResultFromBase(recipeOutput, RecipeCategory.BUILDING_BLOCKS, ModBlocks.BISMUTH_WALL.get(), ModBlocks.BISMUTH_BLOCK.get());
    }

    protected static void oreSmelting(RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(recipeOutput, RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_smelting");
    }

    protected static void oreBlasting(RecipeOutput recipeOutput, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group) {
        oreCooking(recipeOutput, RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, ingredients, category, result, experience, cookingTime, group, "_from_blasting");
    }

    protected static <T extends AbstractCookingRecipe> void oreCooking(RecipeOutput recipeOutput, RecipeSerializer<T> serializer, AbstractCookingRecipe.Factory<T> factory, List<ItemLike> ingredients, RecipeCategory category, ItemLike result, float experience, int cookingTime, String group, String suffix) {
        for (ItemLike ingredient : ingredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(ingredient), category, result, experience, cookingTime, serializer, factory)
                    .group(group)
                    .unlockedBy(getHasName(ingredient), has(ingredient))
                    .save(recipeOutput, ResourceLocation.fromNamespaceAndPath(Learn1Mod.MOD_ID,
                            getItemName(result) + suffix + "_" + getItemName(ingredient)));
        }
    }
}
