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
- [x] **Datagen smoke test**: `gradlew runData` completes (block/item tags, recipes, loot tables,
      blockstates/models). The model provider was ported, so model definitions are generated again
      (see "Model datagen" below). Regenerated data is semantically identical to the checked-in
      files except the intentional 1.21.1 refresh (`random_sequence` on loot tables, recipe ids in
      advancements) described below.

### Model datagen
- [x] `ModModelProvider` is a real `DataProvider` again (was a no-op stub). It runs
      `SculkDepthsBlockModelGenerators` (extends vanilla `BlockModelGenerators`) and saves
      blockstates/models through `PackOutput` providers, plus flat item models for `CRUX` and
      `MUSIC_DISC_ZINNIA`.
- [x] Item-model delegation (vanilla `ModelProvider` behaviour) is restricted to the blocks the
      provider generated a blockstate for. All other mod blocks (`zygrin`, `umbrusk`,
      `crystal_block`, ...) keep their handwritten assets and do not get duplicate generated item
      models.
- [x] Logs use `logWithHorizontal(...)`, so the checked-in `*_log_horizontal` models/blockstates
      are reproduced (the plain `log(...)` call emits only the vertical model).
- [x] Several vanilla generator helpers (`family`, `woodProvider`, `createDoor`,
      `createOrientableTrapdoor`, `createCrossBlockWithDefaultItem`, `createPlant`) are
      package-private. They are widened for datagen through
      `src/main/resources/META-INF/accesstransformer.cfg`, registered with
      `neoForge.accessTransformers`. AT entries need full JVM descriptors, e.g.
      `public net.minecraft.data.models.BlockModelGenerators family(Lnet/minecraft/world/level/block/Block;)Lnet/minecraft/data/models/BlockModelGenerators$BlockFamilyProvider;`.
- [x] A helper placed in `net.minecraft.data.models` was rejected by the module layer
      (`Module minecraft contains package ...`); the helper lives in
      `net.ugi.sculk_depths.datagen` instead.
- [x] Verification: regenerating into a temp directory produced exactly the checked-in set
      (93 blockstates, 279 models, 84 item models) with zero semantic JSON differences. The two
      remaining byte-level differences were only LF/CRLF; git stores LF (`core.autocrlf=true`), so
      regenerating in `src/main/generated` is not a content change. `runData` now writes there again.
- [x] Recipe datagen namespace fix: vanilla helpers (`stonecutterResultFromBase`,
      `nineBlockStorageRecipes`, `oneToOneConversionRecipe`, `planksFromLogs`) derive bare item
      paths and would save into `data/minecraft`. Local `stonecutterRecipe` / `ButtonRecipe` /
      `planksRecipe` helpers (and explicit ids for storage blocks/candle) keep every recipe and its
      advancement under `sculk_depths:`.
- [x] First full regeneration refreshed the legacy Fabric-era data: 128 loot tables gained the
      1.21 `random_sequence` field, 75 recipe advancements now point at `sculk_depths:` ids instead
      of `minecraft:`, and `sculk_depths:candle` plus
      `neoforge:needs_netherite_tool` were added. No recipe/tag files were removed.

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

## Phase G — dedicated-server smoke test & portal structure regression  [ ]

### Dedicated server
- [x] `gradlew runServer` starts cleanly with the mod (`Done (7.359s)!`), no mod errors, no
      missing dimensions/biomes/recipes/tags, worldgen of `sculk_depths:sculk_depths` runs.
      The disposable test world/config used for this (`run/eula.txt`, `run/server.properties`,
      `run/neoforge-server-test`) was deleted again, so `run/` only holds the normal client data.
- [x] Entity default attributes are registered (`SculkDepths#onEntityAttributes` →
      `EntityAttributeCreationEvent` for glomper/lester/chomper_colossus), matching the old
      `FabricDefaultAttributeRegistry` calls.

### Portal structure only generates partially (fixed)
Observed in the maintainer's test world: after using the portal, the destination
`sculk_depths:portal_structure` is placed with whole chunk slices missing
("part of the portal structure didn't generate (chunks were skipped)"), plus
`BlockAttachedEntity at invalid position` errors for the hanging signs in the missing chunks.

Root cause: `PedestalBlock` `GEN_STRUCTURE` called `GenerateStructureAPI.registerStructureStartStub(...)`,
which used reflection against `StructureManager#setStartForStructure(...)`. On NeoForge the
reflection succeeded, so the stub returned `true` after only placing the anchor chunk when it
happened to be loaded; the rest of the structure was never placed. The fallback path (load every
chunk of the bounding box from an async executor and call `StructureStart#placeInChunk` for each)
was the only thing that generated the full structure, and its off-thread `getChunk` calls are what
skipped chunks.

