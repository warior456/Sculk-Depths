package net.ugi.sculk_depths.item.crystal;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.block.BlockState;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.enums.CrystalType;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.tags.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;


public class CrystalArmor extends Item {
    public CrystalArmor(Settings settings) {
        super(settings);
    }
    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if(!context.getWorld().isClient()) {
            BlockPos positionClicked = context.getBlockPos();
            PlayerEntity player = context.getPlayer();
            boolean foundBlock = false;

            for(int i = 0; i <= positionClicked.getY() + 64; i++) {
                BlockState state = context.getWorld().getBlockState(positionClicked.down(i));

            }

            if(!foundBlock) {
                player.sendMessage(Text.literal("No Valuables Found!"));
            }
        }
        return ActionResult.SUCCESS;
    }


    public static ActionResult createCrystalArmor(ItemStack stack, PlayerEntity player, CrystalType crystal) {
        addNbtToCrystalArmor(stack, player, crystal);
        tooltipAdd();

        return ActionResult.SUCCESS;
    }




    public static void addNbtToCrystalArmor(ItemStack stack, PlayerEntity player, CrystalType crystal) {

        NbtCompound nbtData = new NbtCompound();
        nbtData.putString("sculk_depths.crystal", crystal.toString());

        stack.setNbt(nbtData);

    }


    /*public static void tooltipAdd() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (stack.getItem() == ModItems.THE_HIEROPHANT) {
                NbtCompound nbtData = stack.getNbt();
                if (nbtData != null) {
                    lines.add(1, Text.literal("Total EXP: " + nbtData.getInt("TotalExp") + "/10000")
                            .formatted(Formatting.GREEN));
                    lines.add(2, Text.literal("Mode: ")
                            .formatted(Formatting.GREEN)
                            .append(Text.literal(TheHierophant.modes[nbtData.getInt("Mode")])
                                    .formatted(Formatting.BLUE)));
                }
            }
        });
    }*/

    public static void appendTooltip(ItemStack stack, DynamicRegistryManager registryManager, List<Text> tooltip) {
        Optional<ArmorTrim> optional = ArmorTrim.getTrim(registryManager, stack);
        if (optional.isPresent()) {
            ArmorTrim armorTrim = optional.get();
            tooltip.add(1,Text.translatable("tooltip.sculk_depths.crystal_armor.tooltip").formatted(Formatting.GREEN));
        }
    }
    public static void tooltipAdd() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (stack.isIn(ModTags.Items.CRYSTAL_ARMOR)) {
                NbtCompound nbtData = stack.getNbt();
                if (nbtData != null) {
                    lines.add(1,Text.translatable("tooltip.sculk_depths.crystal_armor.tooltip").formatted(Formatting.GREEN));

                }
            }
        });
    }
}