package com.dylanensor.sleepysnacks;

@Mod("sleepysnacks")
public class SleepySnacksForge {
    private static final Logger LOGGER = LogManager.getLogger();

    public static Config config;
    public static CreativeModeTab SLEEPYSNACKS_ITEM_GROUP = null;
    public static SleepySnacksMod mod;

    public SleepySnacksForge() {
        config = new Config();
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();


        mod = new SleepySnacksMod(new ForgeAdapter());
    }
}