package net.ugi.sculk_depths.sound;

import net.minecraft.world.item.JukeboxSong;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.ugi.sculk_depths.SculkDepths;

public class ModSounds {

    public static final ResourceLocation SCULK_DEPTHS_MAIN_SOUNDTRACK = SculkDepths.id("music.sculk_depths.main_soundtrack");
    public static final ResourceLocation SCULK_DEPTHS_CEPHLERA_SOUNDTRACK = SculkDepths.id("music.sculk_depths.cephlera_soundtrack");
    public static final ResourceLocation SCULK_DEPTHS_MAIN_THEME = SculkDepths.id("music.sculk_depths.main_theme");
    public static final ResourceLocation SCULK_DEPTHS_ZINNIA = SculkDepths.id("music.sculk_depths.zinnia");
    public static final ResourceLocation SCULK_DEPTHS_OST_1 = SculkDepths.id("music.sculk_depths.ost-1");
    public static final ResourceLocation AMBIENT_WIND_ADDITIONS = SculkDepths.id("ambient.wind.additions");
    public static final ResourceLocation AMBIENT_CAVE_ADDITIONS = SculkDepths.id("ambient.cave.additions");
    public static final ResourceLocation BLOCK_AMALGAMITE_BREAK = SculkDepths.id("block.amalgamite.break");
    public static final ResourceLocation BLOCK_AMALGAMITE_STEP = SculkDepths.id("block.amalgamite.step");
    public static final ResourceLocation BLOCK_AMALGAMITE_PLACE = SculkDepths.id("block.amalgamite.place");
    public static final ResourceLocation ZINNIA_MUSIC_DISC = SculkDepths.id("disc.zinnia");
    public static SoundEvent SCULK_DEPTHS_MAIN_SOUNDTRACK_EVENT = SoundEvent.createVariableRangeEvent(SCULK_DEPTHS_MAIN_SOUNDTRACK);
    public static SoundEvent SCULK_DEPTHS_CEPHLERA_SOUNDTRACK_EVENT = SoundEvent.createVariableRangeEvent(SCULK_DEPTHS_CEPHLERA_SOUNDTRACK);
    public static SoundEvent SCULK_DEPTHS_MAIN_THEME_EVENT = SoundEvent.createVariableRangeEvent(SCULK_DEPTHS_MAIN_THEME);
    public static SoundEvent SCULK_DEPTHS_ZINNIA_EVENT = SoundEvent.createVariableRangeEvent(SCULK_DEPTHS_ZINNIA);
    public static SoundEvent SCULK_DEPTHS_OST_1_EVENT = SoundEvent.createVariableRangeEvent(SCULK_DEPTHS_OST_1);
    public static SoundEvent AMBIENT_WIND_ADDITIONS_EVENT = SoundEvent.createVariableRangeEvent(AMBIENT_WIND_ADDITIONS);
    public static SoundEvent AMBIENT_CAVE_ADDITIONS_EVENT = SoundEvent.createVariableRangeEvent(AMBIENT_CAVE_ADDITIONS);
    public static SoundEvent BLOCK_AMALGAMITE_BREAK_EVENT = SoundEvent.createVariableRangeEvent(BLOCK_AMALGAMITE_BREAK);
    public static SoundEvent BLOCK_AMALGAMITE_STEP_EVENT = SoundEvent.createVariableRangeEvent(BLOCK_AMALGAMITE_STEP);
    public static SoundEvent BLOCK_AMALGAMITE_PLACE_EVENT = SoundEvent.createVariableRangeEvent(BLOCK_AMALGAMITE_PLACE);
    public static SoundEvent ZINNIA_MUSIC_DISC_EVENT = SoundEvent.createVariableRangeEvent(ZINNIA_MUSIC_DISC);


    public static final ResourceKey<JukeboxSong> ZINNIA_KEY = ResourceKey.create(Registries.JUKEBOX_SONG, SculkDepths.id("zinnia"));


    public static void registerModSounds() {
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_MAIN_SOUNDTRACK, SCULK_DEPTHS_MAIN_SOUNDTRACK_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_CEPHLERA_SOUNDTRACK, SCULK_DEPTHS_CEPHLERA_SOUNDTRACK_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_MAIN_THEME, SCULK_DEPTHS_MAIN_THEME_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_ZINNIA, SCULK_DEPTHS_ZINNIA_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_OST_1, SCULK_DEPTHS_OST_1_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.AMBIENT_WIND_ADDITIONS, AMBIENT_WIND_ADDITIONS_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.AMBIENT_CAVE_ADDITIONS, AMBIENT_CAVE_ADDITIONS_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.BLOCK_AMALGAMITE_BREAK, BLOCK_AMALGAMITE_BREAK_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.BLOCK_AMALGAMITE_STEP, BLOCK_AMALGAMITE_STEP_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.BLOCK_AMALGAMITE_PLACE, BLOCK_AMALGAMITE_PLACE_EVENT);
        Registry.register(BuiltInRegistries.SOUND_EVENT, ModSounds.ZINNIA_MUSIC_DISC, ZINNIA_MUSIC_DISC_EVENT);
    }
}
