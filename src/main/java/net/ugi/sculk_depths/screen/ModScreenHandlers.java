package net.ugi.sculk_depths.screen;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.Registry;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.ugi.sculk_depths.SculkDepths;

public class ModScreenHandlers {
    public static MenuType<ZygrinFurnaceScreenHandler> ZYGRIN_FURNACE_SCREEN_HANDLER = Registry.register(
            BuiltInRegistries.MENU,
            SculkDepths.id( "zygrin_furnace"),
            new MenuType<>(ZygrinFurnaceScreenHandler::new, FeatureFlags.VANILLA_SET));
    //public static final ScreenHandlerType<ZygrinFurnaceScreenHandler> ZYGRIN_FURNACE_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(ZygrinFurnaceScreenHandler::new);
    //public static final ScreenHandlerType<ZygrinFurnaceScreenHandler> ZYGRIN_FURNACE = ScreenHandlerType.register("blast_furnace", ZygrinFurnaceScreenHandler::new);
    public static void registerModScreenHandlers() {
    }
}
