package com.starfish_studios.another_furniture.neoforge;

import com.starfish_studios.another_furniture.AnotherFurniture;
import com.starfish_studios.another_furniture.client.AnotherFurnitureClient;
import com.starfish_studios.another_furniture.client.renderer.blockentity.ServiceBellButtonRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = AnotherFurniture.MOD_ID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
public class AnotherFurnitureForgeClient {
    @SubscribeEvent
    public static void init(FMLClientSetupEvent event) {
        AnotherFurnitureClient.init();

    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(ServiceBellButtonRenderer.SERVICE_BELL_MODEL, ServiceBellButtonRenderer::createBodyLayer);
        //event.registerLayerDefinition(GrandfatherClockRenderer.GRANDFATHER_CLOCK_MODEL, GrandfatherClockRenderer::createBodyLayer);

    }

//    @SubscribeEvent
//    public static void registerSpecialModels(ModelEvent.RegisterAdditional event) {
//        event.register(new ModelResourceLocation(AnotherFurniture.MOD_ID + ":green_wallpaper"));
//        event.register(new ModelResourceLocation(AnotherFurniture.MOD_ID + ":red_stripes_wallpaper"));
//    }

}
