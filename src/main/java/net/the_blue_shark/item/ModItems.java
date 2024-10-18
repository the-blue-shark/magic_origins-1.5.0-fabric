package net.the_blue_shark.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.type.FoodComponents;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.the_blue_shark.MagicOrigins;
import net.the_blue_shark.item.custom.BambooLickingItem;
import net.the_blue_shark.item.custom.EnchantedItem;

public class ModItems {
    public static final Item BAMBOO_SANDWICH = registerItem("bamboo_sandwich",
            new Item(new Item.Settings().food(ModFoodComponents.BAMBOO_SANDWICH)));

    public static final Item GOLDEN_BAMBOO = registerItem("golden_bamboo",
            new Item(new Item.Settings().food(FoodComponents.GOLDEN_CARROT)));

    public static final Item BAMBOO_GOLDEN_APPLE = registerItem("bamboo_golden_apple",
            new Item(new Item.Settings().food(FoodComponents.GOLDEN_APPLE)));

    public static final Item ENCHANTED_BAMBOO_GOLDEN_APPLE = registerItem("enchanted_bamboo_golden_apple",
            new EnchantedItem(new Item.Settings().food(FoodComponents.ENCHANTED_GOLDEN_APPLE)));

    public static final Item GLAZED_BAMBOO = registerItem("glazed_bamboo",
            new BambooLickingItem(new Item.Settings().food(FoodComponents.HONEY_BOTTLE)));

    public static final Item MAGIC_ORIGINS_ICON = registerItem("magic_origins_icon",
            new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MagicOrigins.MOD_ID, name), item);
    }

    private static void itemGroupNatural(FabricItemGroupEntries entries) {

    }
    private static void itemGroupFoodAndDrink(FabricItemGroupEntries entries) {
        entries.add(BAMBOO_SANDWICH);
        entries.add(ModItems.GOLDEN_BAMBOO);
        entries.add(ModItems.BAMBOO_GOLDEN_APPLE);
        entries.add(ModItems.ENCHANTED_BAMBOO_GOLDEN_APPLE);

    }

    public static void registerModItems() {
        MagicOrigins.LOGGER.info("Registering Mod Items for " + MagicOrigins.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ModItems::itemGroupNatural);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::itemGroupFoodAndDrink);
    }
}
