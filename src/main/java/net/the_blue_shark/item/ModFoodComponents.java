package net.the_blue_shark.item;

import net.minecraft.component.type.FoodComponent;

public class ModFoodComponents {
    public static final FoodComponent BAMBOO_SANDWICH = new FoodComponent.Builder()
            .nutrition(7).saturationModifier(0.6f).build();
}
