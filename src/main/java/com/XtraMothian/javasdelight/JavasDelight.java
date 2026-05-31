package com.XtraMothian.javasdelight;

import com.XtraMothian.javasdelight.item.ModItems;
// FIXED: Added the missing import for your recipe serializers
import com.XtraMothian.javasdelight.common.registry.ModRecipeSerializers;

import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

@Mod(JavasDelight.MOD_ID)
public class JavasDelight {
    public static final String MOD_ID = "javasdelight";
    public static final Logger LOGGER = LogUtils.getLogger();

    public JavasDelight(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
        NeoForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);

        // CLEANED UP: Only call item registration once
        ModItems.register(modEventBus);

        // This will now resolve perfectly because of the import above!
        ModRecipeSerializers.register(modEventBus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(ModItems.COFFEECHERRIES);
            event.accept(ModItems.RAWCOFFEEBEANS);
            event.accept(ModItems.GREENCOFFEEBEANS);
            event.accept(ModItems.ROASTEDCOFFEEBEANS);
        }
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MOD_ID)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
            });
        }
    }
}