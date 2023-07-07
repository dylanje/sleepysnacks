package com.dylanensor.sleepysnacks;

import com.dylanensor.sleepysnacks.biome.CropModifier;
import com.dylanensor.sleepysnacks.blocks.SleepySnacksCropBlock;
import com.dylanensor.sleepysnacks.common.MiscNames;
import com.dylanensor.sleepysnacks.config.Config;
import com.dylanensor.sleepysnacks.datagen.SleepySnacksBiomeData;
import com.dylanensor.sleepysnacks.items.SeedItem;
import com.dylanensor.sleepysnacks.listeners.BlockBreakEvent;
import com.dylanensor.sleepysnacks.listeners.EntitySpawn;
import com.dylanensor.sleepysnacks.listeners.Harvest;
import com.dylanensor.sleepysnacks.listeners.LootTableModification;
import com.dylanensor.sleepysnacks.loot.AdditionalTableModifier;
import com.dylanensor.sleepysnacks.loot.EntityModifier;
import com.dylanensor.sleepysnacks.loot.SpawnChestModifier;
import com.dylanensor.sleepysnacks.register.Content;
import com.dylanensor.sleepysnacks.register.helpers.FarmlandCrop;
import com.dylanensor.sleepysnacks.registry.GeneratorRegistry;
import com.google.common.collect.Sets;
import com.mojang.serialization.Codec;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.animal.Parrot;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.EventListenerHelper;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static com.dylanensor.sleepysnacks.common.MiscNames.MOD_ID;

