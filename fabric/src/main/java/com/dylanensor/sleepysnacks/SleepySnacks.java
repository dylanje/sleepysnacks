import com.dylanensor.sleepysnacks.FabricAdapter;
import com.dylanensor.sleepysnacks.SleepySnacksMod;
import com.dylanensor.sleepysnacks.register.Content;
import com.mojang.logging.LogUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistrySynchronization;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import org.slf4j.Logger;

import java.util.Comparator;

import static com.dylanensor.sleepysnacks.common.MiscNames.MOD_ID;

public class SleepySnacks implements ModInitializer {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final boolean devEnvironment = Boolean.getBoolean(MOD_ID + ".dev");
    public SleepySnacksConfig config;

    public static final CreativeModeTab SLEEPYSNACKS_ITEM_GROUP = FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.sleepysnacks"))
            .displayItems((featureFlagSet, output) ->
                    BuiltInRegistries.ITEM.entrySet().stream()
                            .filter(entry -> entry.getKey().location().getNamespace().equals(MOD_ID))
                            .sorted(Comparator.comparing(entry -> BuiltInRegistries.ITEM.getId(entry.getValue())))
                            .forEach(entry-> output.accept(entry.getValue())))
            .icon(()-> new ItemStack(Content.ARUGULA))
            .build();
    @Override
    public void onInitialize() {
        SleepySnacksMod mod = new SleepySnacksMod(new FabricAdapter());

        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, new ResourceLocation(MOD_ID, "sleepysnacks"), SLEEPYSNACKS_ITEM_GROUP);
        Content.registerBlocks((id, object) -> Registry.register(BuiltInRegistries.BLOCK, id, object));

        Content.registerItems((id, object) -> Registry.register(BuiltInRegistries.ITEM, id, object));

        Composter.init();

        this.config = new SleepySnacksConfig(devEnvironment, "sleepysnacks.conf");
        config.addSerializer(ResourceLocation.class, IdentifierSerializer.INSTANCE);
        config.loadConfig();

        CropLootTableModifier.init();

        // ** left off here **
    }

}