package com.XtraMothian.javasdelight;

import com.XtraMothian.javasdelight.block.ModBlocks;
import com.XtraMothian.javasdelight.common.registry.ModBlockEntities;
import com.XtraMothian.javasdelight.client.ClientModEvents;
import com.XtraMothian.javasdelight.common.registry.ModRecipeSerializers;
import com.XtraMothian.javasdelight.item.ModItems;
import com.XtraMothian.javasdelight.menu.ModMenuTypes;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(JavasDelight.MOD_ID)
public class JavasDelight {

    public static final String MOD_ID = "javasdelight";
    public static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceKey<CreativeModeTab> FARMERS_DELIGHT_TAB =
            ResourceKey.create(
                    Registries.CREATIVE_MODE_TAB,
                    ResourceLocation.fromNamespaceAndPath(
                            "farmersdelight",
                            "farmersdelight"
                    )
            );

    public JavasDelight(IEventBus modEventBus, ModContainer modContainer) {

        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreative);

        // Register menu screens
        modEventBus.addListener(ClientModEvents::registerScreens);

        NeoForge.EVENT_BUS.register(this);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModRecipeSerializers.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {

        if (event.getTabKey() == FARMERS_DELIGHT_TAB) {

            event.insertAfter(
                    vectorwing.farmersdelight.common.registry.ModItems.SKILLET.get().getDefaultInstance(),
                    ModBlocks.COPPER_KETTLE.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            event.insertAfter(
                    vectorwing.farmersdelight.common.registry.ModItems.WILD_RICE.get().getDefaultInstance(),
                    ModBlocks.WILD_COFFEE.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            event.insertAfter(
                    vectorwing.farmersdelight.common.registry.ModItems.RICE_BAG.get().getDefaultInstance(),
                    ModBlocks.COFFEE_CHERRY_BAG.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            event.insertAfter(
                    ModBlocks.COFFEE_CHERRY_BAG.get().asItem().getDefaultInstance(),
                    ModBlocks.ROASTED_COFFEE_BAG.get().asItem().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            event.insertAfter(
                    vectorwing.farmersdelight.common.registry.ModItems.RICE_PANICLE.get().getDefaultInstance(),
                    ModItems.COFFEECHERRIES.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            event.insertAfter(
                    vectorwing.farmersdelight.common.registry.ModItems.TOMATO_SEEDS.get().getDefaultInstance(),
                    ModItems.RAWCOFFEEBEANS.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            event.insertAfter(
                    vectorwing.farmersdelight.common.registry.ModItems.CABBAGE_LEAF.get().getDefaultInstance(),
                    ModItems.GREENCOFFEEBEANS.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );

            event.insertAfter(
                    ModItems.GREENCOFFEEBEANS.get().getDefaultInstance(),
                    ModItems.ROASTEDCOFFEEBEANS.get().getDefaultInstance(),
                    CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS
            );
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }
}