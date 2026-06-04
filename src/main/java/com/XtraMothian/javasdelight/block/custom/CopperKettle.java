package com.XtraMothian.javasdelight.block.custom;

import com.XtraMothian.javasdelight.block.state.CopperKettleSupport;
import com.XtraMothian.javasdelight.common.block.entity.CopperKettleBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.HashMap;
import java.util.Map;

public class CopperKettle extends Block implements SimpleWaterloggedBlock, EntityBlock {

    public static final DirectionProperty FACING =
            HorizontalDirectionalBlock.FACING;

    public static final BooleanProperty WATERLOGGED =
            BlockStateProperties.WATERLOGGED;

    public static final EnumProperty<CopperKettleSupport> SUPPORT =
            EnumProperty.create(
                    "support",
                    CopperKettleSupport.class
            );

    private static final Map<Direction, VoxelShape> SHAPES = new HashMap<>();

    private static final VoxelShape TRAY_SHAPE =
            Block.box(0.0, -1.0, 0.0, 16.0, 0.0, 16.0);

    static {
        VoxelShape pot = Block.box(3.0, 0.0, 3.0, 13.0, 6.0, 13.0);
        VoxelShape lid = Block.box(6.0, 6.0, 6.0, 10.0, 7.0, 10.0);
        VoxelShape handle = Block.box(4.0, 6.0, 8.0, 12.0, 11.0, 8.0);

        VoxelShape spoutNorth = Block.box(1.0, 2.0, 7.0, 3.0, 8.0, 9.0);
        VoxelShape mouthNorth = Block.box(0.0, 6.0, 7.0, 1.0, 8.0, 9.0);
        SHAPES.put(Direction.NORTH,
                Shapes.or(pot, lid, handle, spoutNorth, mouthNorth));

        VoxelShape spoutSouth = Block.box(13.0, 2.0, 7.0, 15.0, 8.0, 9.0);
        VoxelShape mouthSouth = Block.box(15.0, 6.0, 7.0, 16.0, 8.0, 9.0);
        SHAPES.put(Direction.SOUTH,
                Shapes.or(pot, lid, handle, spoutSouth, mouthSouth));

        VoxelShape spoutEast = Block.box(7.0, 2.0, 1.0, 9.0, 8.0, 3.0);
        VoxelShape mouthEast = Block.box(7.0, 6.0, 0.0, 9.0, 8.0, 1.0);
        SHAPES.put(Direction.EAST,
                Shapes.or(pot, lid, handle, spoutEast, mouthEast));

        VoxelShape spoutWest = Block.box(7.0, 2.0, 13.0, 9.0, 8.0, 15.0);
        VoxelShape mouthWest = Block.box(7.0, 6.0, 15.0, 9.0, 8.0, 16.0);
        SHAPES.put(Direction.WEST,
                Shapes.or(pot, lid, handle, spoutWest, mouthWest));
    }

    public CopperKettle(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(SUPPORT, CopperKettleSupport.NONE)
                        .setValue(WATERLOGGED, false)
        );
    }

    private CopperKettleSupport getTrayState(
            LevelAccessor level,
            BlockPos pos) {

        if (level.getBlockState(pos.below())
                .is(BlockTags.CAMPFIRES)) {

            return CopperKettleSupport.TRAY;
        }

        return CopperKettleSupport.NONE;
    }

    @Override
    public BlockEntity newBlockEntity(
            BlockPos pos,
            BlockState state) {

        return new CopperKettleBlockEntity(pos, state);
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState state,
            Level level,
            BlockPos pos,
            Player player,
            BlockHitResult hit) {

        if (!level.isClientSide()) {

            BlockEntity blockEntity =
                    level.getBlockEntity(pos);

            if (blockEntity instanceof CopperKettleBlockEntity kettle) {

                player.openMenu(
                        (MenuProvider) kettle,
                        pos
                );
            }
        }

        return InteractionResult.sidedSuccess(level.isClientSide());
    }

    @Override
    public VoxelShape getShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context) {

        VoxelShape kettleShape =
                SHAPES.get(state.getValue(FACING));

        if (state.getValue(SUPPORT) == CopperKettleSupport.TRAY) {
            return Shapes.or(kettleShape, TRAY_SHAPE);
        }

        return kettleShape;
    }

    @Override
    public VoxelShape getCollisionShape(
            BlockState state,
            BlockGetter level,
            BlockPos pos,
            CollisionContext context) {

        VoxelShape kettleShape =
                SHAPES.get(state.getValue(FACING));

        if (state.getValue(SUPPORT) == CopperKettleSupport.TRAY) {
            return Shapes.or(kettleShape, TRAY_SHAPE);
        }

        return kettleShape;
    }

    @Override
    public BlockState getStateForPlacement(
            BlockPlaceContext context) {

        FluidState fluidState =
                context.getLevel()
                        .getFluidState(context.getClickedPos());

        return this.defaultBlockState()
                .setValue(
                        FACING,
                        context.getHorizontalDirection()
                )
                .setValue(
                        SUPPORT,
                        getTrayState(
                                context.getLevel(),
                                context.getClickedPos()
                        )
                )
                .setValue(
                        WATERLOGGED,
                        fluidState.getType() == Fluids.WATER
                );
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED)
                ? Fluids.WATER.getSource(false)
                : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(
            BlockState state,
            Direction direction,
            BlockState neighborState,
            LevelAccessor level,
            BlockPos pos,
            BlockPos neighborPos) {

        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(
                    pos,
                    Fluids.WATER,
                    Fluids.WATER.getTickDelay(level)
            );
        }

        if (direction.getAxis() == Direction.Axis.Y) {
            return state.setValue(
                    SUPPORT,
                    getTrayState(level, pos)
            );
        }

        return state;
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(
                FACING,
                SUPPORT,
                WATERLOGGED
        );
    }
}