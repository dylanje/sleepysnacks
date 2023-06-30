package com.dylanensor.sleepysnacks.util;

import net.minecraft.resources.ResourceLocation;

public interface RegisterFunction<T> {
    T register(ResourceLocation id, T object);
}
