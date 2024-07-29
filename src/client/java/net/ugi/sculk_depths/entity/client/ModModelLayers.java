package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModModelLayers {

    public static final EntityModelLayer GLOMPER =
            new EntityModelLayer(SculkDepths.identifier( "glomper"), "main");

    public static final EntityModelLayer LESTER =
            new EntityModelLayer(SculkDepths.identifier( "lester"), "main");

    public static final EntityModelLayer CHOMPER_COLOSSUS =
            new EntityModelLayer(SculkDepths.identifier( "chomper_colossus"), "main");
}