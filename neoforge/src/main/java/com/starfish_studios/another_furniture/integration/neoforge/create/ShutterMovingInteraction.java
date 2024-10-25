package com.starfish_studios.another_furniture.integration.neoforge.create;

//import com.simibubi.create.content.contraptions.Contraption;
//import com.simibubi.create.content.contraptions.behaviour.SimpleBlockMovingInteraction;
//import com.starfish_studios.another_furniture.block.ShutterBlock;
//import com.starfish_studios.another_furniture.block.properties.VerticalConnectionType;
//import net.minecraft.core.BlockPos;
//import net.minecraft.world.entity.player.Player;
//import net.minecraft.world.level.block.state.BlockState;
//import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
//
//public class ShutterMovingInteraction extends SimpleBlockMovingInteraction {
//
//    protected BlockState handle(Player player, Contraption contraption, BlockPos pos, BlockState currentState) {
//        toggleShutters(currentState, pos, currentState.getValue(ShutterBlock.OPEN), contraption);
//        this.playSound(player, ShutterBlock.shutterSound(currentState.getValue(ShutterBlock.OPEN)), 1.0F);
//        return currentState.cycle(ShutterBlock.OPEN);
//    }
//
//    public void toggleShutters(BlockState state, BlockPos pos, boolean open, Contraption contraption) {
//        open = !open;
//
//        BlockState updateState = state;
//        BlockPos updatePos = pos;
//        if (state.getValue(ShutterBlock.VERTICAL) == VerticalConnectionType.MIDDLE || state.getValue(ShutterBlock.VERTICAL) == VerticalConnectionType.BOTTOM) {
//            int heightUp = (int)contraption.bounds.maxY - updatePos.getY();
//            for (int i = 0; i < heightUp; i++) {
//                StructureTemplate.StructureBlockInfo above = contraption.getBlocks().get(updatePos.above());
//                if (above != null && above.state().is(state.getBlock()) && above.state().getValue(ShutterBlock.FACING) == updateState.getValue(ShutterBlock.FACING) && above.state().getValue(ShutterBlock.HINGE) == updateState.getValue(ShutterBlock.HINGE) && above.state().getValue(ShutterBlock.OPEN) != open) {
//                    updateState = above.state();
//                    updatePos = updatePos.above();
//                    this.setContraptionBlockData(contraption.entity, updatePos, new StructureTemplate.StructureBlockInfo(above.pos(), updateState.setValue(ShutterBlock.OPEN, open), above.nbt()));
//                } else {
//                    break;
//                }
//            }
//        }
//        if (state.getValue(ShutterBlock.VERTICAL) == VerticalConnectionType.MIDDLE || state.getValue(ShutterBlock.VERTICAL) == VerticalConnectionType.TOP) {
//            updateState = state;
//            updatePos = pos;
//            int heightDown = (int)contraption.bounds.minY - updatePos.getY();
//            heightDown = (heightDown < 0) ? -heightDown : heightDown;
//            for (int i = 0; i < heightDown; i++) {
//                StructureTemplate.StructureBlockInfo below = contraption.getBlocks().get(updatePos.below());
//                if (below != null && below.state().is(state.getBlock()) && below.state().getValue(ShutterBlock.FACING) == updateState.getValue(ShutterBlock.FACING) && below.state().getValue(ShutterBlock.HINGE) == updateState.getValue(ShutterBlock.HINGE) && below.state().getValue(ShutterBlock.OPEN) != open) {
//                    updateState = below.state();
//                    updatePos = updatePos.below();
//                    this.setContraptionBlockData(contraption.entity, updatePos, new StructureTemplate.StructureBlockInfo(below.pos(), updateState.setValue(ShutterBlock.OPEN, open), below.nbt()));
//                } else {
//                    break;
//                }
//            }
//        }
//    }
//
//    protected boolean updateColliders() {
//        return true;
//    }
//}
