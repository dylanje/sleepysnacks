package com.dylanensor.sleepysnacks.datagen;

import com.dylanensor.sleepysnacks.SleepySnacks;
import com.dylanensor.sleepysnacks.mixin.datagen.IdentifierAccessor;
import com.dylanensor.sleepysnacks.mixin.datagen.ObjectBuilderAccessor;
import com.dylanensor.sleepysnacks.mixin.datagen.TagProviderAccessor;
import com.dylanensor.sleepysnacks.register.TagCategory;
import com.dylanensor.sleepysnacks.register.helpers.FarmlandCrop;
import com.dylanensor.sleepysnacks.util.PluralInfo;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.impl.datagen.ForcedTagEntry;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.tags.TagManager;
import net.minecraft.world.item.Item;

import java.util.concurrent.CompletableFuture;

public class SleepySnacksIndependentItemTagProvider  extends FabricTagProvider.ItemTagProvider {
    public SleepySnacksIndependentItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> completableFuture) {
        super(output, completableFuture, null);
        ((TagProviderAccessor) this).setPathProvider(
                new DependentPathProvider(output,
                        PackOutput.Target.DATA_PACK,
                        TagManager.getTagDir(Registries.ITEM)));
    }

    @Override
    public String getName() {
        return "SleepySnacks Independent Tags";
    }

    @Override
    public void addTags(HolderLookup.Provider arg) {
        generateCrops();
    }

    protected void generateCrops() {
        for (FarmlandCrop crop : FarmlandCrop.copy()) {
            createCategoryTag(crop.getTagCategory().getLowerCaseName(), PluralInfo.plural(crop.getLowercaseName(), crop.hasPlural()), crop.asItem());
            if (crop.getTagCategory() != TagCategory.CROPS) {
                createCategoryTag(TagCategory.CROPS.getLowerCaseName(), PluralInfo.plural(crop.getLowercaseName(), crop.hasPlural()), crop.asItem());
            }
        }
    }

    private static TagKey<Item> register(String id) {
        return TagKey.create(Registries.ITEM, SleepySnacks.createIdentifier(id));
    }

    private void createCategoryTag(String category, String name, Item item) {
        String path = reverseLookup(item).location().getPath();
        TagKey<Item> forgeFriendlyTag = register(category + "/" + path);
        ResourceLocation independentEntry = independentTag(category + "/" + path);
        this.tag(forgeFriendlyTag).add(reverseLookup(item));
        ObjectBuilderAccessor fabricGeneralTag = (ObjectBuilderAccessor) this.tag(register(name)).add(reverseLookup(item));
        fabricGeneralTag.getBuilder().add(new ForcedTagEntry(TagEntry.tag(independentEntry)));
        ObjectBuilderAccessor group = (ObjectBuilderAccessor) this.tag(register(category));
        ResourceLocation entryForGroup = independentTag(name);
        group.getBuilder().add(new ForcedTagEntry(TagEntry.tag(entryForGroup)));
    }

    private FabricTagBuilder createGeneralTag(String name, Item item) {
        TagKey<Item> pluralTag = register(name);
        return this.getOrCreateTagBuilder(pluralTag).add(item);
    }

    private ResourceLocation independentTag(String name) {
        IdentifierAccessor accessor = (IdentifierAccessor) SleepySnacks.createIdentifier(name);
        accessor.setNamespace("${dependent}");
        return (ResourceLocation) accessor;
    }
}
