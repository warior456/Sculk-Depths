package net.ugi.sculk_depths.util;

import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.util.Identifier;
import net.ugi.sculk_depths.SculkDepths;

public class ModLootTableModifiers {
/*
    private static Identifier buildInjectionRoute(Identifier id) {
        return new Identifier(SculkDepths.MOD_ID, "injections/" + id.getNamespace() + "/" + id.getPath());
    }

    public static void modifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, manager, id, supplier, setter) -> {
            LootTable table = manager.getLootTable(buildInjectionRoute(id));
            if (table != LootTable.EMPTY) {
                LootPool[] pools = table.pools;

                if (pools != null)
                    supplyPools(supplier, pools);
            }

        });
    }

    private static void supplyPools(LootTable.Builder supplier, LootPool[] pools) {
        for (LootPool pool: pools) {
            supplier.pool(pool);
        }
    }
*/
}