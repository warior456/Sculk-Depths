package net.ugi.sculk_depths.item.custom.crux_resonator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;
import net.minecraft.util.dynamic.Codecs;

import java.util.List;
import java.util.function.Consumer;

public record OscillatorTrackerComponentList(int flightDuration, List<OscillatorTrackerComponent> trackers) implements TooltipAppender {
    public static final int MAX_EXPLOSIONS = 256;
    public static final Codec<OscillatorTrackerComponentList> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codecs.UNSIGNED_BYTE.optionalFieldOf("flight_duration", 0).forGetter(OscillatorTrackerComponentList::flightDuration),
                            OscillatorTrackerComponent.CODEC.sizeLimitedListOf(256).optionalFieldOf("trackers", List.of()).forGetter(OscillatorTrackerComponentList::trackers)
                    )
                    .apply(instance, OscillatorTrackerComponentList::new)
    );
    public static final PacketCodec<ByteBuf, OscillatorTrackerComponentList> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            OscillatorTrackerComponentList::flightDuration,
            OscillatorTrackerComponent.PACKET_CODEC.collect(PacketCodecs.toList(256)), //todo eeee
            OscillatorTrackerComponentList::trackers,
            OscillatorTrackerComponentList::new
    );

    public OscillatorTrackerComponentList(int flightDuration, List<OscillatorTrackerComponent> trackers) {
        if (trackers.size() > 256) {
            throw new IllegalArgumentException("Got " + trackers.size() + " explosions, but maximum is 256");
        } else {
            this.flightDuration = flightDuration;
            this.trackers = trackers();
        }
    }

/*    public OscillatorTrackerComponentList with(OscillatorTrackerComponent tracker) {
        return new OscillatorTrackerComponentList(Util.withAppended(this.trackers, tracker));
    }*/

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        if (this.flightDuration > 0) {
            tooltip.accept(
                    Text.translatable("item.minecraft.firework_rocket.flight").append(ScreenTexts.SPACE).append(String.valueOf(this.flightDuration)).formatted(Formatting.GRAY)
            );
        }

        for (OscillatorTrackerComponent oscillatorTrackerComponent : this.trackers) {
/*            oscillatorTrackerComponent.appendShapeTooltip(tooltip);
            oscillatorTrackerComponent.appendOptionalTooltip(text -> tooltip.accept(Text.literal("  ").append(text)));*/
        }
    }
}

