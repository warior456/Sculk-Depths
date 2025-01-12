package net.ugi.sculk_depths.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;

public class ModItemGroup {
    public static final RegistryKey<ItemGroup> SCULK_DEPTHS_BLOCKS = RegistryKey.of(RegistryKeys.ITEM_GROUP, SculkDepths.identifier( "sculk_depths_blocks"));
    public static final RegistryKey<ItemGroup> SCULK_DEPTHS_ITEMS = RegistryKey.of(RegistryKeys.ITEM_GROUP, SculkDepths.identifier( "sculk_depths_items"));

    public static void registerItemgroups() {


        Registry.register(Registries.ITEM_GROUP, SCULK_DEPTHS_BLOCKS, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.UMBRUSK))
                .displayName(Text.translatable("itemgroup.sculk_depths_blocks"))
                .build());

        Registry.register(Registries.ITEM_GROUP, SCULK_DEPTHS_ITEMS, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModItems.QUAZARITH_PIECES))
                .displayName(Text.translatable("itemgroup.sculk_depths_items"))
                .build());
    }
}

