package net.the_blue_shark.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryWrapper;
import net.the_blue_shark.item.ModItems;
import net.the_blue_shark.util.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ModTags.Items.PANDA_FOOD)
                .add(ModItems.BAMBOO_SANDWICH)
                .add(ModItems.BAMBOO_GOLDEN_APPLE)
                .add(ModItems.ENCHANTED_BAMBOO_GOLDEN_APPLE)
                .add(ModItems.GOLDEN_BAMBOO)
                .add(ModItems.GLAZED_BAMBOO)
                .add(Items.BAMBOO)

        ;

        getOrCreateTagBuilder(ModTags.Items.NEUTRAL_FOOD)
                .add(Items.OMINOUS_BOTTLE)

        ;

        getOrCreateTagBuilder(ModTags.Items.BAMBOO_DARTS)
                .add(ModItems.DART)
                .add(ModItems.TIPPED_DART)

        ;
    }
}
