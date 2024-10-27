package com.starfish_studios.another_furniture.neoforge;

import com.starfish_studios.another_furniture.AnotherFurniture;
import com.starfish_studios.another_furniture.registry.AFBlocks;
import com.starfish_studios.another_furniture.registry.neoforge.AFRegistryImpl;
import com.starfish_studios.another_furniture.registry.neoforge.AFTabsImpl;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(AnotherFurniture.MOD_ID)
public class AnotherFurnitureNeoForge {

    public AnotherFurnitureNeoForge(IEventBus bus) {
        AnotherFurniture.init();

        AFRegistryImpl.BLOCKS.register(bus);
        AFRegistryImpl.ITEMS.register(bus);
        AFRegistryImpl.SOUND_EVENTS.register(bus);
        AFRegistryImpl.ENTITY_TYPES.register(bus);
        AFRegistryImpl.BLOCK_ENTITY_TYPES.register(bus);
        AFTabsImpl.register(bus);

        bus.addListener(this::setup);
        //NeoForge.EVENT_BUS.register(this);

    }

    private void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            AFBlocks.registerFlammables();
        });
    }

}