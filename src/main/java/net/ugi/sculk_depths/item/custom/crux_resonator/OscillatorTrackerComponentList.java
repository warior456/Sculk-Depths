package net.ugi.sculk_depths.item.custom.crux_resonator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.tooltip.TooltipAppender;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.dynamic.Codecs;

import java.util.List;
import java.util.function.Consumer;

public record OscillatorTrackerComponentList(int selectedLocation, List<OscillatorTrackerComponent> trackers) implements TooltipAppender {
    public static final int MAX_EXPLOSIONS = 256;
    public static final Codec<OscillatorTrackerComponentList> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            Codecs.POSITIVE_INT.optionalFieldOf("selected_location", 0).forGetter(OscillatorTrackerComponentList::selectedLocation),
                            OscillatorTrackerComponent.CODEC.sizeLimitedListOf(256).optionalFieldOf("trackers", List.of()).forGetter(OscillatorTrackerComponentList::trackers)
                    )
                    .apply(instance, OscillatorTrackerComponentList::new)
    );
    public static final PacketCodec<ByteBuf, OscillatorTrackerComponentList> PACKET_CODEC = PacketCodec.tuple(
            PacketCodecs.VAR_INT,
            OscillatorTrackerComponentList::selectedLocation,
            OscillatorTrackerComponent.PACKET_CODEC.collect(PacketCodecs.toList(256)), //todo set lower limit
            OscillatorTrackerComponentList::trackers,
            OscillatorTrackerComponentList::new
    );

    public OscillatorTrackerComponentList(int selectedLocation, List<OscillatorTrackerComponent> trackers) {
        if (trackers.size() > 256) {
            throw new IllegalArgumentException("Got " + trackers.size() + " explosions, but maximum is 256");
        } else {
            this.selectedLocation = selectedLocation;
            this.trackers = trackers;
        }
    }

/*    public OscillatorTrackerComponentList with(OscillatorTrackerComponent tracker) {
        return new OscillatorTrackerComponentList(Util.withAppended(this.trackers, tracker));
    }*/

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {

        tooltip.accept(Text.translatable("tooltip.sculk_depths.crux_resonator.selected_location").append(ScreenTexts.SPACE).append(String.valueOf(this.selectedLocation)).formatted(Formatting.GRAY));



        for (OscillatorTrackerComponent oscillatorTrackerComponent : this.trackers) {
            oscillatorTrackerComponent.appendOptionalTooltip(text -> tooltip.accept(Text.literal("  ").append(text)));
        }
    }


}

