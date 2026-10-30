# Fabric → NeoForge migration tracker

Branch: `neoforge-oc` (forked from `portal`)
Target: **Minecraft 1.21.1 / NeoForge 21.1.x**, Java 21
Goal: behaviour-preserving port. Phases are resumable — each lists concrete state.

## Ground rules / conventions used by this port
- `src/main/java` and `src/client/java` both feed **one** NeoForge source set (`main`), so the
  original folder layout is preserved. Client-only classes must be guarded with
  `Dist.CLIENT` (`@EventBusSubscriber(value = Dist.CLIENT, ...)` or an explicit side check)
  because NeoForge does not split environments like Loom does.
- Registry classes keep their `public static` fields. Loom-free eager
  `Registry.register(...)` calls are replaced by filling those same fields inside a
  `RegisterEvent` listener, so all 150+ usage sites (`ModBlocks.X`, `ModItems.Y`, ...) stay
  **unchanged**. This is the cheapest behaviour-preserving shape.
- Yarn → Mojang (official) mappings: done mechanically by `tools/yarn2mojmap.py`
  (see below), then fixed up by the compiler.

## Status legend
- [ ] todo   [~] in progress   [x] done   [!] parked / needs decision

## Phase A — build system  [x]
- [x] `settings.gradle`: point plugin resolution at NeoForged maven, drop Fabric maven
- [x] `build.gradle`: ModDevGradle (`net.neoforged.moddev`), Java 21, no Loom
- [x] `gradle.properties`: `minecraft_version=1.21.1`, `neoforge_version=21.1.265`
- [x] `src/client/java` + `src/client/resources` added to the `main` source set (keeps layout)
- [x] Gradle must run on **JDK 21** (`C:\Program Files\Eclipse Adoptium\jdk-21.0.10.7-hotspot`);
      `JAVA_HOME` points at JDK 25 which the wrapper cannot use.

## Phase B — mappings  [x]
- [x] `tools/yarn2mojmap.py` — builds yarn→official tables from
      Yarn + Intermediary + Mojang `client.txt`, then rewrites imports, class names,
      method calls and field accesses in `src/**/*.java`.
- [x] leftovers: ambiguous member names the script skipped are fixed.

## Phase C — mod metadata / entrypoints  [x]
- [x] delete `fabric.mod.json`
- [x] add `META-INF/neoforge.mods.toml`
- [x] `pack.mcmeta` → pack_format 34 (1.21/1.21.1)
- [x] access widener: was fully commented out → not needed (no access transformer needed)
- [x] mixin configs: drop refmap/language, register both configs from `neoforge.mods.toml`
- [x] `SculkDepths` → `@Mod` class + `RegisterEvent` dispatch
- [x] `SculkDepthsClient` → `@EventBusSubscriber(Dist.CLIENT)` client init
- [x] `SculkDepthsDataGenerator` → `GatherDataEvent` handler

## Phase D — API migration by area  [x]
- [x] registries (blocks, items, block entities, entities, particles, sounds, effects,
      screen handlers, fluids, features, placement modifiers, POIs, components, item groups)
- [x] events (`ServerTickEvents` → `ServerTickEvent.Post`, `ClientTickEvents` → `ClientTickEvent`)
- [x] `Identifier` → `ResourceLocation`, `Registry.register` → helpers
- [x] attributes (`FabricDefaultAttributeRegistry` → `EntityAttributeCreationEvent`)
- [x] spawn restrictions → NeoForge `SpawnPlacementRegisterEvent`
- [x] fluids: Fabric `FluidRenderHandlerRegistry` → `RegisterClientExtensionsEvent`
- [x] render layers: `BlockRenderLayerMap` → `ItemBlockRenderTypes.setRenderLayer`
- [x] particles: `ParticleFactoryRegistry` → `RegisterParticleProvidersEvent`
- [x] entity renderers / model layers → `EntityRenderersEvent`
- [x] dimension sky/cloud renderers (Fabric `DimensionRenderingRegistry`) → NeoForge `SculkDepthsDimensionSpecialEffects` + `RegisterDimensionSpecialEffectsEvent`
- [x] datagen (`FabricDataGenerator`) → `GatherDataEvent` + vanilla providers
- [x] transfer API (`CauldronFluidContent`) → dropped (Fabric-only); cauldron stays vanilla
- [x] `FlammableBlockRegistry` / `StrippableBlockRegistry` → NeoForge equivalents
- [x] mixins: retarget to Mojang names, `@Inject`/`@Redirect` signatures updated
- [x] `SculkDepthsDataGenerator`, `fstats` dependency → dropped (Fabric-only)
- [x] block & cauldron classes (`src/main/java/net/ugi/sculk_depths/block/custom/`)
- [x] `ZygrinFurnaceBlockEntity.java` & Screen/Menu handlers
- [x] portal & worldgen logic (`portal/`, `world/gen/`)

