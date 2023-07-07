package com.dylanensor.sleepysnacks;

import com.dylanensor.sleepysnacks.common.PlatformAdapter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ForgeAdapter implements PlatformAdapter<ForgeAdapter> {

    @Override
    public void afterBlockBroken(Level level, Player player, BlockPos pos, BlockState state, BlockEntity entity) {

    }

    @Override
    public CreativeModeTab getTab() {
        return SleepySnacksForge.SLEEPYSNACKS_ITEM_GROUP;
    }

    @Override
    public boolean skipHarvest() {
        return true;
    }

    @Override
    public void registerFlammableBlocks() {

    }
}