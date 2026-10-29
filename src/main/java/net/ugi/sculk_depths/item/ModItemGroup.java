package net.ugi.sculk_depths.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;

public class ModItemGroup {
    public static final ResourceKey<CreativeModeTab> SCULK_DEPTHS_BLOCKS = ResourceKey.of(Registries.CREATIVE_MODE_TAB, SculkDepths.id( "sculk_depths_blocks"));
    public static final ResourceKey<CreativeModeTab> SCULK_DEPTHS_ITEMS = ResourceKey.of(Registries.CREATIVE_MODE_TAB, SculkDepths.id( "sculk_depths_items"));

    public static void registerItemgroups() {


        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SCULK_DEPTHS_BLOCKS, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.UMBRUSK))
                .title(Component.translatable("itemgroup.sculk_depths_blocks"))
                .build());

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SCULK_DEPTHS_ITEMS, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModItems.QUAZARITH_PIECES))
                .title(Component.translatable("itemgroup.sculk_depths_items"))
                .build());
    }
}

