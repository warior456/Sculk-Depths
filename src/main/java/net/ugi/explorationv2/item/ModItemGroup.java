package net.ugi.explorationv2.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.ugi.explorationv2.ExplorationV2;

public class ModItemGroup {
    public static ItemGroup EXPLORATIONV2;
    public static void registerItemgroups(){
        EXPLORATIONV2 = FabricItemGroup.builder(new Identifier(ExplorationV2.MOD_ID, "explorationv2"))
                .displayName(Text.translatable("itemgroup.explorationv2"))
                .icon(() -> new ItemStack(ModItems.CITRINE)).build();
    }
}
