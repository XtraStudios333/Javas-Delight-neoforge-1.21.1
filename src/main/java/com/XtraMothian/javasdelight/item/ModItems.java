package com.XtraMothian.javasdelight.item;

import com.XtraMothian.javasdelight.JavasDelight;
import com.XtraMothian.javasdelight.block.ModBlocks;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(JavasDelight.MOD_ID);

    // 1. Updated with Food Component (Restores 2 hunger points, 0.2 saturation)
    public static final DeferredItem<Item> COFFEECHERRIES = ITEMS.register("coffee_cherries",
            () -> new Item(new Item.Properties().component(DataComponents.FOOD,
                    new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build()
            )));

    public static final DeferredItem<Item> RAWCOFFEEBEANS = ITEMS.register("raw_coffee_beans",
            () -> new ItemNameBlockItem(ModBlocks.COFFEE.get(), new Item.Properties()));

    public static final DeferredItem<Item> GREENCOFFEEBEANS = ITEMS.register("green_coffee_beans",
            () -> new Item(new Item.Properties()));

    // 2. Updated with Food Component (Restores 1 hunger point, 0.1 saturation, fast to eat, always edible)
    public static final DeferredItem<Item> ROASTEDCOFFEEBEANS = ITEMS.register("roasted_coffee_beans",
            () -> new Item(new Item.Properties().component(DataComponents.FOOD,
                    new FoodProperties.Builder().nutrition(1).saturationModifier(0.0f).fast().alwaysEdible().build()
            )));

    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }
}