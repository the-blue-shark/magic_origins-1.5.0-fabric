package net.the_blue_shark.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.the_blue_shark.MagicOrigins;

public class ModItemGroups {
    public static final ItemGroup MAGIC_ORIGINS_ITEMS = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(MagicOrigins.MOD_ID, "magic_origins_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.MAGIC_ORIGINS_ICON))
                    .displayName(Text.translatable("itemgroup.magic_origins.magic_origins_items"))
                    .entries((displayContext, entries) -> {

                        entries.add(ModItems.BAMBOO_SANDWICH);

                    }).build());


    public static void registerItemGroups() {
        MagicOrigins.LOGGER.info("Registering Item Groups for " + MagicOrigins.MOD_ID);
    }
}
