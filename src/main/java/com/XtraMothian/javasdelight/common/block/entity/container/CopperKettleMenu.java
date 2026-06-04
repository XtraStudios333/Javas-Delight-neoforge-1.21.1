package com.XtraMothian.javasdelight.common.block.entity.container;

import com.XtraMothian.javasdelight.common.block.entity.CopperKettleBlockEntity;
import com.XtraMothian.javasdelight.menu.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class CopperKettleMenu extends AbstractContainerMenu {

    private final CopperKettleBlockEntity blockEntity;

    public CopperKettleMenu(
            int containerId,
            Inventory inventory,
            FriendlyByteBuf buffer) {

        this(
                containerId,
                inventory,
                (CopperKettleBlockEntity) inventory.player.level()
                        .getBlockEntity(buffer.readBlockPos())
        );
    }

    public CopperKettleMenu(
            int containerId,
            Inventory inventory,
            CopperKettleBlockEntity blockEntity) {

        super(ModMenuTypes.COPPER_KETTLE.get(), containerId);

        this.blockEntity = blockEntity;

        // =========================
        // Kettle Ingredient Slots
        // =========================

        // ingredients
        this.addSlot(new SlotItemHandler(
                blockEntity.getInventory(),
                0,
                30,
                25));

        this.addSlot(new SlotItemHandler(
                blockEntity.getInventory(),
                1,
                48,
                25));

        this.addSlot(new SlotItemHandler(
                blockEntity.getInventory(),
                2,
                66,
                25));

// result
        this.addSlot(new SlotItemHandler(
                blockEntity.getInventory(),
                3,
                124,
                26
        ) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });

// additives
        this.addSlot(new SlotItemHandler(
                blockEntity.getInventory(),
                4,
                115,
                55));

        this.addSlot(new SlotItemHandler(
                blockEntity.getInventory(),
                5,
                133,
                55));

        // =========================
        // Player Inventory
        // =========================

        int inventoryStartX = 8;
        int inventoryStartY = 84;

        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {

                this.addSlot(new Slot(
                        inventory,
                        column + row * 9 + 9,
                        inventoryStartX + column * 18,
                        inventoryStartY + row * 18
                ));
            }
        }

        // =========================
        // Hotbar
        // =========================

        int hotbarY = 142;

        for (int column = 0; column < 9; column++) {

            this.addSlot(new Slot(
                    inventory,
                    column,
                    inventoryStartX + column * 18,
                    hotbarY
            ));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {

        Slot sourceSlot = this.slots.get(index);

        if (!sourceSlot.hasItem()) {
            return ItemStack.EMPTY;
        }

        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copy = sourceStack.copy();

        // ==========================
        // From kettle -> player
        // ==========================

        if (index < 6) {

            if (!moveItemStackTo(
                    sourceStack,
                    6,
                    this.slots.size(),
                    true)) {

                return ItemStack.EMPTY;
            }
        }

        // ==========================
        // From player -> kettle
        // ==========================

        else {

            boolean moved = false;

            // Ingredient slots first
            moved = moveItemStackTo(
                    sourceStack,
                    0,
                    3,
                    false
            );

            // Additives second
            if (!moved) {
                moved = moveItemStackTo(
                        sourceStack,
                        4,
                        6,
                        false
                );
            }

            if (!moved) {
                return ItemStack.EMPTY;
            }
        }

        if (sourceStack.isEmpty()) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }

        sourceSlot.onTake(player, sourceStack);

        return copy;
    }
}