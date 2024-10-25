package com.starfish_studios.another_furniture.registry.neoforge;

import com.starfish_studios.another_furniture.AnotherFurniture;
import com.starfish_studios.another_furniture.registry.AFBlocks;
import com.starfish_studios.another_furniture.registry.AFRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;

import java.util.function.Supplier;

public class AFTabsImpl {
    public static final Supplier<CreativeModeTab> AFM_TAB = AFRegistryImpl.MOD_TABS.register(AnotherFurniture.MOD_ID, () -> CreativeModeTab.builder()
            .icon(() -> new ItemStack(AFBlocks.OAK_CHAIR.get().asItem()))
            .title(Component.translatable("item_group." + AnotherFurniture.MOD_ID + ".tab"))
            .displayItems(((parameters, output) -> {
                output.acceptAll(AFRegistry.getAllModItems());
            })).build());

    public static void register(IEventBus eventBus) {
        AFRegistryImpl.MOD_TABS.register(eventBus);
    }
}
