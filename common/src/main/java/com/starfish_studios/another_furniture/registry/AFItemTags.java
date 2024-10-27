package com.starfish_studios.another_furniture.registry;

import com.starfish_studios.another_furniture.AnotherFurniture;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class AFItemTags {

    public static final TagKey<Item> BENCHES = itemTag("benches");
    public static final TagKey<Item> CHAIRS = itemTag("chairs");
    public static final TagKey<Item> CURTAINS = itemTag("curtains");
    public static final TagKey<Item> DRAWERS = itemTag("drawers");
    public static final TagKey<Item> GRANDFATHER_CLOCKS = itemTag("grandfather_clocks");
    public static final TagKey<Item> LAMPS = itemTag("lamps");
    public static final TagKey<Item> PLANTER_BOXES = itemTag("planter_boxes");
    public static final TagKey<Item> SHELVES = itemTag("shelves");
    public static final TagKey<Item> SHUTTERS = itemTag("shutters");
    public static final TagKey<Item> SOFAS = itemTag("sofas");
    public static final TagKey<Item> STOOLS = itemTag("stools");
    public static final TagKey<Item> TABLES = itemTag("tables");
    public static final TagKey<Item> TALL_STOOLS = itemTag("tall_stools");

    public static final TagKey<Item> FLOWER_BOX_PLACEABLES = itemTag("flower_box_placeables");
    public static final TagKey<Item> FLOWER_BOX_BANNED = itemTag("flower_box_banned");
    public static final TagKey<Item> FURNITURE_HAMMER = itemTag("furniture_hammers");

    private static TagKey<Item> itemTag(String name) {
        return TagKey.create(Registries.ITEM, AnotherFurniture.res(name));
    }

    public static void init() {}
}
