package com.dylanensor.sleepysnacks.datagen;

import com.dylanensor.sleepysnacks.SleepySnacksMod;
import com.dylanensor.sleepysnacks.items.SeedItem;
import com.dylanensor.sleepysnacks.register.Content;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;

import java.util.concurrent.CompletableFuture;

public class SleepySnacksItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public SleepySnacksItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        generateMisc();
    }

    protected void generateMisc() {
        FabricTagBuilder crops = getOrCreateTagBuilder(ItemTags.VILLAGER_PLANTABLE_SEEDS);
        for (SeedItem seed : SleepySnacksMod.seeds) {
            crops.add(seed);
        }

        FabricTagBuilder foxFood = getOrCreateTagBuilder(ItemTags.FOX_FOOD);
        foxFood.add(Content.ARUGULA.asItem());
    }
}