@Mod("sleepysnacks")
public class SleepySnacksForge {
    private static final Logger LOGGER = LogManager.getLogger();
    public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_SERIALIZER =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, MiscNames.MOD_ID);
    public static final DeferredRegister<BiomeModifier> BIOME_MODIFIER =
            DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIERS, MiscNames.MOD_ID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> GLM = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, MiscNames.MOD_ID);

    public static Config config;
    public static CreativeModeTab SLEEPYSNACKS_ITEM_GROUP = null;
    public static SleepySnacksMod mod;

    public SleepySnacksForge() {
        config = new Config();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::doClientStuff);
        SleepySnacksBiomeData data = new SleepySnacksBiomeData(bus);
        BIOME_MODIFIER.register(bus);
        BIOME_SERIALIZER.register(bus);
        BIOME_SERIALIZER.register("crops", CropModifier::makeCodec);
        GLM.register(bus);
        GLM.register("spawn_loot", SpawnChestModifier.CODEC);
        GLM.register("entity_modifier", EntityModifier.CODEC);
        GLM.register("table_adder", AdditionalTableModifier.CODEC);
        GLM.register("spawn_loot", SpawnChestModifier.CODEC);
        GLM.register("entity_modifier", EntityModifier.CODEC);
        GLM.register("table_adder", AdditionalTableModifier.CODEC);

        bus.addListener(data::getData);

        bus.addListener(config::initConfig);

        MinecraftForge.EVENT_BUS.addListener(SleepySnacksForge::onWorldLoad);
        MinecraftForge.EVENT_BUS.register(new LootTableModification());
        MinecraftForge.EVENT_BUS.register(new Harvest());
        MinecraftForge.EVENT_BUS.register(new BlockBreakEvent());
        //MinecraftForge.EVENT_BUS.register(new CroptopiaVillagerTrades());
        MinecraftForge.EVENT_BUS.register(new EntitySpawn());
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, config.config);
        EventListenerHelper.getListenerList(PlayerInteractEvent.RightClickBlock.class);

        // Register ourselves for server and other game events we are interested in
        mod = new SleepySnacksMod(new ForgeAdapter());
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client

        SleepySnacksMod.cropBlocks.forEach(block -> {
            ItemBlockRenderTypes.setRenderLayer(block, RenderType.cutoutMipped());
        });

        BlockColors colors = Minecraft.getInstance().getBlockColors();
        colors.register((state, world, pos, tintIndex) ->
                world != null && pos != null
                        ? BiomeColors.getAverageFoliageColor(world, pos)
                        : FoliageColor.getDefaultColor());
    }

    @SubscribeEvent // You can use SubscribeEvent and let the Event Bus discover methods to call
    public void onServerStarting(FMLDedicatedServerSetupEvent event) {
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {

        @SubscribeEvent
        public static void registerTag(BuildCreativeModeTabContentsEvent event) {
            // not a fan of forges event compared to fabrics.
            if (event.getTab().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
                FarmlandCrop.copy().stream().map(FarmlandCrop::getSeedItem).map(ItemStack::new).forEachOrdered(stack -> {
                    event.getEntries().putAfter(new ItemStack(Items.NETHER_WART), stack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                });
            }
        }


        @SubscribeEvent
        public static void onRegister(RegisterEvent event) {
            if (event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)) {
                SLEEPYSNACKS_ITEM_GROUP = CreativeModeTab.builder()
                        .title(Component.translatable("itemGroup.sleepysnacks"))
                        .displayItems((featureFlagSet, output) ->
                                BuiltInRegistries.ITEM.entrySet().stream()
                                        .filter(entry -> entry.getKey().location().getNamespace().equals(MOD_ID))
                                        .sorted(Comparator.comparing(entry -> BuiltInRegistries.ITEM.getId(entry.getValue())))
                                        .forEach(entry -> output.accept(entry.getValue())))
                        .icon(() -> new ItemStack(Content.ARUGULA)).build();
                Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(MOD_ID, "sleepysnacks"), SLEEPYSNACKS_ITEM_GROUP);
                Content.registerItems((id, item) -> {
                    event.register(ForgeRegistries.Keys.ITEMS, id, () -> item);
                    if (item instanceof ItemNameBlockItem) {
                        ((ItemNameBlockItem) item).registerBlocks(Item.BY_BLOCK, item);
                    }
                    if (item instanceof SeedItem it) {
                        // maybe not needed anymore
                        SleepySnacksCropBlock block = (SleepySnacksCropBlock) (it).getBlock();
                        block.setSeed(it);
                    }
                    return item;
                });

                List<ItemLike> chickenItems = new ArrayList<>(SleepySnacksMod.seeds);
                chickenItems.addAll(Arrays.stream(Chicken.FOOD_ITEMS.getItems()).map(ItemStack::getItem).collect(Collectors.toList()));
                Chicken.FOOD_ITEMS = Ingredient.of(chickenItems.toArray(new ItemLike[0]));
                List<Item> parrotItems = new ArrayList<>(Parrot.TAME_FOOD);
                parrotItems.addAll(SleepySnacksMod.seeds);
                Parrot.TAME_FOOD = Sets.newHashSet(parrotItems);

                List<ItemLike> pigItems = new ArrayList<>(Arrays.asList(Content.ARUGULA, Content.ARUGULA));
                pigItems.addAll(Arrays.stream(Pig.FOOD_ITEMS.getItems()).map(ItemStack::getItem).collect(Collectors.toList()));
                Pig.FOOD_ITEMS = Ingredient.of(pigItems.toArray(new ItemLike[0]));

                GeneratorRegistry.init();
                Config.setFeatures(config);
            }
            if (event.getRegistryKey().equals(ForgeRegistries.Keys.BLOCKS)) {
                Content.registerBlocks((id, object) -> {
                    event.register(ForgeRegistries.Keys.BLOCKS, blockRegisterHelper -> blockRegisterHelper.register(id, object));
                    return object;
                });
                mod.platform().registerFlammableBlocks();
            }
        }
    }

    public static ResourceLocation createIdentifier(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private static boolean hasRun;

    public static void onWorldLoad(LevelEvent.Load event) {
        if (!hasRun) {
            hasRun = true;
        }
    }
}
