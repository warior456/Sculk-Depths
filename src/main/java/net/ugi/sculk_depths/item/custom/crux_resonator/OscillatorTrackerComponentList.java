package net.ugi.sculk_depths.item.custom.crux_resonator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipProvider;

import java.util.List;
import java.util.function.Consumer;

public record OscillatorTrackerComponentList(int selectedLocation, List<OscillatorTrackerComponent> trackers) implements TooltipProvider {
    public static final int MAX_EXPLOSIONS = 256;
    public static final Codec<OscillatorTrackerComponentList> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            ExtraCodecs.NON_NEGATIVE_INT.optionalFieldOf("selected_location", 0).forGetter(OscillatorTrackerComponentList::selectedLocation),
                            OscillatorTrackerComponent.CODEC.sizeLimitedListOf(256).optionalFieldOf("trackers", List.of()).forGetter(OscillatorTrackerComponentList::trackers)
                    )
                    .apply(instance, OscillatorTrackerComponentList::new)
    );
    public static final StreamCodec<ByteBuf, OscillatorTrackerComponentList> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            OscillatorTrackerComponentList::selectedLocation,
            OscillatorTrackerComponent.PACKET_CODEC.apply(ByteBufCodecs.list(256)),
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

    @Override
    public void addToTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
        tooltip.accept(Component.translatable("tooltip.sculk_depths.crux_resonator.selected_location").append(CommonComponents.SPACE).append(String.valueOf(this.selectedLocation)).withStyle(ChatFormatting.GRAY));

        for (OscillatorTrackerComponent oscillatorTrackerComponent : this.trackers) {
            oscillatorTrackerComponent.addAdditionalTooltip(text -> tooltip.accept(Component.literal("  ").append(text)));
        }
    }
}
