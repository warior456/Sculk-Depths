package net.ugi.sculk_depths.item.crystal;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.ugi.sculk_depths.block.enums.CrystalType;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.List;


public class CrystalUpgrade extends Item {


    public CrystalUpgrade(Settings settings) {
        super(settings);
    }

    public static ActionResult createCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal, World world, CrystalType[] crystalStateArray, List<String> crystalItemNbt) {

        addNbtToCrystalUpgrade(stack, player, crystal);

        return ActionResult.SUCCESS;
    }

    public static void addNbtToCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal) {

        NbtCompound nbtData = stack.getNbt();
        nbtData.putString("sculk_depths.crystal", crystal.toString());

        stack.setNbt(nbtData);

    }

    public static void tooltipAdd() {

        ItemTooltipCallback.EVENT.register((stack, context, tooltip) -> {
            if (stack.isIn(ModTags.Items.CRYSTAL_UPGRADE_ITEMS)) {
                NbtElement nbtData = stack.getNbt().get("sculk_depths.crystal");
                if (nbtData != null) {
                    tooltip.add(1, Text.translatable("tooltip.sculk_depths.crystal_upgrade.tooltip").formatted(Formatting.GREEN));
                    tooltip.add(2, Text.translatable("tooltip.sculk_depths.crystal_upgrade.crystal." + nbtData).formatted(Formatting.BLUE));
                    tooltip.add(3, Text.literal(""));
                }
            }

        });
    }
}