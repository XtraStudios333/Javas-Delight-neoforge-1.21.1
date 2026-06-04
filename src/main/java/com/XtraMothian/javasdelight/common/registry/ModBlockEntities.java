package com.XtraMothian.javasdelight.common.registry;

import com.XtraMothian.javasdelight.JavasDelight;
import com.XtraMothian.javasdelight.block.ModBlocks;
import com.XtraMothian.javasdelight.common.block.entity.CopperKettleBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, JavasDelight.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CopperKettleBlockEntity>>
            COPPER_KETTLE = BLOCK_ENTITIES.register(
            "copper_kettle",
            () -> BlockEntityType.Builder.of(
                    CopperKettleBlockEntity::new,
                    ModBlocks.COPPER_KETTLE.get()
            ).build(null)
    );

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}