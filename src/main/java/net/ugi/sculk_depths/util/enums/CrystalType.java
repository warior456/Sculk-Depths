package net.ugi.sculk_depths.util.enums;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import net.ugi.sculk_depths.item.ModItems;

import java.util.function.IntFunction;

public enum CrystalType implements StringIdentifiable {
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
}

