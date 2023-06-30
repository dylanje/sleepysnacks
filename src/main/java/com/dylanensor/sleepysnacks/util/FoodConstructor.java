package com.dylanensor.sleepysnacks.util;

import net.minecraft.world.food.FoodProperties;

public record FoodConstructor(int hunger) {
    public static final FoodConstructor RAW_CROP_1 = new FoodConstructor(1);

    public static FoodProperties.Builder createBuilder(FoodConstructor reg) {
        return new FoodProperties.Builder().nutrition(reg.hunger);
    }

    public static FoodProperties createFood(FoodConstructor reg) {
        return createBuilder(reg).build();
    }
}
