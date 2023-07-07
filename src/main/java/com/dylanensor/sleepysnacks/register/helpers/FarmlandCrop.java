package com.dylanensor.sleepysnacks.register.helpers;

import com.dylanensor.sleepysnacks.SleepySnacksMod;
import com.dylanensor.sleepysnacks.blocks.SleepySnacksCropBlock;
import com.dylanensor.sleepysnacks.items.CropItem;
import com.dylanensor.sleepysnacks.items.SeedItem;
import com.dylanensor.sleepysnacks.register.TagCategory;
import com.dylanensor.sleepysnacks.util.BlockConvertible;
import com.dylanensor.sleepysnacks.util.FoodConstructor;
import com.dylanensor.sleepysnacks.util.ItemConvertibleWithPlural;
import com.dylanensor.sleepysnacks.util.RegisterFunction;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.dylanensor.sleepysnacks.SleepySnacksMod.*;
import static com.dylanensor.sleepysnacks.util.FoodConstructor.createFood;

public class FarmlandCrop implements ItemConvertibleWithPlural, BlockConvertible {

    private static final List<FarmlandCrop> FARMLAND_CROPS = new ArrayList<>();

    private final String name;
    private final String dropName;
    private final boolean plural;
    private final TagCategory tagCategory;
    private final Item cropItem;
    private final Block cropBlock;
    private final SeedItem seedItem;
    private final TagKey<Biome> biomes; // todo implement

    public FarmlandCrop(String cropName, boolean isPlural, TagCategory category, FoodConstructor registry, TagKey<Biome> biomes) {
        this(cropName, cropName, isPlural, category, registry, biomes);
    }

    public FarmlandCrop(String cropName, String dropName, boolean isPlural, TagCategory category, FoodConstructor registry, TagKey<Biome> biomes) {
        Objects.requireNonNull(category);
        this.name = cropName;
        this.dropName = dropName;
        this.plural = isPlural;
        this.tagCategory = category;
        this.biomes = biomes;
        if (registry == null) {
            this.cropItem = new CropItem(createGroup());
        } else {
            this.cropItem = new CropItem(createGroup().food(createFood(registry)));
        }
        cropBlock = new SleepySnacksCropBlock(createCropSettings());
        seedItem = new SeedItem(cropBlock, createGroup(), biomes);
        FARMLAND_CROPS.add(this);
    }

    @Override
    public Block asBlock() {
        return cropBlock;
    }

    @Override
    public String name() {
        return dropName;
    }

    @Override
    public boolean hasPlural() {
        return plural;
    }

    @Override
    public Item asItem() {
        return cropItem;
    }

    public TagCategory getTagCategory() {
        return tagCategory;
    }

    public SeedItem getSeedItem() {
        return seedItem;
    }

    public static List<FarmlandCrop> copy() {
        return FARMLAND_CROPS;
    }

    public static void registerBlocks(RegisterFunction<Block> register) {
        for (FarmlandCrop farmlandCrop : FARMLAND_CROPS) {
            register.register(createIdentifier(farmlandCrop.name + "_crop"), farmlandCrop.asBlock());
            SleepySnacksMod.cropBlocks.add(farmlandCrop.asBlock());
        }
    }

    public static void registerItems(RegisterFunction<Item> register) {
        for (FarmlandCrop farmlandCrop : FARMLAND_CROPS) {
            register.register(createIdentifier(farmlandCrop.dropName), farmlandCrop.asItem());
            register.register(createIdentifier(farmlandCrop.name + "_seed"), farmlandCrop.seedItem);
            SleepySnacksMod.cropItems.add(farmlandCrop.asItem());
            SleepySnacksMod.seeds.add(farmlandCrop.seedItem);
        }
    }
}