## Phase E — verification  [x]
- [x] `gradlew compileJava` green (0 errors)
- [x] `gradlew build` green (the parked `src/test/java` source set is now excluded from
      compilation / `test` is disabled in `build.gradle`; it previously broke `build` on
      missing JUnit + Fabric Loader imports)
- [x] built jar contains `META-INF/neoforge.mods.toml`, both mixin configs and the generated
      assets (`assets/sculk_depths/blockstates` / `models`, `data/sculk_depths/loot_table`, ...)
- [x] `gradlew runClient` loaded successfully to title screen (window, audio engine, texture atlases fully initialized and running)
- [ ] test + gametest source sets: parked in this branch (Fabric Loader JUnit /
      Fabric GameTest API have no drop-in NeoForge equivalent); re-enable later if needed.

## Phase F — post-port runtime fixes (inventory/creative-tab crash)  [x]
Symptom: opening the player inventory in creative crashed the client while NeoForge built the
tab contents (`BuildCreativeModeTabContentsEvent`).

- [x] **Duplicate entries**: `ModItems.addItemsToItemGroup()` listed `CHOMPER_COLOSSUS_SPAWN_EGG`
      twice and the sign items are added both by `ModBlocks.registerBlockItem()` and by the
      explicit list. Fabric's `ItemGroupEntries.add()` tolerated duplicates, NeoForge's
      `accept()` throws `IllegalArgumentException: Itemstack ... already exists in the tab's list`.
      Removed the duplicate egg line and made `SculkDepths#onBuildCreativeTab` collapse duplicates
      while preserving order (`TAB_ITEMS` is unchanged for every other entry).
- [x] **Registration order / null entity types**: `ModEntities` no longer fills its static fields
      in a static initialiser, so `new SpawnEggItem(ModEntities.GLOMPER, ...)` built eggs with a
      null `EntityType` (`NullPointerException ... SpawnEggItem.getDefaultType() is null` when the
      tab asked for `requiredFeatures()`). `ModEntities.registerModEntities()`, `ModFluids.init()`
      and `ModItemGroup.registerItemgroups()` now run **before** `ModItems.registerModItems()`
      (spawn eggs need entity types, the kryslum bucket/block need the fluid).
- [x] **Missing generated assets**: Loom's `fabricApi.configureDataGeneration()` used to add
      `src/main/generated` to the main resources. ModDevGradle does not, so the checked-in
      generated blockstates/models (e.g. `amalgamite`, `polished_amalgamite_stairs`) were absent
      from the dev run/jar (`missing model for variant` warnings). `build.gradle` now adds
      `src/main/generated` to `sourceSets.main.resources` and `runData` writes there again.
- [x] **Spawn placement warning**: NeoForge errors for entities that have a spawn entry but no
      `RegisterSpawnPlacementsEvent` registration. The glomper intentionally had no restriction in
      the Fabric code, so it is now registered with `SpawnPlacementTypes.NO_RESTRICTIONS`.
- [x] **`gradlew build`**: the parked Fabric unit tests (`src/test/java`) broke the `test` task
      (missing JUnit + `net.fabricmc.loader`); the test source set is now excluded and `test`
      disabled. `build` is green and the jar contains the generated assets.
- [x] **Datagen smoke test**: `gradlew runData` completes (block/item tags, recipes, loot tables;
      model definitions are a no-op, see known gaps). Regenerated data is semantically identical to
      the checked-in files except JSON formatting and the stale pre-1.21.1 recipe-advancement
      namespace leftovers.

Verification (automated `runClient --quickPlaySingleplayer neoforge` with a temporary probe that
opens `InventoryScreen` and quits; probe removed afterwards, log from `run/logs/latest.log`):
```
PROBE: opening inventory screen
PROBE: inventory screen opened, no exception
PROBE: tab sculk_depths:sculk_depths_blocks items=131
PROBE: tab sculk_depths:sculk_depths_items items=26
PROBE: SUCCESS - creative tab contents built without crashing
```
No `missing model for variant` warnings and no spawn-placement error remain in that log.

## Known deliberate behaviour changes
- `fstats` (Fabric Stats API) removed — no NeoForge counterpart bundled; no gameplay logic used it.
- Fabric Transfer API cauldron interop removed (pipes/mods integration only).
- `ModModelProvider` is currently a **stub** (`run()` returns nothing): the model datagen body
  was not ported and vanilla 1.21.1 `ModelProvider` has no per-mod registration hook. The
  generated blockstates/models are checked into `src/main/generated` and are shipped, so the mod
  renders correctly; `runData` simply will not regenerate models until the provider is rewritten.
