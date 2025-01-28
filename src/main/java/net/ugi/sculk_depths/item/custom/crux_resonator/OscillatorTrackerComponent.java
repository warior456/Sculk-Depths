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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.poi.PointOfInterestTypes;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.util.POIs;

import java.util.Optional;
import java.util.function.Consumer;

public record OscillatorTrackerComponent(Optional<GlobalPos> target, boolean tracked, String name) implements TooltipAppender {
    public static final Codec<OscillatorTrackerComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            GlobalPos.CODEC.optionalFieldOf("target").forGetter(OscillatorTrackerComponent::target),
                            Codec.BOOL.optionalFieldOf("tracked", Boolean.valueOf(true)).forGetter(OscillatorTrackerComponent::tracked),
                            Codec.STRING.optionalFieldOf("name","").forGetter(OscillatorTrackerComponent::name)
                    )
                    .apply(instance, OscillatorTrackerComponent::new)
    );
    public static final PacketCodec<ByteBuf, OscillatorTrackerComponent> PACKET_CODEC = PacketCodec.tuple(
            GlobalPos.PACKET_CODEC.collect(PacketCodecs::optional),
            OscillatorTrackerComponent::target,
            PacketCodecs.BOOL,
            OscillatorTrackerComponent::tracked,
            PacketCodecs.STRING,
            OscillatorTrackerComponent::name,
            OscillatorTrackerComponent::new
    );

    public OscillatorTrackerComponent forWorld(ServerWorld world) {
        if (this.tracked && !this.target.isEmpty()) {
            if (((GlobalPos)this.target.get()).dimension() != world.getRegistryKey()) {
                return this;
            } else {
                BlockPos blockPos = ((GlobalPos)this.target.get()).pos();
                return world.isInBuildLimit(blockPos) && world.getPointOfInterestStorage().hasTypeAt(POIs.QUAZARITH_OSCILLATOR_POI, blockPos)
                        ? this
                        : new OscillatorTrackerComponent(Optional.empty(), true, "");
            }
        } else {
            return this;
        }
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Text> tooltip, TooltipType type) {
        this.appendShapeTooltip(tooltip);
        this.appendOptionalTooltip(tooltip);
    }

    public void appendShapeTooltip(Consumer<Text> textConsumer) {
        //textConsumer.accept(Text.of("hello1"));
    }

    public void appendOptionalTooltip(Consumer<Text> textConsumer) {
        this.target.ifPresent(pos -> {
            textConsumer.accept(Text.of("target: " + pos.pos()));
        });
    }


}