package net.ugi.sculk_depths.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.ModBlocks;

public class ModItemGroup {
    public static ItemGroup SCULK_DEPTHS;
    public static void registerItemgroups(){
        SCULK_DEPTHS = FabricItemGroup.builder(new Identifier(SculkDepths.MOD_ID, "sculk_depths"))
                .displayName(Text.translatable("itemgroup.sculk_depths"))
                .icon(() -> new ItemStack(ModBlocks.UMBRUSK)).build();
    }
}
