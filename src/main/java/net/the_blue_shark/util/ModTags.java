package net.the_blue_shark.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.the_blue_shark.MagicOrigins;

public class ModTags {
    public static class Blocks {

        private static TagKey<Block> createTag(String name) {
            return TagKey.of(RegistryKeys.BLOCK, Identifier.of(MagicOrigins.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> PANDA_FOOD = createTag("panda_food");
        public static final TagKey<Item> NEUTRAL_FOOD = createTag("neutral_food");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(MagicOrigins.MOD_ID, name));
        }
    }
}
