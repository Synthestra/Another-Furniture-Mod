package com.starfish_studios.another_furniture.registry.fabric;

import com.starfish_studios.another_furniture.AnotherFurniture;
import com.starfish_studios.another_furniture.registry.AFRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;

public class AFRegistryImpl {

    public static <T extends Block> Supplier<T> registerBlock(String name, Supplier<T> block) {
        var registry = Registry.register(BuiltInRegistries.BLOCK, AnotherFurniture.res(name), block.get());
        return () -> registry;
    }

    public static <T extends Item> Supplier<T> registerItem(String name, Supplier<T> item, String tab_id) {
        var registry = Registry.register(BuiltInRegistries.ITEM, AnotherFurniture.res(name), item.get());
        itemList.add(registry.getDefaultInstance());
        return () -> registry;
    }

    public static <T extends SoundEvent> Supplier<T> registerSoundEvent(String name, Supplier<T> soundEvent) {
        var registry = Registry.register(BuiltInRegistries.SOUND_EVENT, AnotherFurniture.res(name), soundEvent.get());
        return () -> registry;
    }

    public static <T extends Entity> Supplier<EntityType<T>> registerEntityType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height) {
        var registry = Registry.register(BuiltInRegistries.ENTITY_TYPE, AnotherFurniture.res(name), EntityType.Builder.of(factory, category).sized(width, height).build());
        return () -> registry;
    }

    public static <T extends BlockEntityType<E>, E extends BlockEntity> Supplier<T> registerBlockEntityType(String name, Supplier<T> blockEntity) {
        var registry = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, AnotherFurniture.res(name), blockEntity.get());
        return () -> registry;
    }

    public static <T extends BlockEntity> BlockEntityType<T> createBlockEntityType(AFRegistry.BlockEntitySupplier<T> blockEntity, Block... validBlocks) {
        return BlockEntityType.Builder.of(blockEntity::create, validBlocks).build();
    }

    public static <T extends Entity> void registerEntityRenderers(Supplier<EntityType<T>> type, EntityRendererProvider<T> renderProvider) {
        EntityRendererRegistry.register(type.get(), renderProvider);
    }

    public static <T extends BlockEntity> void registerBlockEntityRenderer(Supplier<BlockEntityType<T>> type, BlockEntityRendererProvider<T> renderProvider) {
        BlockEntityRenderers.register(type.get(), renderProvider);
    }

    public static <T extends Block> void setFlammable(Block fireBlock, Supplier<T> block, int encouragement, int flammability) {
        FlammableBlockRegistry.getInstance(fireBlock).add(block.get(), encouragement, flammability);
    }

    public static boolean isModLoaded(String mod) {
        return FabricLoader.getInstance().isModLoaded(mod);
    }

    public static boolean isFakePlayer(Player player) {
        return player instanceof ServerPlayer && player.getClass() != ServerPlayer.class;
    }

    // TODO this is a really unclean implementation
    static List<ItemStack> itemList = new ArrayList<>();
    public static Collection<ItemStack> getAllModItems() {
        return itemList;
    }
}
