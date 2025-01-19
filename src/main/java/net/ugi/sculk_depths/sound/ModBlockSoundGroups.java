package net.ugi.sculk_depths.sound;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

import static net.ugi.sculk_depths.sound.ModSounds.*;

public class ModBlockSoundGroups {
    public static BlockSoundGroup AMALGAMITE = new BlockSoundGroup(0.3F,0.1F,BLOCK_AMALGAMITE_BREAK_EVENT, BLOCK_AMALGAMITE_STEP_EVENT, BLOCK_AMALGAMITE_PLACE_EVENT, SoundEvents.BLOCK_DEEPSLATE_HIT, SoundEvents.BLOCK_DEEPSLATE_FALL);
}
