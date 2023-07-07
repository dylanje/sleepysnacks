package com.dylanensor.sleepysnacks.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.world.level.biome.Biome;

import java.util.concurrent.CompletableFuture;

public class SleepySnacksBiomeTagProvider  extends TagsProvider<Biome> {
    protected SleepySnacksBiomeTagProvider(PackOutput dataGenerator, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(dataGenerator, Registries.BIOME, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

    }
}
