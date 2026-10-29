package net.ugi.sculk_depths.sound;

import net.minecraft.world.level.block.SoundType;
import net.minecraft.sounds.SoundEvents;

import static net.ugi.sculk_depths.sound.ModSounds.*;

public class ModBlockSoundGroups {
    public static SoundType AMALGAMITE = new SoundType(1,1,BLOCK_AMALGAMITE_BREAK_EVENT, BLOCK_AMALGAMITE_STEP_EVENT, BLOCK_AMALGAMITE_PLACE_EVENT, SoundEvents.DEEPSLATE_HIT, SoundEvents.DEEPSLATE_FALL);
}
