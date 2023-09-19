package net.ugi.sculk_depths.sound;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final Identifier SCULK_DEPTHS_MAIN_SOUNDTRACK = new Identifier("sculk_depths:music.sculk_depths.main_soundtrack");
    public static final Identifier SCULK_DEPTHS_MAIN_THEME = new Identifier("sculk_depths:music.sculk_depths.main_theme");
    public static final Identifier SCULK_DEPTHS_PETRIFIED_FOREST_ADDITIONS = new Identifier("sculk_depths:sculk_depths.petrified_forest.additions");
    public static SoundEvent SCULK_DEPTHS_MAIN_SOUNDTRACK_EVENT = SoundEvent.of(SCULK_DEPTHS_MAIN_SOUNDTRACK);
    public static SoundEvent SCULK_DEPTHS_MAIN_THEME_EVENT = SoundEvent.of(SCULK_DEPTHS_MAIN_THEME);
    public static SoundEvent SCULK_DEPTHS_PETRIFIED_FOREST_ADDITIONS_EVENT = SoundEvent.of(SCULK_DEPTHS_PETRIFIED_FOREST_ADDITIONS);

    public static void registerModSounds() {
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_MAIN_SOUNDTRACK, SCULK_DEPTHS_MAIN_SOUNDTRACK_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_MAIN_THEME, SCULK_DEPTHS_MAIN_THEME_EVENT);
        Registry.register(Registries.SOUND_EVENT, ModSounds.SCULK_DEPTHS_PETRIFIED_FOREST_ADDITIONS, SCULK_DEPTHS_PETRIFIED_FOREST_ADDITIONS_EVENT);
    }
}
