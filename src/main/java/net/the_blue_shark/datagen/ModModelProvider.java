package net.the_blue_shark.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;
import net.minecraft.data.client.TexturedModel;
import net.the_blue_shark.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {


    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.BAMBOO_SANDWICH, Models.GENERATED);
        itemModelGenerator.register(ModItems.BAMBOO_GOLDEN_APPLE, Models.GENERATED);
        itemModelGenerator.register(ModItems.GOLDEN_BAMBOO, Models.GENERATED);
        itemModelGenerator.register(ModItems.GLAZED_BAMBOO, Models.GENERATED);

        itemModelGenerator.register(ModItems.MAGIC_ORIGINS_ICON, Models.GENERATED);
    }
}
