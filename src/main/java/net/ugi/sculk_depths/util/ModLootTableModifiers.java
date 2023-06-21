

package net.ugi.sculk_depths.util;
/*
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModLootTableModifiers {
    private static final Identifier BLAZE_ID = new Identifier("minecraft", "entities/blaze");

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register(((resourceManager, manager, id, supplier, source) -> {
            if (BLAZE_ID.equals(id)) {
                LootPool[] pools = manager.getLootTable(new Identifier(SculkDepths.MOD_ID, "injections/blaze")).pools;

                if (pools != null)
                    supplyPools(supplier, pools);
            }
        }));
    }

    private static void supplyPools(LootTable.Builder supplier, LootPool[] pools) {
        for (LootPool pool: pools) {
            supplier.pool(pool);
        }
    }
}
*/