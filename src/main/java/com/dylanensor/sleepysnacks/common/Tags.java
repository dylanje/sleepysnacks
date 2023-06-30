package com.dylanensor.sleepysnacks.common;

import com.dylanensor.sleepysnacks.SleepySnacksMod;
import com.google.common.collect.ImmutableList;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class Tags {
    private static final List<TagKey<Biome>> SLEEPYSNACKS_BIOME_TAGS = new ArrayList<>();

    public static final TagKey<Biome> HAS_ARUGULA = create("has_crop/" + ItemNames.ARUGULA);

    private static TagKey<Biome> create(String key) {
        TagKey<Biome> biomeKey = TagKey.create(Registries.BIOME, SleepySnacksMod.createIdentifier(key));
        SLEEPYSNACKS_BIOME_TAGS.add(biomeKey);
        return biomeKey;
    }

    public static List<TagKey<Biome>> getSleepysnacksBiomeTags() {
        return ImmutableList.copyOf(SLEEPYSNACKS_BIOME_TAGS);
    }
}
