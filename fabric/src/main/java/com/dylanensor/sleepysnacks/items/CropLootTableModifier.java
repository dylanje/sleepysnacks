package com.dylanensor.sleepysnacks.items;

import com.dylanensor.sleepysnacks.SleepySnacks;
import com.dylanensor.sleepysnacks.SleepySnacksMod;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CropLootTableModifier {
    private static final Logger LOGGER = LoggerFactory.getLogger(CropLootTableModifier.class);

    public static void init() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.getNamespace().equalsIgnoreCase("minecraft")) {
                String path = id.getPath();
                switch (path) {
                    case "chests/spawn_bonus_chest" -> {
                        LootPool.Builder builder = LootPool.lootPool();
                        builder.setRolls(ConstantValue.exactly(1));
                        builder.setBonusRolls(ConstantValue.exactly(0));
                        for (SeedItem seed : SleepySnacksMod.seeds) {
                            builder.add(
                                    LootItem.lootTableItem(seed).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(3, 8), false))
                            );
                        }
                        tableBuilder.withPool(builder);
                    }
                }
            }
        });
    }
}
