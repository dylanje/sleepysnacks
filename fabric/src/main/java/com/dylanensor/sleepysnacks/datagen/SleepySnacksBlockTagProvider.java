package com.dylanensor.sleepysnacks.datagen;

import com.dylanensor.sleepysnacks.register.helpers.FarmlandCrop;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;

public class SleepySnacksBlockTagProvider extends IntrinsicHolderTagsProvider<Block> {

    public SleepySnacksBlockTagProvider(PackOutput packOutput,
                                        CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(packOutput, Registries.BLOCK, completableFuture, block -> block.builtInRegistryHolder().key());
    }

    @Override
    protected void addTags(HolderLookup.Provider arg) {
        generateCrops();
    }

    protected void generateCrops() {
        IntrinsicTagAppender<Block> crops = this.tag(BlockTags.CROPS);
        for (FarmlandCrop crop : FarmlandCrop.copy()) {
            crops.add(crop.asBlock());
        }
    }
}
