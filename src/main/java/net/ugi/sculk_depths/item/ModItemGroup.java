package net.ugi.sculk_depths.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;

public class ModItemGroup {
    public static final ResourceKey<CreativeModeTab> SCULK_DEPTHS_BLOCKS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, SculkDepths.id("sculk_depths_blocks"));
    public static final ResourceKey<CreativeModeTab> SCULK_DEPTHS_ITEMS = ResourceKey.create(Registries.CREATIVE_MODE_TAB, SculkDepths.id("sculk_depths_items"));

    public static void registerItemgroups() {
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SCULK_DEPTHS_BLOCKS, CreativeModeTab.builder()
                .icon(() -> new ItemStack(ModBlocks.UMBRUSK))
                .title(Component.translatable("itemgroup.sculk_depths_blocks"))
                .build());

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, SCULK_DEPTHS_ITEMS, CreativeModeTab.builder()
                .icon(() -> new ItemStack(ModItems.QUAZARITH_PIECES))
                .title(Component.translatable("itemgroup.sculk_depths_items"))
                .build());
    }
}
