package net.ugi.sculk_depths.regression;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * Stub for issues #90 / #91 (portal ignition thread-safety / state).
 *
 * The portal ignition logic (portal/Portal.java genFrameStep/genPortalStep together
 * with block/custom/PedestalBlock) mutates the world from scheduled/threaded tasks,
 * which is where #90/#91 live. A meaningful regression test needs an in-world
 * Fabric GameTest with the full portal frame built (two charged SCULK_PEDESTALs
 * with HAS_ENERGY_ESSENCE plus the reinforced-deepslate frame), plus a controlled
 * server-tick environment to observe the threaded state mutation deterministically.
 *
 * Building that frame headlessly requires the exact 10-block pedestal spacing and
 * frame geometry from Portal.getFramePos/getFrameMinPos and is too heavy to keep
 * deterministic inside this suite, so it is deferred. To implement: create a
 * GameTest that builds the frame via World.setBlockState, triggers ignition via
 * the pedestal's onUse path with an ENERGY_ESSENCE, and asserts (a) all frame
 * blocks convert exactly once and (b) no concurrent-modification/state corruption
 * occurs when ignition is triggered twice in the same tick.
 */
class Issue90Issue91PortalIgnitionStub {

    @Test
    @Disabled("issue #90/#91: portal ignition needs a full in-world GameTest frame; see class javadoc")
    void portalIgnitionIsThreadSafeAndIdempotent() {
    }
}
