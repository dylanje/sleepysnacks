package com.dylanensor.sleepysnacks.register.helpers;

import com.dylanensor.sleepysnacks.util.ItemConvertibleWithPlural;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

import java.util.Objects;

public enum VanillaCrops implements ItemConvertibleWithPlural {
    APPLE(Items.APPLE),
    BEETROOT(Items.BEETROOT),
    CARROT(Items.CARROT),
    MELON(Items.MELON_SLICE),
    POTATO(Items.POTATO),
    PUMPKIN(Items.PUMPKIN),
    WHEAT(Items.WHEAT);

    private Item item;

    VanillaCrops(ItemLike source) {
        Objects.requireNonNull(source);
        this.item = source.asItem();
    }

    @Override
    public Item asItem() {
        return item;
    }

    @Override
    public boolean hasPlural() {
        return true;
    }
}
