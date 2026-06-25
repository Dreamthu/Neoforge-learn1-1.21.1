package net.traum.learn1mod.datagen;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.traum.learn1mod.Learn1Mod;

@EventBusSubscriber(modid = Learn1Mod.MOD_ID)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {

    }
}
