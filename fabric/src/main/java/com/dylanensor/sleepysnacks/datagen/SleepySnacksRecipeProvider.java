package com.dylanensor.sleepysnacks.datagen;

import com.dylanensor.sleepysnacks.SleepySnacks;
import com.dylanensor.sleepysnacks.common.ItemNames;
import com.dylanensor.sleepysnacks.common.MiscNames;
import com.dylanensor.sleepysnacks.mixin.datagen.IdentifierAccessor;
import com.dylanensor.sleepysnacks.register.helpers.FarmlandCrop;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import java.util.function.Consumer;

public class SleepySnacksRecipeProvider extends FabricRecipeProvider {
    public SleepySnacksRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void buildRecipes(Consumer<FinishedRecipe> exporter) {
        generateSeeds(exporter);
    }

    protected void generateSeeds(Consumer<FinishedRecipe> exporter) {
        for (FarmlandCrop crop : FarmlandCrop.copy()) {
            TagKey<Item> tag = independentTag(crop.getPlural());
            ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, crop.getSeedItem()).requires(tag).unlockedBy("has_" + crop.getLowercaseName(), RecipeProvider.has(crop)).save(exporter);
        }
    }

    private TagKey<Item> sleepysnacks(String name) {
        return TagKey.create(Registries.ITEM, new ResourceLocation(MiscNames.MOD_ID, name));
    }

    public static TagKey<Item> independentTag(String name) {
        IdentifierAccessor accessor = (IdentifierAccessor) SleepySnacks.createIdentifier(name);
        accessor.setNamespace("${dependent}");
        return TagKey.create(Registries.ITEM, (ResourceLocation) accessor);
    }
}
