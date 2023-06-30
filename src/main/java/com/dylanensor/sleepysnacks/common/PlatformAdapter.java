package com.dylanensor.sleepysnacks.common;


import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public interface PlatformAdapter<T> {
    void afterBlockBroken(Level level, Player player, BlockPos pos, BlockState state, BlockEntity entity);

    CreativeModeTab getTab();

    boolean skipHarvest();

    void registerFlammableBlocks();

}
