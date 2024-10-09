package net.the_blue_shark.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.AliasedBlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.the_blue_shark.MagicOrigins;

public class ModItems {
    public static final Item BAMBOO_SANDWICH = registerItem("bamboo_sandwich",
            new Item(new Item.Settings().food(ModFoodComponents.BAMBOO_SANDWICH)));

    public static final Item MAGIC_ORIGINS_ICON = registerItem("magic_origins_icon",
            new Item(new Item.Settings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(MagicOrigins.MOD_ID, name), item);
    }

    private static void itemGroupNatural(FabricItemGroupEntries entries) {

    }
    private static void itemGroupFoodAndDrink(FabricItemGroupEntries entries) {
        entries.add(BAMBOO_SANDWICH);

    }

    public static void registerModItems() {
        MagicOrigins.LOGGER.info("Registering Mod Items for " + MagicOrigins.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ModItems::itemGroupNatural);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FOOD_AND_DRINK).register(ModItems::itemGroupFoodAndDrink);
    }
}
