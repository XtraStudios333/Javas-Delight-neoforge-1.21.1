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

    // Ingredients
    public static final DeferredItem<Item> COFFEECHERRIES = ITEMS.register("coffee_cherries",
            () -> new Item(new Item.Properties().component(DataComponents.FOOD,
                    new FoodProperties.Builder().nutrition(2).saturationModifier(0.2f).build()
            )));
    public static final DeferredItem<Item> RAWCOFFEEBEANS = ITEMS.register("raw_coffee_beans",
            () -> new ItemNameBlockItem(ModBlocks.COFFEE.get(), new Item.Properties()));
    public static final DeferredItem<Item> GREENCOFFEEBEANS = ITEMS.register("green_coffee_beans",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> ROASTEDCOFFEEBEANS = ITEMS.register("roasted_coffee_beans",
            () -> new Item(new Item.Properties().component(DataComponents.FOOD,
                    new FoodProperties.Builder()
                            .nutrition(1)
                            .saturationModifier(0.0f)
                            .fast()
                            .alwaysEdible()
                            .build()
            )));
    public static final DeferredItem<Item> ROASTEDCOFFEEPOWDER = ITEMS.register("roasted_coffee_powder",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> GREENCOFFEEPOWDER = ITEMS.register("green_coffee_powder",
            () -> new Item(new Item.Properties()));

    // Ceramics
    public static final DeferredItem<Item> RAWPORCELAIN = ITEMS.register("raw_porcelain",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> RAWPORCELAINCUP = ITEMS.register("raw_porcelain_cup",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> PORCELAINCUP = ITEMS.register("porcelain_cup",
            () -> new Item(new Item.Properties()));

    // Drinks
    public static final DeferredItem<Item> ESPRESSO = ITEMS.register("espresso",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> STEAMEDMILK = ITEMS.register("steamed_milk",
            () -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> FOAMEDMILK = ITEMS.register("foamed_milk",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventbus) {
        ITEMS.register(eventbus);
    }
}