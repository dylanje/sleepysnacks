package com.dylanensor.sleepysnacks.listeners;

import com.dylanensor.sleepysnacks.register.Content;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.event.entity.living.MobSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class EntitySpawn {
    @SubscribeEvent
    public void onEntitySpawn(MobSpawnEvent.FinalizeSpawn event) {
        Mob mob = event.getEntity();
        if (mob instanceof Chicken) {
            mob.goalSelector.addGoal(4, new TemptGoal((PathfinderMob) mob, 1.2d, Ingredient.of(Content.ARUGULA), false));
        }
    }
}
