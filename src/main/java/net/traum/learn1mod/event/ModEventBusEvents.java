package net.traum.learn1mod.event;

import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.traum.learn1mod.Learn1Mod;
import net.traum.learn1mod.entity.ModEntities;
import net.traum.learn1mod.entity.client.GeckoModel;
import net.traum.learn1mod.entity.client.TomahawkProjectileModel;
import net.traum.learn1mod.entity.custom.GeckoEntity;

@EventBusSubscriber(modid = Learn1Mod.MOD_ID)
public class ModEventBusEvents {
    @SubscribeEvent
    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GeckoModel.LAYER_LOCATION, GeckoModel::createBodyLayer);
        event.registerLayerDefinition(TomahawkProjectileModel.LAYER_LOCATION, TomahawkProjectileModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.GECKO.get(), GeckoEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        event.register(ModEntities.GECKO.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Animal::checkAnimalSpawnRules, RegisterSpawnPlacementsEvent.Operation.REPLACE);
    }
}