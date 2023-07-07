package com.dylanensor.sleepysnacks.listeners;

import com.dylanensor.sleepysnacks.config.Config;
import com.dylanensor.sleepysnacks.events.HarvestEvent;
import com.dylanensor.sleepysnacks.mixin.AgeInvokerMixin;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Harvest {
    @SubscribeEvent
    public void onHarvest(PlayerInteractEvent.RightClickBlock event) {
        if (Config.canRightClickHarvest && !event.getEntity().isSpectator()) {
            if (!(event.getEntity().getMainHandItem().getItem() instanceof BoneMealItem)) {
                if (!event.getLevel().isClientSide) {
                    Level world = event.getLevel();
                    BlockPos pos = event.getPos();
                    BlockState blockClicked = event.getLevel().getBlockState(pos);
                    if (blockClicked.getBlock() instanceof CropBlock block && block instanceof AgeInvokerMixin mixin) {
                        IntegerProperty property = mixin.doGetAgeProperty();
                        int age = blockClicked.getValue(property);
                        if (age == block.getMaxAge()) {
                            HarvestEvent harvestedCropEvent = new HarvestEvent(event.getEntity(), blockClicked, withAge(blockClicked, property, 0));
                            MinecraftForge.EVENT_BUS.post(harvestedCropEvent);
                            world.setBlock(pos, harvestedCropEvent.getTurnedState(), 2);
                            Block.dropResources(blockClicked, world, event.getPos());
                            event.setResult(Event.Result.ALLOW);
                        }
                    }
                }
            }
        }
    }

    public BlockState withAge(BlockState state, IntegerProperty property, int age) {
        return state.setValue(property, Integer.valueOf(age));
    }



}
