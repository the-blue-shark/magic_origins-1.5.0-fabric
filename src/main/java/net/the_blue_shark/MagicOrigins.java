package net.the_blue_shark;

import net.fabricmc.api.ModInitializer;

import net.the_blue_shark.entity.ModEntities;
import net.the_blue_shark.item.ModItemGroups;
import net.the_blue_shark.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MagicOrigins implements ModInitializer {
	public static final String MOD_ID = "magic_origins";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();
		ModItems.registerModItems();

		ModEntities.registerModEntities();

		LOGGER.info("Magic Origins has successfully initialized!");
	}
}