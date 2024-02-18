package net.ugi.sculk_depths.screen;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModScreenHandlerTypes {
    public static ScreenHandlerType<ZygrinFurnaceScreenHandler> ZYGRIN_FURNACE_SCREEN_HANDLER;
    //public static final ScreenHandlerType<ZygrinFurnaceScreenHandler> ZYGRIN_FURNACE_SCREEN_HANDLER = new ExtendedScreenHandlerType<>(ZygrinFurnaceScreenHandler::new);
    //public static final ScreenHandlerType<ZygrinFurnaceScreenHandler> ZYGRIN_FURNACE = ScreenHandlerType.register("blast_furnace", ZygrinFurnaceScreenHandler::new);
    public static void registerModScreenHandlers() {
        //Registry.register(Registries.SCREEN_HANDLER, new Identifier(SculkDepths.MOD_ID, "zygrin_furnace"), ZYGRIN_FURNACE_SCREEN_HANDLER);

        ZYGRIN_FURNACE_SCREEN_HANDLER = Registry.register(
                Registries.SCREEN_HANDLER,
                new Identifier(SculkDepths.MOD_ID, "zygrin_furnace"),
                new ScreenHandlerType<>(ZygrinFurnaceScreenHandler::new, FeatureFlags.VANILLA_FEATURES));


    }
}
