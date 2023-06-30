package com.dylanensor.sleepysnacks.register;

import com.dylanensor.sleepysnacks.common.ItemNames;
import com.dylanensor.sleepysnacks.common.Tags;
import com.dylanensor.sleepysnacks.register.helpers.FarmlandCrop;
import com.dylanensor.sleepysnacks.util.RegisterFunction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.dylanensor.sleepysnacks.util.FoodConstructor.*;

public class Content {
    public static final FarmlandCrop ARUGULA = new FarmlandCrop(ItemNames.ARUGULA, true, TagCategory.VEGETABLES, RAW_CROP_1, Tags.HAS_ARUGULA);

    public static Item GUIDE;

    public static void registerBlocks(RegisterFunction<Block> register) {
        FarmlandCrop.registerBlocks(register);
    }

    public static void registerItems(RegisterFunction<Item> register) {
        FarmlandCrop.registerItems(register);
    }

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> Holder<ConfiguredFeature<FC, ?>> register(ResourceLocation id, ConfiguredFeature<FC, F> feature) {
        return Holder.direct(feature);
    }
}
