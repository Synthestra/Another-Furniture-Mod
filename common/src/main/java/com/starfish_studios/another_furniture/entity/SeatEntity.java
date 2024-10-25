package com.starfish_studios.another_furniture.entity;

import com.starfish_studios.another_furniture.block.SeatBlock;
import com.starfish_studios.another_furniture.registry.AFEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.vehicle.DismountHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class SeatEntity extends Entity {
    public SeatEntity(Level level) {
        super(AFEntityTypes.SEAT.get(), level);
        this.noPhysics = true;
    }

    public SeatEntity(Level level, BlockPos pos) {
        this(level);
        this.setPos(pos.getX() + 0.5, pos.getY() + 0.001, pos.getZ() + 0.5);
    }
    @Override
    public void tick() {
        if (this.level().isClientSide) return;

        BlockState state = this.level().getBlockState(this.blockPosition());
        boolean canSit;
        if (state.getBlock() instanceof SeatBlock seatBlock) canSit = seatBlock.isSittable(state);
        else canSit = false;
        if (isVehicle() && canSit) return;


        this.discard();
        this.level().updateNeighbourForOutputSignal(this.blockPosition(), this.level().getBlockState(this.blockPosition()).getBlock());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {}

    @Override
    protected void readAdditionalSaveData(CompoundTag compound) {}

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {}

    @Override
    public Vec3 getPassengerRidingPosition(Entity entity) {
        return this.position().add(new Vec3(0.0, getPassengersRidingOffset(), 0.0));
    }

    public double getPassengersRidingOffset() {
        List<Entity> passengers = this.getPassengers();
        if (passengers.isEmpty()) return 0.0;
        double seatHeight = 0.0;
        BlockState state = this.level().getBlockState(this.blockPosition());
        if (state.getBlock() instanceof SeatBlock seatBlock) seatHeight = seatBlock.seatHeight(state);

        return seatHeight + getEntitySeatOffset(passengers.getFirst());
    }

    public static double getEntitySeatOffset(Entity entity) {
        if (entity instanceof Slime) return 1 / 4f;
        if (entity instanceof Parrot) return 1 / 16f;
        if (entity instanceof Skeleton) return 1 / 8f;
        if (entity instanceof Creeper) return 1 / 4f;
        if (entity instanceof Cat) return 1 / 8f;
        if (entity instanceof Wolf) return 1 / 16f;
        return 0;
    }

    @Override
    protected boolean canRide(Entity entity)
    {
        return true;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket(ServerEntity entity) {
        return super.getAddEntityPacket(entity);
    }
    //todo ^
    //@Override
    //public Packet<ClientGamePacketListener> getAddEntityPacket() {
    //    return new ClientboundAddEntityPacket(this);
    //}

    @Override
    public Vec3 getDismountLocationForPassenger(LivingEntity entity) {
        BlockPos pos = this.blockPosition();
        Vec3 safeVec;
        BlockState state = this.level().getBlockState(pos);
        if (state.getBlock() instanceof SeatBlock seatBlock) {
            safeVec = DismountHelper.findSafeDismountLocation(entity.getType(), this.level(), seatBlock.primaryDismountLocation(this.level(), state, pos), false);
            if (safeVec != null) return safeVec.add(0, 0.25, 0);
        }

        Direction original = this.getDirection();
        Direction[] offsets = {original, original.getClockWise(), original.getCounterClockWise(), original.getOpposite()};
        for(Direction dir : offsets) {
            safeVec = DismountHelper.findSafeDismountLocation(entity.getType(), this.level(), pos.relative(dir), false);
            if (safeVec != null) return safeVec.add(0, 0.25, 0);
        }
        return super.getDismountLocationForPassenger(entity);
    }

    @Override
    protected void addPassenger(Entity passenger) {
        BlockPos pos = this.blockPosition();
        BlockState state = this.level().getBlockState(pos);
        if (state.getBlock() instanceof SeatBlock seatBlock) passenger.setYRot(seatBlock.setRiderRotation(state, passenger));
        super.addPassenger(passenger);
    }

    @Override
    protected void removePassenger(Entity entity) {
        super.removePassenger(entity);
        if (entity instanceof TamableAnimal ta) ta.setInSittingPose(false);
    }
}