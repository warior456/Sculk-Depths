package net.ugi.sculk_depths.entity.custom.multipart;

import com.google.common.collect.Lists;
import com.mojang.logging.LogUtils;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.ai.pathing.PathMinHeap;
import net.minecraft.entity.ai.pathing.PathNode;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.boss.dragon.phase.Phase;
import net.minecraft.entity.boss.dragon.phase.PhaseManager;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.WorldEvents;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.gen.feature.EndPortalFeature;
import net.ugi.sculk_depths.entity.ModEntities;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class MultipartEntity extends PathAwareEntity {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final TargetPredicate CLOSE_PLAYER_PREDICATE = TargetPredicate.createAttackable().setBaseMaxDistance(64.0);

    /**
     * The damage the dragon can take before it takes off, represented as a ratio to the full health.
     */

    private static final String DRAGON_DEATH_TIME_KEY = "DragonDeathTime";

    /**
     * (yaw, y, ?)
     */
    public final double[][] segmentCircularBuffer = new double[64][3];
    public int latestSegment = -1;
    private final EntityPart[] parts;
    public final EntityPart head;
    private final EntityPart neck;
    private final EntityPart body;
    private final EntityPart tail1;
    private final EntityPart tail2;
    private final EntityPart tail3;
    private final EntityPart rightWing;
    private final EntityPart leftWing;
    public float prevWingPosition;
    public float wingPosition;
    public boolean slowedDownByBlock;
    public int ticksSinceDeath;
    public float yawAcceleration;
    @Nullable
    public EndCrystalEntity connectedCrystal;
    @Nullable
    private EnderDragonFight fight;
    private BlockPos fightOrigin = BlockPos.ORIGIN;
    private int ticksUntilNextGrowl = 100;
    private float damageDuringSitting;
    /**
     * The first 12 path nodes are used for end crystals; the others are not tied to them.
     */
    private final PathNode[] pathNodes = new PathNode[24];
    /**
     * An array of 24 bitflags, where node #i leads to #j if and only if
     * {@code (pathNodeConnections[i] & (1 << j)) != 0}.
     */
    private final int[] pathNodeConnections = new int[24];
    private final PathMinHeap pathHeap = new PathMinHeap();

    public MultipartEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super((EntityType<? extends PathAwareEntity>) ModEntities.CHOMPER_COLOSSUS, world);
        this.head = new EntityPart(this, "head", 1.0f, 1.0f);
        this.neck = new EntityPart(this, "neck", 3.0f, 3.0f);
        this.body = new EntityPart(this, "body", 5.0f, 3.0f);
        this.tail1 = new EntityPart(this, "tail", 2.0f, 2.0f);
        this.tail2 = new EntityPart(this, "tail", 2.0f, 2.0f);
        this.tail3 = new EntityPart(this, "tail", 2.0f, 2.0f);
        this.rightWing = new EntityPart(this, "wing", 4.0f, 2.0f);
        this.leftWing = new EntityPart(this, "wing", 4.0f, 2.0f);
        this.parts = new EntityPart[]{this.head, this.neck, this.body, this.tail1, this.tail2, this.tail3, this.rightWing, this.leftWing};
        this.setHealth(this.getMaxHealth());
        this.noClip = true;
        this.ignoreCameraFrustum = true;
    }

    public void setFight(EnderDragonFight fight) {
        this.fight = fight;
    }

    public void setFightOrigin(BlockPos fightOrigin) {
        this.fightOrigin = fightOrigin;
    }

    public BlockPos getFightOrigin() {
        return this.fightOrigin;
    }

    public static DefaultAttributeContainer.Builder createEnderDragonAttributes() {
        return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 200.0);
    }

    @Override
    public boolean isFlappingWings() {
        float f = MathHelper.cos(this.wingPosition * ((float)Math.PI * 2));
        float g = MathHelper.cos(this.prevWingPosition * ((float)Math.PI * 2));
        return g <= -0.3f && f >= -0.3f;
    }

    @Override
    public void addFlapEffects() {
        if (this.getWorld().isClient && !this.isSilent()) {
            this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ENDER_DRAGON_FLAP, this.getSoundCategory(), 5.0f, 0.8f + this.random.nextFloat() * 0.3f, false);
        }
    }


    public double[] getSegmentProperties(int segmentNumber, float tickDelta) {
        if (this.isDead()) {
            tickDelta = 0.0f;
        }
        tickDelta = 1.0f - tickDelta;
        int i = this.latestSegment - segmentNumber & 0x3F;
        int j = this.latestSegment - segmentNumber - 1 & 0x3F;
        double[] ds = new double[3];
        double d = this.segmentCircularBuffer[i][0];
        double e = MathHelper.wrapDegrees(this.segmentCircularBuffer[j][0] - d);
        ds[0] = d + e * (double)tickDelta;
        d = this.segmentCircularBuffer[i][1];
        e = this.segmentCircularBuffer[j][1] - d;
        ds[1] = d + e * (double)tickDelta;
        ds[2] = MathHelper.lerp((double)tickDelta, this.segmentCircularBuffer[i][2], this.segmentCircularBuffer[j][2]);
        return ds;
    }

    @Override
    public void tickMovement() {
        int ac;
        float q;
        float p;
        float o;
        ServerWorld serverWorld;
        EnderDragonFight enderDragonFight;
        World world;
        this.addAirTravelEffects();

    }

    private void movePart(EntityPart enderDragonPart, double dx, double dy, double dz) {
        enderDragonPart.setPosition(this.getX() + dx, this.getY() + dy, this.getZ() + dz);
    }

    private float getHeadVerticalMovement() {
        double[] ds = this.getSegmentProperties(5, 1.0f);
        double[] es = this.getSegmentProperties(0, 1.0f);
        return (float)(ds[1] - es[1]);
    }

    /**
     * Things to do every tick related to end crystals. The Ender Dragon:
     *
     * * Disconnects from its crystal if it is removed
     * * If it is connected to a crystal, then heals every 10 ticks
     * * With a 1 in 10 chance each tick, searches for the nearest crystal and connects to it if present
     */
    private void tickWithEndCrystals() {
        if (this.connectedCrystal != null) {
            if (this.connectedCrystal.isRemoved()) {
                this.connectedCrystal = null;
            } else if (this.age % 10 == 0 && this.getHealth() < this.getMaxHealth()) {
                this.setHealth(this.getHealth() + 1.0f);
            }
        }
        if (this.random.nextInt(10) == 0) {
            List<EndCrystalEntity> list = this.getWorld().getNonSpectatingEntities(EndCrystalEntity.class, this.getBoundingBox().expand(32.0));
            EndCrystalEntity endCrystalEntity = null;
            double d = Double.MAX_VALUE;
            for (EndCrystalEntity endCrystalEntity2 : list) {
                double e = endCrystalEntity2.squaredDistanceTo(this);
                if (!(e < d)) continue;
                d = e;
                endCrystalEntity = endCrystalEntity2;
            }
            this.connectedCrystal = endCrystalEntity;
        }
    }

    private void launchLivingEntities(List<Entity> entities) {
        double d = (this.body.getBoundingBox().minX + this.body.getBoundingBox().maxX) / 2.0;
        double e = (this.body.getBoundingBox().minZ + this.body.getBoundingBox().maxZ) / 2.0;
        for (Entity entity : entities) {
            if (!(entity instanceof LivingEntity)) continue;
            double f = entity.getX() - d;
            double g = entity.getZ() - e;
            double h = Math.max(f * f + g * g, 0.1);
            entity.addVelocity(f / h * 4.0, 0.2f, g / h * 4.0);
            if (((LivingEntity)entity).getLastAttackedTime() >= entity.age - 2) continue;
            entity.damage(this.getDamageSources().mobAttack(this), 5.0f);
            this.applyDamageEffects(this, entity);
        }
    }

    private void damageLivingEntities(List<Entity> entities) {
        for (Entity entity : entities) {
            if (!(entity instanceof LivingEntity)) continue;
            entity.damage(this.getDamageSources().mobAttack(this), 10.0f);
            this.applyDamageEffects(this, entity);
        }
    }

    private float wrapYawChange(double yawDegrees) {
        return (float)MathHelper.wrapDegrees(yawDegrees);
    }

    private boolean destroyBlocks(Box box) {
        int i = MathHelper.floor(box.minX);
        int j = MathHelper.floor(box.minY);
        int k = MathHelper.floor(box.minZ);
        int l = MathHelper.floor(box.maxX);
        int m = MathHelper.floor(box.maxY);
        int n = MathHelper.floor(box.maxZ);
        boolean bl = false;
        boolean bl2 = false;
        for (int o = i; o <= l; ++o) {
            for (int p = j; p <= m; ++p) {
                for (int q = k; q <= n; ++q) {
                    BlockPos blockPos = new BlockPos(o, p, q);
                    BlockState blockState = this.getWorld().getBlockState(blockPos);
                    if (blockState.isAir() || blockState.isIn(BlockTags.DRAGON_TRANSPARENT)) continue;
                    if (!this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) || blockState.isIn(BlockTags.DRAGON_IMMUNE)) {
                        bl = true;
                        continue;
                    }
                    bl2 = this.getWorld().removeBlock(blockPos, false) || bl2;
                }
            }
        }
        if (bl2) {
            BlockPos blockPos2 = new BlockPos(i + this.random.nextInt(l - i + 1), j + this.random.nextInt(m - j + 1), k + this.random.nextInt(n - k + 1));
            this.getWorld().syncWorldEvent(WorldEvents.ENDER_DRAGON_BREAKS_BLOCK, blockPos2, 0);
        }
        return bl;
    }

    public boolean damagePart(EntityPart part, DamageSource source, float amount) {
        return true;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (!this.getWorld().isClient) {
            return this.damagePart(this.body, source, amount);
        }
        return false;
    }

    protected boolean parentDamage(DamageSource source, float amount) {
        return super.damage(source, amount);
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
        this.emitGameEvent(GameEvent.ENTITY_DIE);
    }

    @Override
    protected void updatePostDeath() {

        ++this.ticksSinceDeath;
        if (this.ticksSinceDeath >= 180 && this.ticksSinceDeath <= 200) {
            float f = (this.random.nextFloat() - 0.5f) * 8.0f;
            float g = (this.random.nextFloat() - 0.5f) * 4.0f;
            float h = (this.random.nextFloat() - 0.5f) * 8.0f;
            this.getWorld().addParticle(ParticleTypes.EXPLOSION_EMITTER, this.getX() + (double)f, this.getY() + 2.0 + (double)g, this.getZ() + (double)h, 0.0, 0.0, 0.0);
        }
        boolean bl = this.getWorld().getGameRules().getBoolean(GameRules.DO_MOB_LOOT);
        int i = 500;
        if (this.fight != null && !this.fight.hasPreviouslyKilled()) {
            i = 12000;
        }
        if (this.getWorld() instanceof ServerWorld) {
            if (this.ticksSinceDeath > 150 && this.ticksSinceDeath % 5 == 0 && bl) {
                ExperienceOrbEntity.spawn((ServerWorld)this.getWorld(), this.getPos(), MathHelper.floor((float)i * 0.08f));
            }
            if (this.ticksSinceDeath == 1 && !this.isSilent()) {
                this.getWorld().syncGlobalEvent(WorldEvents.ENDER_DRAGON_DIES, this.getBlockPos(), 0);
            }
        }
        this.move(MovementType.SELF, new Vec3d(0.0, 0.1f, 0.0));
        if (this.ticksSinceDeath == 200 && this.getWorld() instanceof ServerWorld) {
            if (bl) {
                ExperienceOrbEntity.spawn((ServerWorld)this.getWorld(), this.getPos(), MathHelper.floor((float)i * 0.2f));
            }
            this.remove(Entity.RemovalReason.KILLED);
            this.emitGameEvent(GameEvent.ENTITY_DIE);
        }
    }

    public int getNearestPathNodeIndex() {
        if (this.pathNodes[0] == null) {
            for (int i = 0; i < 24; ++i) {
                int m;
                int l;
                int j = 5;
                int k = i;
                if (i < 12) {
                    l = MathHelper.floor(60.0f * MathHelper.cos(2.0f * ((float)(-Math.PI) + 0.2617994f * (float)k)));
                    m = MathHelper.floor(60.0f * MathHelper.sin(2.0f * ((float)(-Math.PI) + 0.2617994f * (float)k)));
                } else if (i < 20) {
                    l = MathHelper.floor(40.0f * MathHelper.cos(2.0f * ((float)(-Math.PI) + 0.3926991f * (float)(k -= 12))));
                    m = MathHelper.floor(40.0f * MathHelper.sin(2.0f * ((float)(-Math.PI) + 0.3926991f * (float)k)));
                    j += 10;
                } else {
                    l = MathHelper.floor(20.0f * MathHelper.cos(2.0f * ((float)(-Math.PI) + 0.7853982f * (float)(k -= 20))));
                    m = MathHelper.floor(20.0f * MathHelper.sin(2.0f * ((float)(-Math.PI) + 0.7853982f * (float)k)));
                }
                int n = Math.max(this.getWorld().getSeaLevel() + 10, this.getWorld().getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(l, 0, m)).getY() + j);
                this.pathNodes[i] = new PathNode(l, n, m);
            }
            this.pathNodeConnections[0] = 6146;
            this.pathNodeConnections[1] = 8197;
            this.pathNodeConnections[2] = 8202;
            this.pathNodeConnections[3] = 16404;
            this.pathNodeConnections[4] = 32808;
            this.pathNodeConnections[5] = 32848;
            this.pathNodeConnections[6] = 65696;
            this.pathNodeConnections[7] = 131392;
            this.pathNodeConnections[8] = 131712;
            this.pathNodeConnections[9] = 263424;
            this.pathNodeConnections[10] = 526848;
            this.pathNodeConnections[11] = 525313;
            this.pathNodeConnections[12] = 1581057;
            this.pathNodeConnections[13] = 3166214;
            this.pathNodeConnections[14] = 2138120;
            this.pathNodeConnections[15] = 6373424;
            this.pathNodeConnections[16] = 4358208;
            this.pathNodeConnections[17] = 12910976;
            this.pathNodeConnections[18] = 9044480;
            this.pathNodeConnections[19] = 9706496;
            this.pathNodeConnections[20] = 15216640;
            this.pathNodeConnections[21] = 0xD0E000;
            this.pathNodeConnections[22] = 11763712;
            this.pathNodeConnections[23] = 0x7E0000;
        }
        return this.getNearestPathNodeIndex(this.getX(), this.getY(), this.getZ());
    }

    public int getNearestPathNodeIndex(double x, double y, double z) {
        float f = 10000.0f;
        int i = 0;
        PathNode pathNode = new PathNode(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
        int j = 0;
        if (this.fight == null || this.fight.getAliveEndCrystals() == 0) {
            j = 12;
        }
        for (int k = j; k < 24; ++k) {
            float g;
            if (this.pathNodes[k] == null || !((g = this.pathNodes[k].getSquaredDistance(pathNode)) < f)) continue;
            f = g;
            i = k;
        }
        return i;
    }

    @Nullable
    public Path findPath(int from, int to, @Nullable PathNode pathNode) {
        PathNode pathNode2;
        for (int i = 0; i < 24; ++i) {
            pathNode2 = this.pathNodes[i];
            pathNode2.visited = false;
            pathNode2.heapWeight = 0.0f;
            pathNode2.penalizedPathLength = 0.0f;
            pathNode2.distanceToNearestTarget = 0.0f;
            pathNode2.previous = null;
            pathNode2.heapIndex = -1;
        }
        PathNode pathNode3 = this.pathNodes[from];
        pathNode2 = this.pathNodes[to];
        pathNode3.penalizedPathLength = 0.0f;
        pathNode3.heapWeight = pathNode3.distanceToNearestTarget = pathNode3.getDistance(pathNode2);
        this.pathHeap.clear();
        this.pathHeap.push(pathNode3);
        PathNode pathNode4 = pathNode3;
        int j = 0;
        if (this.fight == null || this.fight.getAliveEndCrystals() == 0) {
            j = 12;
        }
        while (!this.pathHeap.isEmpty()) {
            int l;
            PathNode pathNode5 = this.pathHeap.pop();
            if (pathNode5.equals(pathNode2)) {
                if (pathNode != null) {
                    pathNode.previous = pathNode2;
                    pathNode2 = pathNode;
                }
                return this.getPathOfAllPredecessors(pathNode3, pathNode2);
            }
            if (pathNode5.getDistance(pathNode2) < pathNode4.getDistance(pathNode2)) {
                pathNode4 = pathNode5;
            }
            pathNode5.visited = true;
            int k = 0;
            for (l = 0; l < 24; ++l) {
                if (this.pathNodes[l] != pathNode5) continue;
                k = l;
                break;
            }
            for (l = j; l < 24; ++l) {
                if ((this.pathNodeConnections[k] & 1 << l) <= 0) continue;
                PathNode pathNode6 = this.pathNodes[l];
                if (pathNode6.visited) continue;
                float f = pathNode5.penalizedPathLength + pathNode5.getDistance(pathNode6);
                if (pathNode6.isInHeap() && !(f < pathNode6.penalizedPathLength)) continue;
                pathNode6.previous = pathNode5;
                pathNode6.penalizedPathLength = f;
                pathNode6.distanceToNearestTarget = pathNode6.getDistance(pathNode2);
                if (pathNode6.isInHeap()) {
                    this.pathHeap.setNodeWeight(pathNode6, pathNode6.penalizedPathLength + pathNode6.distanceToNearestTarget);
                    continue;
                }
                pathNode6.heapWeight = pathNode6.penalizedPathLength + pathNode6.distanceToNearestTarget;
                this.pathHeap.push(pathNode6);
            }
        }
        if (pathNode4 == pathNode3) {
            return null;
        }
        LOGGER.debug("Failed to find path from {} to {}", (Object)from, (Object)to);
        if (pathNode != null) {
            pathNode.previous = pathNode4;
            pathNode4 = pathNode;
        }
        return this.getPathOfAllPredecessors(pathNode3, pathNode4);
    }

    private Path getPathOfAllPredecessors(PathNode unused, PathNode node) {
        ArrayList<PathNode> list = Lists.newArrayList();
        PathNode pathNode = node;
        list.add(0, pathNode);
        while (pathNode.previous != null) {
            pathNode = pathNode.previous;
            list.add(0, pathNode);
        }
        return new Path(list, new BlockPos(node.x, node.y, node.z), true);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt(DRAGON_DEATH_TIME_KEY, this.ticksSinceDeath);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

    }

    @Override
    public void checkDespawn() {
    }

    public EntityPart[] getBodyParts() {
        return this.parts;
    }

    @Override
    public boolean canHit() {
        return false;
    }

    @Override
    public SoundCategory getSoundCategory() {
        return SoundCategory.HOSTILE;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ENDER_DRAGON_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ENDER_DRAGON_HURT;
    }

    @Override
    protected float getSoundVolume() {
        return 5.0f;
    }







    @Override
    public boolean addStatusEffect(StatusEffectInstance effect, @Nullable Entity source) {
        return false;
    }

    @Override
    protected boolean canStartRiding(Entity entity) {
        return false;
    }

    @Override
    public boolean canUsePortals() {
        return false;
    }

    @Override
    public void onSpawnPacket(EntitySpawnS2CPacket packet) {
        super.onSpawnPacket(packet);
        EntityPart[] enderDragonParts = this.getBodyParts();
        for (int i = 0; i < enderDragonParts.length; ++i) {
            enderDragonParts[i].setId(i + packet.getId());
        }
    }

    @Override
    public boolean canTarget(LivingEntity target) {
        return target.canTakeDamage();
    }

    @Override
    public double getMountedHeightOffset() {
        return this.body.getHeight();
    }
}
