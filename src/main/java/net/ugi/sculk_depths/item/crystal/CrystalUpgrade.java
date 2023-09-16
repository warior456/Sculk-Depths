package net.ugi.sculk_depths.item.crystal;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.ugi.sculk_depths.SculkDepths;
import net.ugi.sculk_depths.block.enums.CrystalType;
import net.ugi.sculk_depths.item.ModItems;
import net.ugi.sculk_depths.tags.ModTags;

import java.util.Arrays;
import java.util.List;


public class CrystalUpgrade extends Item {


    static int[] crystalUpgradeColorArray = {16777215, 65526, 16742144, 1703680};
    static List<String> crystalItemNbtList = Arrays.asList("\"white\"", "\"blue\"", "\"orange\"", "\"lime\"");


    public CrystalUpgrade(Settings settings) {
        super(settings);
    }

    public static ActionResult createCrystalUpgrade(ItemStack stack, PlayerEntity player, CrystalType crystal) {
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
                    int i = crystalItemNbtList.indexOf(nbtData.toString());
                    int crystalUpgradeColor = crystalUpgradeColorArray[i];


                    tooltip.add(1, Text.translatable("tooltip.sculk_depths.crystal_upgrade.tooltip").formatted(Formatting.GRAY));

                    MutableText crystalTooltipText = Text.translatable("tooltip.sculk_depths.crystal_upgrade.crystal." + nbtData);
                    crystalTooltipText.setStyle(crystalTooltipText.getStyle().withColor(crystalUpgradeColor));
                    tooltip.add(2, crystalTooltipText);

                }
            }

        });
    }
    public void inventoryTick(ItemStack stack, World world, Entity entity,int slot, boolean selected) {
        if(!world.isClient()){
            if (entity instanceof PlayerEntity player){
                //mainhand
                ItemStack mainHandItem = player.getEquippedStack(EquipmentSlot.OFFHAND);
                if (mainHandItem.getItem() == ModItems.QUAZARITH_AXE){
                    if (mainHandItem.getNbt().get("sculk_depths.crystal") != null){
                        if (mainHandItem.getNbt().get("sculk_depths.crystal").toString().equals("\"white\"")) {
                            int i = player.getStatusEffect(StatusEffects.STRENGTH) != null ?
                                    player.getStatusEffect(StatusEffects.STRENGTH).getAmplifier() + 1 : 0;
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 0, i, false, false));
                        }
                    }
                }

                //offhand
                ItemStack offHandItem = player.getEquippedStack(EquipmentSlot.OFFHAND);
                if (offHandItem.getItem() == ModItems.QUAZARITH_AXE){
                    if (offHandItem.getNbt().get("sculk_depths.crystal") != null){
                        if (offHandItem.getNbt().get("sculk_depths.crystal").toString().equals("\"white\"")) {
                            int i = player.getStatusEffect(StatusEffects.STRENGTH) != null ?
                                    player.getStatusEffect(StatusEffects.STRENGTH).getAmplifier() + 1 : 0;
                            player.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 0, i, false, false));
                        }
                    }
                }
            }

        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}