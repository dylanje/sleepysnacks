package com.dylanensor.sleepysnacks;

import com.dylanensor.sleepysnacks.common.PlatformAdapter;
import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class FabricAdapter implements PlatformAdapter<FabricAdapter> {
    @Override
    public void afterBlockBroken(Level level, Player player, BlockPos pos, BlockState state, BlockEntity entity) {
        PlayerBlockBreakEvents.AFTER.invoker().afterBlockBreak(level, player, pos, state, entity);
    }

    @Override
    public CreativeModeTab getTab() {
        return SleepySnacks.SLEEPYSNACKS_ITEM_GROUP;
    }

    @Override
    public boolean skipHarvest() {
        return false;
    }

    @Override
    public void registerFlammableBlocks() {

    }
}
