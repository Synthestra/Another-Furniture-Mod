package com.starfish_studios.another_furniture.mixin.neoforge.create;

//import com.simibubi.create.content.contraptions.BlockMovementChecks;
//import com.starfish_studios.another_furniture.block.ShutterBlock;
//import com.starfish_studios.another_furniture.block.properties.VerticalConnectionType;
//import com.starfish_studios.another_furniture.integration.common.create.CreateCommon;
//import net.minecraft.core.BlockPos;
//import net.minecraft.core.Direction;
//import net.minecraft.world.level.Level;
//import net.minecraft.world.level.block.state.BlockState;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//
//@Mixin(value = BlockMovementChecks.class, remap = false)
//public abstract class BlockMovementChecksMixin {
//    @Inject(method = "isBlockAttachedTowardsFallback", at = @At(value = "HEAD"), cancellable = true)
//    private static void af$allowStickyConnections(BlockState state, Level world, BlockPos pos, Direction direction, CallbackInfoReturnable<Boolean> cir) {
//        if (CreateCommon.canStickToContraption(state, direction)) cir.setReturnValue(true);
//    }
//}
