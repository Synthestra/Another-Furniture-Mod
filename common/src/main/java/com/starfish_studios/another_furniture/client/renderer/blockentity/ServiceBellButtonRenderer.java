package com.starfish_studios.another_furniture.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.starfish_studios.another_furniture.AnotherFurniture;
import com.starfish_studios.another_furniture.block.entity.ServiceBellBlockEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;

@Environment(value= EnvType.CLIENT)
public class ServiceBellButtonRenderer implements BlockEntityRenderer<ServiceBellBlockEntity> {
    public static final Material BELL_TEXTURE = new Material(InventoryMenu.BLOCK_ATLAS, AnotherFurniture.res("block/service_bell"));
    public static ModelLayerLocation SERVICE_BELL_MODEL = new ModelLayerLocation(AnotherFurniture.res("service_bell"), "service_bell");
    private final ModelPart button;

    public ServiceBellButtonRenderer(BlockEntityRendererProvider.Context context) {
        this.button = context.bakeLayer(SERVICE_BELL_MODEL);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();
        partdefinition.addOrReplaceChild("button", CubeListBuilder.create().texOffs(19, 11).addBox(-9.0F, -7.0F, 7.0F, 2.0F, 1.0F, 2.0F), PartPose.ZERO);
        partdefinition.getChild("button").addOrReplaceChild("button_shaft", CubeListBuilder.create().texOffs(0, 0).addBox(-8.5F, -6.0F, 7.5F, 1.0F, 1.0F, 1.0F), PartPose.ZERO);
        return LayerDefinition.create(meshdefinition, 32, 32);
    }


    @Override
    public void render(ServiceBellBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        float f3 = 0f;
        if (blockEntity.pressed) f3 = -(blockEntity.ticks / 25.0f);

        poseStack.translate(0, f3, 0);


        poseStack.pushPose();
        poseStack.scale(-1f, -1f, 1f);

        this.button.render(poseStack, BELL_TEXTURE.buffer(bufferSource, RenderType::entitySolid), packedLight, packedOverlay);

        poseStack.popPose();
    }
}
