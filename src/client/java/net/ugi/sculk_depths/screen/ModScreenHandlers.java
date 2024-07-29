package net.ugi.sculk_depths.screen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.ugi.sculk_depths.SculkDepths;

public class ModScreenHandlers {
    public static ScreenHandlerType<ZygrinFurnaceScreenHandler> ZYGRIN_FURNACE_SCREEN_HANDLER = Registry.register(
            Registries.SCREEN_HANDLER,
            SculkDepths.identifier( "zygrin_furnace"),
            new ScreenHandlerType<>(ZygrinFurnaceScreenHandler::new, FeatureFlags.VANILLA_FEATURES));
    //public static final ScreenHandlerType<ZygrinFurnaceScreenHandler> ZYGRIN_FURNACE_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(ZygrinFurnaceScreenHandler::new);
    //public static final ScreenHandlerType<ZygrinFurnaceScreenHandler> ZYGRIN_FURNACE = ScreenHandlerType.register("blast_furnace", ZygrinFurnaceScreenHandler::new);
    public static void registerModScreenHandlers() {
    }
}
