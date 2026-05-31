package com.XtraMothian.javasdelight.item;

import net.minecraft.world.food.FoodProperties;

public final class ModFoodProperties {
    // Private constructor prevents other classes from doing: new ModFoodProperties()
    private ModFoodProperties() {}
    // Coffee Cherries: Restores 1 Hunger Shank (2 points), low saturation (0.2f)
    public static final FoodProperties COFFEE_CHERRIES = new FoodProperties.Builder()
            .nutrition(3)
            .saturationModifier(0.2f)
            .build();

    // Roasted Coffee Beans: Restores 0.5 Hunger Shank (1 point), fast to eat, always edible
    public static final FoodProperties ROASTED_COFFEE_BEANS = new FoodProperties.Builder()
            .nutrition(1)
            .saturationModifier(0.1f)
            .fast() // Makes the eating animation twice as fast, like dried kelp
            .alwaysEdible() // Can be consumed even if the hunger bar is full
            .build();
}