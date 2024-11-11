package com.starfish_studios.another_furniture.block;

import com.starfish_studios.another_furniture.block.properties.ModBlockStateProperties;
import com.starfish_studios.another_furniture.registry.AFBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class LampConnectorBlock extends Block implements SimpleWaterloggedBlock {

    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty BASE = ModBlockStateProperties.BASE;

    protected static final VoxelShape AABB_NOT_BASE = Block.box(7.0D, 0.0D, 7.0D, 9.0D, 16.0D, 9.0D);
    protected static final VoxelShape AABB_BASE = Shapes.or(Block.box(5.0D, 0.0D, 5.0D, 11.0D, 2.0D, 11.0D), Block.box(7.0D, 2.0D, 7.0D, 9.0D, 16.0D, 9.0D));

    private final DyeColor color;
    public LampConnectorBlock(DyeColor color, Properties properties) {
        super(properties);
        this.color = color;
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(POWERED, false)
                .setValue(WATERLOGGED, false)
                .setValue(BASE, true));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(BASE) ? AABB_BASE : AABB_NOT_BASE;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();

        BlockState blockstate = this.defaultBlockState();
        if (level.hasNeighborSignal(pos)) blockstate = blockstate.setValue(POWERED, true);

        return blockstate.setValue(WATERLOGGED, waterlogged);
    }

    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));

        if (direction == Direction.UP || direction == Direction.DOWN) {
            BlockState aState = level.getBlockState(currentPos.above());
            BlockState bState = level.getBlockState(currentPos.below());
            boolean aConnect = (aState.getBlock() instanceof LampBlock lampBlock && lampBlock.getColor() == this.getColor() && aState.getValue(LampBlock.FACING) == Direction.UP) || (aState.getBlock() instanceof LampConnectorBlock connectorBlock && connectorBlock.getColor() == this.getColor());
            boolean bConnect = (bState.getBlock() instanceof LampBlock lampBlock && lampBlock.getColor() == this.getColor() && bState.getValue(LampBlock.FACING) == Direction.UP) || (bState.getBlock() instanceof LampConnectorBlock connectorBlock && connectorBlock.getColor() == this.getColor());

            if (aConnect && !bConnect) state = state.setValue(BASE, true);
            else if (!aConnect && bConnect) state = getLampByColor(color).defaultBlockState().setValue(BASE, false).setValue(WATERLOGGED, state.getValue(WATERLOGGED));
            else if (aConnect) state = state.setValue(BASE, false);
            else state = getLampByColor(color).defaultBlockState().setValue(BASE, true).setValue(WATERLOGGED, state.getValue(WATERLOGGED));
        }

        return state;
    }

    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        if (level.isClientSide) return;

        BlockState below = level.getBlockState(pos.below());
        boolean powered = level.hasNeighborSignal(pos) || (below.getBlock() instanceof LampConnectorBlock && below.getValue(POWERED));
        if (powered != state.getValue(POWERED)) state = state.setValue(POWERED, powered);
        level.setBlock(pos, state, 3);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED, POWERED, BASE);
    }

    public DyeColor getColor() {
        return color;
    }

    public static Block getLampByColor(DyeColor color) {
        return switch (color) {
            case WHITE -> AFBlocks.WHITE_LAMP.get();
            case ORANGE -> AFBlocks.ORANGE_LAMP.get();
            case MAGENTA -> AFBlocks.MAGENTA_LAMP.get();
            case LIGHT_BLUE -> AFBlocks.LIGHT_BLUE_LAMP.get();
            case YELLOW -> AFBlocks.YELLOW_LAMP.get();
            case LIME -> AFBlocks.LIME_LAMP.get();
            case PINK -> AFBlocks.PINK_LAMP.get();
            case GRAY -> AFBlocks.GRAY_LAMP.get();
            case LIGHT_GRAY -> AFBlocks.LIGHT_GRAY_LAMP.get();
            case CYAN -> AFBlocks.CYAN_LAMP.get();
            case PURPLE -> AFBlocks.PURPLE_LAMP.get();
            case BLUE -> AFBlocks.BLUE_LAMP.get();
            case BROWN -> AFBlocks.BROWN_LAMP.get();
            case GREEN -> AFBlocks.GREEN_LAMP.get();
            case RED -> AFBlocks.RED_LAMP.get();
            case BLACK -> AFBlocks.BLACK_LAMP.get();
        };
    }

    @Override
    public String getDescriptionId() {
        Block lamp = getLampByColor(color);
        return lamp.getDescriptionId();
    }
}