package com.starfish_studios.another_furniture.registry;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

public class AFFurnaceFuelRegistry {
    public static void init() {
        register(AFItemTags.BENCHES, 300);
        register(AFItemTags.CHAIRS, 300);
        register(AFItemTags.CURTAINS, 300);
        register(AFItemTags.DRAWERS, 300);
        register(AFItemTags.GRANDFATHER_CLOCKS, 300);
        register(AFItemTags.LAMPS, 300);
        register(AFItemTags.LATTICES, 300);
        register(AFItemTags.PLANTER_BOXES, 300);
        register(AFItemTags.SHELVES, 300);
        register(AFItemTags.SHUTTERS, 300);
        register(AFItemTags.SOFAS, 300);
        register(AFItemTags.STOOLS, 300);
        register(AFItemTags.TABLES, 300);
        register(AFItemTags.TALL_STOOLS, 300);
    }

    @ExpectPlatform
    public static void register(ItemLike item, int burnTicks) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void register(TagKey<Item> tag, int burnTicks) {
        throw new AssertionError();
    }
}
