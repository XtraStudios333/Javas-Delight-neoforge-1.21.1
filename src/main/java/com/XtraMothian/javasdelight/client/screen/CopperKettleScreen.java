package com.XtraMothian.javasdelight.client.screen;

import com.XtraMothian.javasdelight.JavasDelight;
import com.XtraMothian.javasdelight.common.block.entity.container.CopperKettleMenu;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class CopperKettleScreen extends AbstractContainerScreen<CopperKettleMenu> {

    private static final ResourceLocation TEXTURE =
            ResourceLocation.fromNamespaceAndPath(
                    JavasDelight.MOD_ID,
                    "textures/gui/copper_kettle.png"
            );

    public CopperKettleScreen(CopperKettleMenu menu,
                              Inventory inventory,
                              Component title) {

        super(menu, inventory, title);

        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics,
                            float partialTick,
                            int mouseX,
                            int mouseY) {

        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        guiGraphics.blit(
                TEXTURE,
                x,
                y,
                0,
                0,
                this.imageWidth,
                this.imageHeight
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics,
                       int mouseX,
                       int mouseY,
                       float partialTick) {

        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        super.render(guiGraphics, mouseX, mouseY, partialTick);

        // THIS enables item tooltips
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}