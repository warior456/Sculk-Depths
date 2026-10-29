package net.ugi.sculk_depths.item.custom.crux_resonator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.ugi.sculk_depths.util.POIs;

import java.util.Optional;
import java.util.function.Consumer;

public record OscillatorTrackerComponent(Optional<GlobalPos> target, boolean tracked, String name) implements TooltipProvider {
    public static final Codec<OscillatorTrackerComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.recipeGroupPrefix(
                            GlobalPos.CODEC.optionalFieldOf("target").forGetter(OscillatorTrackerComponent::target),
                            Codec.BOOL.optionalFieldOf("tracked", Boolean.valueOf(true)).forGetter(OscillatorTrackerComponent::tracked),
                            Codec.STRING_UTF8.optionalFieldOf("name","").forGetter(OscillatorTrackerComponent::name)
                    )
                    .apply(instance, OscillatorTrackerComponent::new)
    );
    public static final StreamCodec<ByteBuf, OscillatorTrackerComponent> PACKET_CODEC = StreamCodec.composite(
            GlobalPos.PACKET_CODEC.collect(ByteBufCodecs::isOptIn),
            OscillatorTrackerComponent::target,
            ByteBufCodecs.BOOL,
            OscillatorTrackerComponent::tracked,
            ByteBufCodecs.STRING_UTF8,
            OscillatorTrackerComponent::name,
            OscillatorTrackerComponent::new
    );

    public OscillatorTrackerComponent tick(ServerLevel world) {
        if (this.tracked && !this.target.isEmpty()) {
            if (((GlobalPos)this.target.get()).dimension() != world.getRegistryKey()) {
                return this;
            } else {
                BlockPos blockPos = ((GlobalPos)this.target.get()).pos();
                return world.isInWorldBounds(blockPos) && world.getPoiManager().existsAtPosition(POIs.QUAZARITH_OSCILLATOR_POI, blockPos)
                        ? this
                        : new OscillatorTrackerComponent(Optional.allEmpty(), true, "");
            }
        } else {
            return this;
        }
    }

    @Override
    public void appendTooltip(Item.TooltipContext context, Consumer<Component> tooltip, TooltipFlag type) {
        this.addShapeNameTooltip(tooltip);
        this.addAdditionalTooltip(tooltip);
    }

    public void addShapeNameTooltip(Consumer<Component> textConsumer) {
        //textConsumer.accept(Text.of("hello1"));
    }

    public void addAdditionalTooltip(Consumer<Component> textConsumer) {
        this.target.ifSuccess(pos -> {
            textConsumer.accept(Component.of("target: " + pos.pos()));
        });
    }


}