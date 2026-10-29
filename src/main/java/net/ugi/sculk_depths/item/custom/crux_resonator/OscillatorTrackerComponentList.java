package net.ugi.sculk_depths.item.custom.crux_resonator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.util.ExtraCodecs;

import java.util.List;
import java.util.function.Consumer;

public record OscillatorTrackerComponentList(int selectedLocation, List<OscillatorTrackerComponent> trackers) implements TooltipProvider {
    public static final int MAX_EXPLOSIONS = 256;
    public static final Codec<OscillatorTrackerComponentList> CODEC = RecordCodecBuilder.create(
            instance -> instance.recipeGroupPrefix(
                            ExtraCodecs.POSITIVE_INT.optionalFieldOf("selected_location", 0).forGetter(OscillatorTrackerComponentList::selectedLocation),
                            OscillatorTrackerComponent.CODEC.sizeLimitedListOf(256).optionalFieldOf("trackers", List.of()).forGetter(OscillatorTrackerComponentList::trackers)
                    )
                    .apply(instance, OscillatorTrackerComponentList::new)
    );
    public static final StreamCodec<ByteBuf, OscillatorTrackerComponentList> PACKET_CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            OscillatorTrackerComponentList::selectedLocation,
            OscillatorTrackerComponent.PACKET_CODEC.collect(ByteBufCodecs.toList(256)), //todo set lower limit
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
    public void appendTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {

        tooltip.accept(Component.translatable("tooltip.sculk_depths.crux_resonator.selected_location").append(CommonComponents.ELEMENT_SPACING).append(String.valueOf(this.selectedLocation)).formatted(ChatFormatting.COLOR_GRAY));



        for (OscillatorTrackerComponent oscillatorTrackerComponent : this.trackers) {
            oscillatorTrackerComponent.addAdditionalTooltip(text -> tooltip.accept(Component.literal("  ").append(text)));
        }
    }


}