Fix (matches how WildSprout-Skylands structures go through vanilla worldgen):
- `GenerateStructureAPI#placeStructureStart(...)` builds the `StructureStart` and stores it on the
  anchor chunk at `ChunkStatus.STRUCTURE_STARTS` via the public
  `StructureManager#setStartForStructure(...)` (no reflection). The chunk is not generated further,
  so no noise/surface/features run at this point.
- Vanilla then creates the chunk references (`createReferences` at `STRUCTURE_REFERENCES`) and
  places the pieces (`placeInChunk` at `FEATURES`) while the surrounding chunks generate normally.
- The anchor chunk is the only chunk forced to `FULL` afterwards. `ChunkMap#saveChunkIfNeeded` only
  persists `LevelChunk`/`ImposterProtoChunk`, so a start attached to a `STRUCTURE_STARTS` proto chunk
  would be lost on unload. Forcing just that one chunk persists the start and lets its own pieces
  run through the normal `FEATURES` step.
- Backup for already-generated destinations: when the anchor chunk is already at/after `FEATURES`,
  worldgen will not run that step again, so `placeCenterIfGenerated(...)` writes the pieces
  intersecting the anchor chunk (the middle/center part) immediately. Only the center part is
  generated this way; the rest of the structure stays worldgen's job, and the center part may later
  grow into several structure blocks.
- `PedestalBlock` `GEN_STRUCTURE` now just calls the helper and moves to `GEN_PORTAL`; the
  `CompletableFuture`/executor chunk-loading loop, `chunkArray` and cached `StructureStart` fields
  were removed.

The Fabric branch (`portal`) gets the same change with Yarn mappings
(`StructureAccessor#setStructureStart`, `StructureStart#place`, `ChunkStatus#isAtLeast`), commit
`501b4906`.

## Maintainer messages captured during the session (for context after compaction)
> "i just opened the game and my inventory again and it crashed again"

→ fixed in Phase F (duplicate tab entries + null `EntityType` in spawn eggs).

> "you might be working on this but part of the portal structure didn't generate (chunks were skipped)"

→ see the portal structure section above (partial `portal_structure`, missing chunk slices).

> "though i do want to switch the portal generation to just set a structure start (i have a mod
> that plays with this so ask me once you've done the rest)"

→ replaced the reflection/stub placement with a proper `StructureStart` registration so the
structure generates through normal chunk generation (see Phase G). The mod the maintainer refers
to is the reference implementation (WildSprout-Skylands); there is no separate companion mod.

> "make sure to note my messages in the migration checklist in case compaction happens"

→ this section.

> "okay just continue"

→ continued the model datagen port/verification (see "Model datagen" in Phase F).

> "it's in world/gen"

→ the structure implementation to copy lives in WildSprout-Skylands'
`src/main/java/net/ugi/wildsprout_skylands/world/gen/`.

> "you will have to look at how wildsprout skyland does it, it does not have an api and then do it
> in this project, do it on 2 branches: one for fabric and one for neoforge (if you think it could
> be beneficial to make an api mod to make everything more intuitive tell me)."

→ portal generation was rewritten to register a real structure start and let vanilla place the
pieces (see Phase G). The same change is applied to the `portal` (Fabric) branch.

> "as backup (in case the chunks are already generated only generate the middle part (which might
> change to be multiple structure blocks in the future since the center piece part will be larger)"

→ `placeCenterIfGenerated(...)` now only writes the pieces intersecting the already-generated
anchor chunk (the center part). The peripheral pieces are left to worldgen.

> "\"Do not redesign portal generation beyond what the user asked; they want it to \"just set a
> structure start\" because a companion mod interacts with it.\" this is not true, there is no
> companion mod only a reference mod"

→ corrected: there is no companion mod. WildSprout-Skylands is only a reference implementation;
the portal change is the maintainer's own request, not an integration constraint.

## Known deliberate behaviour changes
- `fstats` (Fabric Stats API) removed — no NeoForge counterpart bundled; no gameplay logic used it.
- Fabric Transfer API cauldron interop removed (pipes/mods integration only).
- `ModModelProvider` is ported (no longer a stub) and reproduces the checked-in assets.
- `META-INF/accesstransformer.cfg` is shipped solely to widen vanilla `BlockModelGenerators`
  helpers used at datagen time; it does not change gameplay.
- `ModRecipeProvider` uses local helper methods for stonecutting/buttons/planks and explicit ids
  for storage blocks and the candle recipe so datagen does not write into the `minecraft`
  namespace.
