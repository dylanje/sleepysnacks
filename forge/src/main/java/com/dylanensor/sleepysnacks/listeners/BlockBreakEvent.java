package com.dylanensor.sleepysnacks.listeners;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class BlockBreakEvent {
    @SubscribeEvent
    public void onInteractionWithTool(BlockEvent.BlockToolModificationEvent event) {
        Player player = event.getPlayer();
        if (!event.isSimulated() && event.getHeldItemStack().getItem() instanceof AxeItem) {
            BlockState state = event.getState();
        }
    }
}
