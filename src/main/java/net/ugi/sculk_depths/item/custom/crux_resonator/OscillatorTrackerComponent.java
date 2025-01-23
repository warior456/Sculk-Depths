package net.ugi.sculk_depths.item.custom.crux_resonator;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.poi.PointOfInterestTypes;
import net.ugi.sculk_depths.block.ModBlocks;
import net.ugi.sculk_depths.util.POIs;

import java.util.Optional;

public record OscillatorTrackerComponent(Optional<GlobalPos> target, boolean tracked) {
    public static final Codec<OscillatorTrackerComponent> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                            GlobalPos.CODEC.optionalFieldOf("target").forGetter(OscillatorTrackerComponent::target),
                            Codec.BOOL.optionalFieldOf("tracked", Boolean.valueOf(true)).forGetter(OscillatorTrackerComponent::tracked)
                    )
                    .apply(instance, OscillatorTrackerComponent::new)
    );
    public static final PacketCodec<ByteBuf, OscillatorTrackerComponent> PACKET_CODEC = PacketCodec.tuple(
            GlobalPos.PACKET_CODEC.collect(PacketCodecs::optional),
            OscillatorTrackerComponent::target,
            PacketCodecs.BOOL,
            OscillatorTrackerComponent::tracked,
            OscillatorTrackerComponent::new
    );

    public OscillatorTrackerComponent forWorld(ServerWorld world) {
        //todo fix this
        if (this.tracked && !this.target.isEmpty()) {
            if (((GlobalPos)this.target.get()).dimension() != world.getRegistryKey()) {
                return this;
            } else {
                BlockPos blockPos = ((GlobalPos)this.target.get()).pos();
                return world.isInBuildLimit(blockPos) && world.getBlockState(blockPos).isOf(ModBlocks.QUAZARITH_OSCILLATOR)/*&& world.getPointOfInterestStorage().hasTypeAt(POIs.QUAZARITH_OSCILLATOR_POI, blockPos)*/
                        ? this
                        : new OscillatorTrackerComponent(Optional.empty(), true);
            }
        } else {
            return this;
        }
    }
}