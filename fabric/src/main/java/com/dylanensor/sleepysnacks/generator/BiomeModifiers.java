package com.dylanensor.sleepysnacks.generator;

import com.dylanensor.sleepysnacks.SleepySnacks;
import com.dylanensor.sleepysnacks.registry.GeneratorRegistry;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.minecraft.world.level.levelgen.GenerationStep;

public class BiomeModifiers {
    public static void init(SleepySnacks sleepySnacks) {
        BiomeModifications.addFeature(context -> true,
                GenerationStep.Decoration.VEGETAL_DECORATION, GeneratorRegistry.getFeatureKey("random_crop"));
    }
}
