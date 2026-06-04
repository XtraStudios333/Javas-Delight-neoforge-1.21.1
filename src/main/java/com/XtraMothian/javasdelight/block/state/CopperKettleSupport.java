package com.XtraMothian.javasdelight.block.state;

import net.minecraft.util.StringRepresentable;

public enum CopperKettleSupport implements StringRepresentable {

    NONE("none"),
    TRAY("tray");

    private final String name;

    CopperKettleSupport(String name) {
        this.name = name;
    }

    @Override
    public String getSerializedName() {
        return name;
    }
}