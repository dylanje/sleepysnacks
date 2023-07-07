package com.dylanensor.sleepysnacks.mixin.datagen;

import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TagsProvider.TagAppender.class)
public interface ObjectBuilderAccessor {
    @Accessor("builder")
    TagBuilder getBuilder();
}
