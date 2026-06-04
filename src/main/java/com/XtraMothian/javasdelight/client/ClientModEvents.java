package com.XtraMothian.javasdelight.client;

import com.XtraMothian.javasdelight.client.screen.CopperKettleScreen;
import com.XtraMothian.javasdelight.menu.ModMenuTypes;

import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ClientModEvents {

    public static void registerScreens(RegisterMenuScreensEvent event) {

        event.register(
                ModMenuTypes.COPPER_KETTLE.get(),
                CopperKettleScreen::new
        );
    }
}