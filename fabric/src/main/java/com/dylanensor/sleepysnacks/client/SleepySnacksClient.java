package com.dylanensor.sleepysnacks.client;

import com.dylanensor.sleepysnacks.SleepySnacksMod;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.Block;

@Environment(EnvType.CLIENT)
public class SleepySnacksClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        SleepySnacksMod.cropBlocks.forEach(this::registerCropBlockLayer);
        registerColorProvider();
    }

    public void registerCropBlockLayer(Block block) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, RenderType.cutoutMipped());
    }

    public void registerColorProvider() {
        ColorProviderRegistry.BLOCK.register((state, world, pos, tintIndex) ->
                world != null && pos != null
                        ? BiomeColors.getAverageFoliageColor(world, pos)
                        : FoliageColor.getDefaultColor());
    }
}