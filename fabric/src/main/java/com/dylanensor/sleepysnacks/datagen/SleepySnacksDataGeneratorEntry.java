package com.dylanensor.sleepysnacks.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class SleepySnacksDataGeneratorEntry implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack resources = fabricDataGenerator.createPack();
        resources.addProvider(SleepySnacksBlockTagProvider::new);
        resources.addProvider(SleepySnacksItemTagProvider::new);
        resources.addProvider(SleepySnacksIndependentItemTagProvider::new);
        resources.addProvider(SleepySnacksModelProvider::new);
        resources.addProvider(SleepySnacksRecipeProvider::new);
    }
}
