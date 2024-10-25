package com.starfish_studios.another_furniture.event.neoforge;

//import com.starfish_studios.another_furniture.mixin.neoforge.AbstractFurnaceBlockEntityAccessor;
//import com.starfish_studios.another_furniture.registry.neoforge.AFFurnaceFuelRegistryImpl;
//import net.minecraft.world.item.Item;
//import net.minecraft.world.level.ItemLike;
//import net.minecraftforge.event.RegisterStructureConversionsEvent;
//import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
//import net.minecraftforge.eventbus.api.SubscribeEvent;
//import net.minecraftforge.fml.common.Mod;
//
//import java.util.Map;
//
//@Mod.EventBusSubscriber
//public class FurnaceFuelEvent {
//    @SubscribeEvent
//    public static void setFuelValue(FurnaceFuelBurnTimeEvent event) {
//        for (Map.Entry<Item, Integer> fuel : AFFurnaceFuelRegistryImpl.BURN_TIMES.entrySet())
//            tryAddFuel(event, fuel.getKey(), fuel.getValue());
//    }
//
//    @SuppressWarnings("ConstantConditions")
//    private static void tryAddFuel(FurnaceFuelBurnTimeEvent event, ItemLike item, int burnTime) {
//        var itemStack = event.getItemStack();
//        // Doesn't recognise that it invokes a mixin to access a condition. This is fine. Suppressed.
//        boolean isValidFuel = itemStack.is(item.asItem()) && !AbstractFurnaceBlockEntityAccessor.invokeNotFurnaceFuel(itemStack.getItem());
//        if (isValidFuel) event.setBurnTime(burnTime);
//    }
//}
