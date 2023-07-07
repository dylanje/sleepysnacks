package com.dylanensor.sleepysnacks.registry;

import com.dylanensor.sleepysnacks.blocks.SleepySnacksCropBlock;
import com.dylanensor.sleepysnacks.register.Content;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static com.dylanensor.sleepysnacks.SleepySnacks.createIdentifier;

public class GeneratorRegistry {
    private static final Map<String, ResourceKey<PlacedFeature>> keyMap = new HashMap<>();
    public static final Map<ResourceKey<PlacedFeature>, Holder<PlacedFeature>> datagenPlacedFeatures = new HashMap<>();

    public static final SimpleBlockConfiguration config = (new SimpleBlockConfiguration(
            new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                    .add(Content.ARUGULA.asBlock().defaultBlockState().setValue(SleepySnacksCropBlock.AGE, 3), 10)
                    .build())));

    public static final ConfiguredFeature<RandomPatchConfiguration, ?> RANDOM_CROP = register(createIdentifier("random_crop"), Feature.RANDOM_PATCH,
            FeatureUtils.simpleRandomPatchConfiguration(6, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, config)));

    public static final Holder<PlacedFeature> RANDOM_CROP_PLACED = register(createIdentifier("random_crop"), RANDOM_CROP,
            CountPlacement.of(3), PlacementUtils.HEIGHTMAP_WORLD_SURFACE);

    public static <FC extends FeatureConfiguration, F extends Feature<FC>> ConfiguredFeature<FC, ?> register(ResourceLocation id, F feature, FC config) {
        return new ConfiguredFeature<>(feature, config);
    }

    public static Holder<PlacedFeature> register(ResourceLocation id, ConfiguredFeature<?, ?> holder, List<PlacementModifier> modifiers) {
        ResourceKey<PlacedFeature> key = ResourceKey.create(Registries.PLACED_FEATURE, id);
        keyMap.put(id.getPath(), key);
        Holder<PlacedFeature> direct = Holder.direct(new PlacedFeature(Holder.direct(holder), modifiers));
        datagenPlacedFeatures.put(key, direct);
        return direct;
    }

    public static Holder<PlacedFeature> register(ResourceLocation id, ConfiguredFeature<?,?> feature, PlacementModifier... modifiers) {
        return register(id, feature, List.of(modifiers));
    }

    public static ResourceKey<PlacedFeature> getFeatureKey(String key) {
        return keyMap.get(key);
    }
}
