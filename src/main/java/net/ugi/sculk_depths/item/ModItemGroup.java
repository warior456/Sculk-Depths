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
    public static final RegistryKey<ItemGroup> SCULK_DEPTHS = RegistryKey.of(RegistryKeys.ITEM_GROUP, SculkDepths.identifier( "sculk_depths"));

    public static void registerItemgroups() {


        Registry.register(Registries.ITEM_GROUP, SCULK_DEPTHS, FabricItemGroup.builder()
                .icon(() -> new ItemStack(ModBlocks.UMBRUSK))
                .displayName(Text.translatable("itemgroup.sculk_depths"))
                .build());
    }
}
