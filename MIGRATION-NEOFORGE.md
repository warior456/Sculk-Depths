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
- [ ] leftovers: ambiguous member names the script skipped are fixed during Phase E.

## Phase C — mod metadata / entrypoints  [ ]
- [x] delete `fabric.mod.json`
- [x] add `META-INF/neoforge.mods.toml`
- [x] `pack.mcmeta` → pack_format 34 (1.21/1.21.1)
- [x] access widener: was fully commented out → not needed (no access transformer needed)
- [x] mixin configs: drop refmap/language, register both configs from `neoforge.mods.toml`
- [ ] `SculkDepths` → `@Mod` class + `RegisterEvent` dispatch
- [ ] `SculkDepthsClient` → `@EventBusSubscriber(Dist.CLIENT)` client init
- [ ] `SculkDepthsDataGenerator` → `GatherDataEvent` handler

## Phase D — API migration by area  [ ]
- [ ] registries (blocks, items, block entities, entities, particles, sounds, effects,
      screen handlers, fluids, features, placement modifiers, POIs, components, item groups)
- [ ] events (`ServerTickEvents` → `ServerTickEvent.Post`, `ClientTickEvents` → `ClientTickEvent`)
- [ ] `Identifier` → `ResourceLocation`, `Registry.register` → helpers
- [ ] attributes (`FabricDefaultAttributeRegistry` → `EntityAttributeCreationEvent`)
- [ ] spawn restrictions → NeoForge `SpawnPlacementRegisterEvent`
- [ ] fluids: Fabric `FluidRenderHandlerRegistry` → `RegisterClientExtensionsEvent`
- [ ] render layers: `BlockRenderLayerMap` → `ItemBlockRenderTypes.setRenderLayer`
- [ ] particles: `ParticleFactoryRegistry` → `RegisterParticleProvidersEvent`
- [ ] entity renderers / model layers → `EntityRenderersEvent`
- [ ] dimension sky/cloud renderers (Fabric `DimensionRenderingRegistry`) → NeoForge
      `DimensionSpecialEffects` hook (**needs design work — see notes**)
- [ ] datagen (`FabricDataGenerator`) → `GatherDataEvent` + vanilla providers
- [ ] transfer API (`CauldronFluidContent`) → dropped (Fabric-only); cauldron stays vanilla
- [ ] `FlammableBlockRegistry` / `StrippableBlockRegistry` → NeoForge equivalents
- [ ] mixins: retarget to Mojang names, `@Inject`/`@Redirect` signatures updated
- [ ] `SculkDepthsDataGenerator`, `fstats` dependency → dropped (Fabric-only)

## Phase E — verification  [ ]
- [ ] `gradlew build` green
- [ ] `gradlew runServer` reaches "Done" without missing-registry errors
- [ ] `gradlew runClient` loads (rendering/particles)
- [ ] test + gametest source sets: parked in this branch (Fabric Loader JUnit /
      Fabric GameTest API have no drop-in NeoForge equivalent); re-enable later if needed.

## Known deliberate behaviour changes
- `fstats` (Fabric Stats API) removed — no NeoForge counterpart bundled; no gameplay logic used it.
- Fabric Transfer API cauldron interop removed (pipes/mods integration only).
