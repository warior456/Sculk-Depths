package net.ugi.sculk_depths.sound;

import net.minecraft.block.jukebox.JukeboxSong;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.ugi.sculk_depths.SculkDepths;

public class ModSounds {

    public static final Identifier SCULK_DEPTHS_MAIN_SOUNDTRACK = SculkDepths.identifier( "music.sculk_depths.main_soundtrack");
    public static final Identifier SCULK_DEPTHS_CEPHLERA_SOUNDTRACK = SculkDepths.identifier( "music.sculk_depths.cephlera_soundtrack");
    public static final Identifier SCULK_DEPTHS_MAIN_THEME = SculkDepths.identifier("music.sculk_depths.main_theme");
    public static final Identifier SCULK_DEPTHS_ZINNIA = SculkDepths.identifier("music.sculk_depths.zinnia");
    public static final Identifier SCULK_DEPTHS_OST_1 = SculkDepths.identifier("music.sculk_depths.ost-1");
    public static final Identifier AMBIENT_WIND_ADDITIONS = SculkDepths.identifier("ambient.wind.additions");
    public static final Identifier AMBIENT_CAVE_ADDITIONS = SculkDepths.identifier("ambient.cave.additions");
    public static final Identifier BLOCK_AMALGAMITE_BREAK = SculkDepths.identifier("block.amalgamite.break");
    public static final Identifier BLOCK_AMALGAMITE_STEP = SculkDepths.identifier("block.amalgamite.step");
    public static final Identifier BLOCK_AMALGAMITE_PLACE = SculkDepths.identifier("block.amalgamite.place");
    public static final Identifier ZINNIA_MUSIC_DISC = SculkDepths.identifier("disc.zinnia");
    public static SoundEvent SCULK_DEPTHS_MAIN_SOUNDTRACK_EVENT = SoundEvent.of(SCULK_DEPTHS_MAIN_SOUNDTRACK);
    public static SoundEvent SCULK_DEPTHS_CEPHLERA_SOUNDTRACK_EVENT = SoundEvent.of(SCULK_DEPTHS_CEPHLERA_SOUNDTRACK);
    public static SoundEvent SCULK_DEPTHS_MAIN_THEME_EVENT = SoundEvent.of(SCULK_DEPTHS_MAIN_THEME);
    public static SoundEvent SCULK_DEPTHS_ZINNIA_EVENT = SoundEvent.of(SCULK_DEPTHS_ZINNIA);
    public static SoundEvent SCULK_DEPTHS_OST_1_EVENT = SoundEvent.of(SCULK_DEPTHS_OST_1);
    public static SoundEvent AMBIENT_WIND_ADDITIONS_EVENT = SoundEvent.of(AMBIENT_WIND_ADDITIONS);
    public static SoundEvent AMBIENT_CAVE_ADDITIONS_EVENT = SoundEvent.of(AMBIENT_CAVE_ADDITIONS);
    public static SoundEvent BLOCK_AMALGAMITE_BREAK_EVENT = SoundEvent.of(BLOCK_AMALGAMITE_BREAK);
    public static SoundEvent BLOCK_AMALGAMITE_STEP_EVENT = SoundEvent.of(BLOCK_AMALGAMITE_STEP);
    public static SoundEvent BLOCK_AMALGAMITE_PLACE_EVENT = SoundEvent.of(BLOCK_AMALGAMITE_PLACE);
    public static SoundEvent ZINNIA_MUSIC_DISC_EVENT = SoundEvent.of(ZINNIA_MUSIC_DISC);


    public static final RegistryKey<JukeboxSong> ZINNIA_KEY = RegistryKey.of(RegistryKeys.JUKEBOX_SONG, SculkDepths.identifier( "zinnia"));


    public static void registerModSounds() {
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_MAIN_SOUNDTRACK, SCULK_DEPTHS_MAIN_SOUNDTRACK_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_CEPHLERA_SOUNDTRACK, SCULK_DEPTHS_CEPHLERA_SOUNDTRACK_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_MAIN_THEME, SCULK_DEPTHS_MAIN_THEME_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_ZINNIA, SCULK_DEPTHS_ZINNIA_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_OST_1, SCULK_DEPTHS_OST_1_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.AMBIENT_WIND_ADDITIONS, AMBIENT_WIND_ADDITIONS_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.AMBIENT_CAVE_ADDITIONS, AMBIENT_CAVE_ADDITIONS_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.BLOCK_AMALGAMITE_BREAK, BLOCK_AMALGAMITE_BREAK_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.BLOCK_AMALGAMITE_STEP, BLOCK_AMALGAMITE_STEP_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.BLOCK_AMALGAMITE_PLACE, BLOCK_AMALGAMITE_PLACE_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.ZINNIA_MUSIC_DISC, ZINNIA_MUSIC_DISC_EVENT);
    }
}
