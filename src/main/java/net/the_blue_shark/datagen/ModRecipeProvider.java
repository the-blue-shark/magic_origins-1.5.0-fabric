package net.the_blue_shark.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.the_blue_shark.item.ModItems;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BAMBOO_SANDWICH)
                .pattern("A")
                .pattern("B")
                .pattern("A")
                .input('A', Items.BREAD)
                .input('B', Items.BAMBOO)
                .criterion(hasItem(Items.BAMBOO), conditionsFromItem(Items.BAMBOO)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.BAMBOO_GOLDEN_APPLE)
                .input(Items.BAMBOO)
                .input(Items.GOLDEN_APPLE)
                .criterion(hasItem(Items.BAMBOO), conditionsFromItem(Items.BAMBOO)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.ENCHANTED_BAMBOO_GOLDEN_APPLE)
                .input(Items.BAMBOO)
                .input(Items.ENCHANTED_GOLDEN_APPLE)
                .criterion(hasItem(Items.BAMBOO), conditionsFromItem(Items.BAMBOO)).offerTo(exporter);

        ShapelessRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.GLAZED_BAMBOO)
                .input(Items.BAMBOO)
                .input(Items.HONEY_BOTTLE)
                .criterion(hasItem(Items.BAMBOO), conditionsFromItem(Items.BAMBOO)).offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, ModItems.GOLDEN_BAMBOO)
                .pattern("AAA")
                .pattern("ABA")
                .pattern("AAA")
                .input('A', Items.GOLD_NUGGET)
                .input('B', Items.BAMBOO)
                .criterion(hasItem(Items.BAMBOO), conditionsFromItem(Items.BAMBOO)).offerTo(exporter);






    }
}
