package com.dylanensor.sleepysnacks;

import com.dylanensor.sleepysnacks.common.MiscNames;
import com.dylanensor.sleepysnacks.common.PlatformAdapter;
import com.dylanensor.sleepysnacks.items.SeedItem;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;

import java.util.ArrayList;

public record SleepySnacksMod(PlatformAdapter<?> platform) {
    public static ArrayList<Item> cropItems = new ArrayList<>();
    public static ArrayList<Block> cropBlocks = new ArrayList<>();
    public static ArrayList<SeedItem> seeds = new ArrayList<>();

    private static SleepySnacksMod mod;

    public SleepySnacksMod(PlatformAdapter<?> platform) {
        this.platform = platform;
        mod = this;
    }

    public static SleepySnacksMod getInstance() {
        return mod;
    }

    public static Item.Properties createGroup() {
        return new Item.Properties();
    }

    public static ResourceLocation createIdentifier(String name) {
        return new ResourceLocation(MiscNames.MOD_ID, name);
    }

    public static BlockBehaviour.Properties createCropSettings() {
        return BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.CROP);
    }

    private static boolean never(BlockState state, BlockGetter world, BlockPos pos) {
        return false;
    }
}
