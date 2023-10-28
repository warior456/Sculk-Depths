package net.ugi.sculk_depths.entity.client;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModModelLayers {
    public static final EntityModelLayer PORCUPINE =
            new EntityModelLayer(new Identifier(SculkDepths.MOD_ID, "lester"), "main");
}