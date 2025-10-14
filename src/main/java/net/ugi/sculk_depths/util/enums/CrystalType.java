package net.ugi.sculk_depths.util.enums;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import net.ugi.sculk_depths.item.ModItems;

import java.util.function.Consumer;
import java.util.function.IntFunction;

public enum CrystalType implements StringIdentifiable, TooltipAppender {
    NONE("none", null),
    WHITE("white", ModItems.WHITE_CRYSTAL),
    BLUE("blue", ModItems.BLUE_CRYSTAL),
    ORANGE("orange", ModItems.ORANGE_CRYSTAL),
    LIME("lime", ModItems.LIME_CRYSTAL);

    public static final Codec<CrystalType> CODEC = StringIdentifiable.createCodec(CrystalType::values);
    public static final IntFunction<CrystalType> ID_TO_VALUE = ValueLists.<CrystalType>createIdToValueFunction(Enum::ordinal, values(), ValueLists.OutOfBoundsHandling.ZERO);
    public static final PacketCodec<ByteBuf, CrystalType> PACKET_CODEC = PacketCodecs.indexed(ID_TO_VALUE, Enum::ordinal);

    private final String name;
    private final Item item;

    private CrystalType(String name, Item item) {
        this.name = name;
        this.item = item;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {

        int color = switch (this) {
            case WHITE -> 0xFFFFFF;
            case BLUE -> 0x00FFF6;
            case ORANGE -> 0xFF7700;
            case LIME -> 0x19FF00;
            default -> -1;
        };

        tooltip.accept(Text.translatable("tooltip.sculk_depths.crystal_upgrade.tooltip").formatted(Formatting.GRAY));

        MutableText crystalTooltipText = Text.translatable("tooltip.sculk_depths.crystal_upgrade.crystal." + this.name);
        crystalTooltipText.setStyle(crystalTooltipText.getStyle().withColor(color));
        tooltip.accept(crystalTooltipText);
    }
}

