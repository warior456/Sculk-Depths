package net.ugi.sculk_depths.util;

import net.fabricmc.fabric.api.transfer.v1.fluid.CauldronFluidContent;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.state.property.IntProperty;
import org.jetbrains.annotations.Nullable;

//todo please help me with a better name for this
public class ModTransferApi {
    public static CauldronFluidContent registerCauldron(
            Block block,
            Fluid fluid,
            long amountPerLevel,
            @Nullable IntProperty levelProperty)
    {

        return null;
    }

}
