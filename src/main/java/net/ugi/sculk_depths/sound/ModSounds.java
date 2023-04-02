package net.ugi.sculk_depths.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModSounds {

    public static final Identifier SCULK_DEPTHS_MUSIC_1 = new Identifier("sculk_depths:music.sculk_depths.music_1");
    public static SoundEvent SCULK_DEPTHS_MUSIC_1_EVENT = SoundEvent.of(SCULK_DEPTHS_MUSIC_1);

    public static void registerModSounds() {
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_MUSIC_1, SCULK_DEPTHS_MUSIC_1_EVENT);
    }
}
