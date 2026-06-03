package com.XtraMothian.javasdelight.block;

import com.XtraMothian.javasdelight.JavasDelight;
import com.XtraMothian.javasdelight.block.custom.CoffeeBlock;
import com.XtraMothian.javasdelight.item.ModItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.block.Block; // Fixed import
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import vectorwing.farmersdelight.common.block.WildCropBlock;
import vectorwing.farmersdelight.common.registry.ModCreativeTabs;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(JavasDelight.MOD_ID);

    // Wild Crops
    public static final DeferredBlock<Block> WILD_COFFEE = registerBlock("wild_coffee",
            () -> new WildCropBlock(
                    MobEffects.MOVEMENT_SPEED,
                    5,
                    Block.Properties.ofFullCopy(Blocks.TALL_GRASS)
                            .noCollission()
                            .destroyTime(0.0F)
            ));
    // Domestic Crops
    public static final DeferredBlock<Block> COFFEE = BLOCKS.register("coffee",
            () -> new CoffeeBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.WHEAT)));
    // Storage Blocks
    public static final DeferredBlock<Block> COFFEE_CHERRY_BAG = registerBlock("coffee_cherry_bag",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));
    public static final DeferredBlock<Block> ROASTED_COFFEE_BAG = registerBlock("roasted_coffee_bag",
            () -> new Block(Block.Properties.ofFullCopy(Blocks.WHITE_WOOL)));


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties())); // Fixed to Block.get()
    }

    public static void register(IEventBus eventbus) {
        BLOCKS.register(eventbus);
    }
}