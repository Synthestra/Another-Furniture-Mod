package com.starfish_studios.another_furniture.block;

import com.starfish_studios.another_furniture.block.properties.ModBlockStateProperties;
import com.starfish_studios.another_furniture.util.block.HammerableBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class StoolBlock extends SeatBlock implements SimpleWaterloggedBlock, HammerableBlock {
    protected static final VoxelShape SHAPE_HIGH = Block.box(0.0D, 3.0D, 0.0D, 16.0D, 8.0D, 16.0D);
    protected static final VoxelShape SHAPE_LOW = Block.box(0.0D, 3.0D, 0.0D, 16.0D, 7.0D, 16.0D);
    protected static final VoxelShape LEG1 = Block.box(14.0D, 0.0D, 14.0D, 16.0D, 3.0D, 16.0D);
    protected static final VoxelShape LEG2 = Block.box(0.0D, 0.0D, 14.0D, 2.0D, 3.0D, 16.0D);
    protected static final VoxelShape LEG3 = Block.box(0.0D, 0.0D, 0.0D, 2.0D, 3.0D, 2.0D);
    protected static final VoxelShape LEG4 = Block.box(14.0D, 0.0D, 0.0D, 16.0D, 3.0D, 2.0D);

    protected static final VoxelShape AABB_HIGH = Shapes.or(SHAPE_HIGH, LEG1, LEG2, LEG3, LEG4);
    protected static final VoxelShape AABB_LOW = Shapes.or(SHAPE_LOW, LEG1, LEG2, LEG3, LEG4);

    public static final BooleanProperty LOW = ModBlockStateProperties.LOW;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public StoolBlock(Properties properties) {
        super(properties);
        registerDefaultState(this.stateDefinition.any()
                .setValue(WATERLOGGED, false)
                .setValue(LOW, false));
    }

    @Override
    public float seatHeight(BlockState state) {
        return state.getValue(LOW) ? 0.35F : 0.4F;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(LOW) ? AABB_LOW : AABB_HIGH;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        boolean waterlogged = context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER;
        return this.defaultBlockState().setValue(WATERLOGGED, waterlogged);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor level, BlockPos currentPos, BlockPos neighborPos) {
        if (state.getValue(WATERLOGGED)) {
            level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }
        return super.updateShape(state, direction, neighborState, level, currentPos, neighborPos);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (tryHammerBlock(LOW, stack, state, level, pos, player)) return ItemInteractionResult.SUCCESS;
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        return super.useWithoutItem(state, level, pos, player, hitResult);
    }

    public void fallOn(Level level, BlockState state, BlockPos pos, Entity entity, float dmg) {
        super.fallOn(level, state, pos, entity, dmg * 0.5F);
    }

    @Override
    public void updateEntityAfterFallOn(BlockGetter reader, Entity entity) {
        if (entity.isSuppressingBounce()) {
            entity.setDeltaMovement(entity.getDeltaMovement().multiply(1.0, 0.0, 1.0));
        } else {
            this.bounceUp(entity);
        }
        if (entity instanceof Player) return;
        super.updateEntityAfterFallOn(reader, entity);
    }

    private void bounceUp(Entity entity) {
        Vec3 vec3 = entity.getDeltaMovement();
        if (vec3.y >= 0.0D) return;
        double d0 = entity instanceof LivingEntity ? 1.0D : 0.8D;
        entity.setDeltaMovement(vec3.x, -vec3.y * (double)0.66F * d0, vec3.z);

    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LOW, WATERLOGGED);
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType pathComputationType) {
        return false;
    }

}
