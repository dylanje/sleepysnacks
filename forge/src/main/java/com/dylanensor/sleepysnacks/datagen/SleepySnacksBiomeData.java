package com.dylanensor.sleepysnacks.datagen;

import com.dylanensor.sleepysnacks.common.MiscNames;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SleepySnacksBiomeData {

    public SleepySnacksBiomeData(IEventBus bus) {
        DeferredRegister<Codec<? extends BiomeModifier>> biomeModSerializer =
                DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MiscNames.MOD_ID);
        biomeModSerializer.register(bus);
    }

    public void getData(GatherDataEvent event) {

    }
}
