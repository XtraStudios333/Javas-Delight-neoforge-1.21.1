package com.XtraMothian.javasdelight.block.custom;

import com.XtraMothian.javasdelight.item.ModItems;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CoffeeBlock extends CropBlock {
    public static final MapCodec<CoffeeBlock> CODEC = simpleCodec(CoffeeBlock::new);

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 6);
    public static final int MAX_AGE = 6;

    public CoffeeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        BlockPos blockBelowPos = pos.below();
        BlockState blockBelowState = level.getBlockState(blockBelowPos);
        ResourceLocation blockBelowId = BuiltInRegistries.BLOCK.getKey(blockBelowState.getBlock());

        if (blockBelowId.toString().equals("farmersdelight:rich_soil_farmland")) {
            if (level.getRawBrightness(pos, 0) >= 9) {
                int currentAge = this.getAge(state);

                if (currentAge < this.getMaxAge()) {
                    float growthSpeed = CropBlock.getGrowthSpeed(
                            this.defaultBlockState(),
                            level,
                            pos
                    );

                    if (random.nextInt((int) (25.0F / growthSpeed) + 1) == 0) {
                        level.setBlock(pos, this.getStateForAge(currentAge + 1), 2);
                    }
                }
            }

            return;
        }

        super.randomTick(state, level, pos, random);
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());

        return blockId.toString().equals("farmersdelight:rich_soil_farmland");
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos blockBelowPos = pos.below();
        BlockState blockBelowState = level.getBlockState(blockBelowPos);
        ResourceLocation blockBelowId = BuiltInRegistries.BLOCK.getKey(blockBelowState.getBlock());

        if (blockBelowId.toString().equals("minecraft:farmland")) {
            if (level instanceof Level world && world.isClientSide) {
                AABB area = new AABB(pos).inflate(5);

                List<Player> players =
                        world.getEntitiesOfClass(Player.class, area);

                for (Player player : players) {
                    player.displayClientMessage(
                            Component.literal("§fThis crop needs better soil"),
                            true
                    );
                }
            }

            return false;
        }

        return this.mayPlaceOn(blockBelowState, level, blockBelowPos);
    }

    @Override
    protected @NotNull ItemLike getBaseSeedId() {
        return ModItems.RAWCOFFEEBEANS.get();
    }

    @Override
    protected IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    public MapCodec<? extends CropBlock> codec() {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hitResult
    ) {
        int age = getAge(state);

        if (age >= 6) {
            if (!level.isClientSide) {

                // Main harvest
                popResource(level, pos,
                        new ItemStack(ModItems.COFFEECHERRIES.get(), 2));

                // Optional bonus bean
                if (level.random.nextFloat() < 0.25F) {
                    popResource(level, pos,
                            new ItemStack(ModItems.COFFEECHERRIES.get()));
                }

                // Reset to regrowth stage
                level.setBlock(
                        pos,
                        state.setValue(AGE, 3),
                        2
                );

                level.playSound(
                        null,
                        pos,
                        SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES,
                        SoundSource.BLOCKS,
                        1.0F,
                        0.8F + level.random.nextFloat() * 0.4F
                );
            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }
}