package com.XtraMothian.javasdelight.menu;

import com.XtraMothian.javasdelight.JavasDelight;

import com.XtraMothian.javasdelight.common.block.entity.container.CopperKettleMenu;
import net.minecraft.world.inventory.MenuType;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(
                    net.minecraft.core.registries.Registries.MENU,
                    JavasDelight.MOD_ID
            );

    public static final DeferredHolder<MenuType<?>, MenuType<CopperKettleMenu>> COPPER_KETTLE =
            MENU_TYPES.register(
                    "copper_kettle",
                    () -> IMenuTypeExtension.create(CopperKettleMenu::new)
            );

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}