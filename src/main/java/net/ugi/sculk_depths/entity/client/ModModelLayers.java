package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModModelLayers {

    public static final EntityModelLayer GLOMPER =
            new EntityModelLayer(new Identifier(SculkDepths.MOD_ID, "glomper"), "main");

    public static final EntityModelLayer LESTER =
            new EntityModelLayer(new Identifier(SculkDepths.MOD_ID, "lester"), "main");

    public static final EntityModelLayer CHOMPER_COLOSSUS =
            new EntityModelLayer(new Identifier(SculkDepths.MOD_ID, "chomper_colossus"), "main");
}