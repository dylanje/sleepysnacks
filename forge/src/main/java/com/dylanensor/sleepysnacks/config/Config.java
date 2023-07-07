package com.dylanensor.sleepysnacks.config;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.slf4j.Logger;

import java.util.List;

@Mod.EventBusSubscriber(modid = "sleepysnacks", bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    private static Logger LOGGER = LogUtils.getLogger();
    private final ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
    public ForgeConfigSpec config;

    public static ForgeConfigSpec.ConfigValue<Boolean> rightClickHarvest;
    private final Multimap<ResourceLocation, String> features = HashMultimap.create();
    public static boolean canRightClickHarvest;

    public Config() {
        rightClickHarvest = CONFIG_BUILDER.comment("allows user to right click harvest crops")
                .translation("sleepysnacks.config.rightclickharvest")
                .define("rightClickHarvest", true);
        CONFIG_BUILDER.pop();
        this.config = CONFIG_BUILDER.build();
    }

    @SubscribeEvent
    public void initConfig(ModConfigEvent configEvent) {
        if (configEvent.getConfig().getSpec() == config) {
            synchronized (this) {
                canRightClickHarvest = rightClickHarvest.get();
                features.clear();
            }
        }
    }

    public static class CropConfiguration {
        String seed;
        List<String> biomes;

        public CropConfiguration(String seed, List<String> biomes) {
            this.seed = seed;
            this.biomes = biomes;
        }

        public static void init() {

        }
    }

    public static void setFeatures(Config config) {

    }

    public Multimap<ResourceLocation, String> getFeatures() {
        return features;
    }
}
