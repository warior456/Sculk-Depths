package net.ugi.sculk_depths.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModSounds {

    public static final Identifier SCULK_DEPTHS_MAIN_SOUNDTRACK = new Identifier(SculkDepths.MOD_ID, "music.sculk_depths.main_soundtrack");
    public static final Identifier SCULK_DEPTHS_MAIN_THEME = new Identifier("sculk_depths:music.sculk_depths.main_theme");
    public static final Identifier SCULK_DEPTHS_ZINNIA = new Identifier("sculk_depths:music.sculk_depths.zinnia");
    public static final Identifier AMBIENT_WIND_ADDITIONS = new Identifier("sculk_depths:ambient.wind.additions");
    public static final Identifier AMBIENT_CAVE_ADDITIONS = new Identifier("sculk_depths:ambient.cave.additions");
    public static SoundEvent SCULK_DEPTHS_MAIN_SOUNDTRACK_EVENT = SoundEvent.of(SCULK_DEPTHS_MAIN_SOUNDTRACK);
    public static SoundEvent SCULK_DEPTHS_MAIN_THEME_EVENT = SoundEvent.of(SCULK_DEPTHS_MAIN_THEME);
    public static SoundEvent SCULK_DEPTHS_ZINNIA_EVENT = SoundEvent.of(SCULK_DEPTHS_ZINNIA);
    public static SoundEvent AMBIENT_WIND_ADDITIONS_EVENT = SoundEvent.of(AMBIENT_WIND_ADDITIONS);
    public static SoundEvent AMBIENT_CAVE_ADDITIONS_EVENT = SoundEvent.of(AMBIENT_CAVE_ADDITIONS);

    public static void registerModSounds() {
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_MAIN_SOUNDTRACK, SCULK_DEPTHS_MAIN_SOUNDTRACK_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_MAIN_THEME, SCULK_DEPTHS_MAIN_THEME_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_ZINNIA, SCULK_DEPTHS_ZINNIA_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.AMBIENT_WIND_ADDITIONS, AMBIENT_WIND_ADDITIONS_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.AMBIENT_CAVE_ADDITIONS, AMBIENT_CAVE_ADDITIONS_EVENT);
    }
}
