package com.dylanensor.sleepysnacks;

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
}