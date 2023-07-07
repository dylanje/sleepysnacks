package com.dylanensor.sleepysnacks.config;

import com.dylanensor.sleepysnacks.common.MiscNames;
import net.fabricmc.loader.api.FabricLoader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.hocon.HoconConfigurationLoader;
import org.spongepowered.configurate.serialize.TypeSerializer;
import org.spongepowered.configurate.serialize.TypeSerializerCollection;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;


public class SleepySnacksConfig {
    protected static final Logger LOGGER = LogManager.getLogger();

    protected HoconConfigurationLoader loader;
    protected ConfigurationNode rootNode;

    protected final String configName;
    protected final boolean devEnvironment;
    private TypeSerializerCollection.Builder serializers;

    public SleepySnacksConfig(boolean devEnvironment, String configName) {
        this.devEnvironment = devEnvironment;
        this.configName = configName;
        this.serializers = TypeSerializerCollection.builder();
    }

    public SleepySnacksConfig(boolean devEnvironment) {
        this(devEnvironment, "sleepysnacks.conf");
    }

    public <T, V extends TypeSerializer<T>> void addSerializer(Class<T> clazz, V instance) {
        serializers.register(clazz, instance);
    }

    public void addSerializer(TypeSerializerCollection collection) {
        serializers.registerAll(collection);
    }

    public boolean loadConfig() {
        File configDirectory = new File(FabricLoader.getInstance().getConfigDir().toFile(), MiscNames.MOD_ID);
        File file = new File(configDirectory, configName);

        boolean createdFile = false;
        URL path = null;
        if (devEnvironment) {
            path = getClass().getClassLoader().getResource(configName);
        } else {
            try (InputStream stream = getClass().getClassLoader().getResourceAsStream(configName)) {
                byte[] bytes = new byte[stream.available()];
                stream.read(bytes);
                LOGGER.debug("Creating default config file: " + configName);
                createdFile = createdFile(file);
                if (createdFile) {
                    try (FileOutputStream outputStream = new FileOutputStream(file)) {
                        outputStream.write(bytes);
                    }
                }
            } catch (Exception e) {
                LOGGER.warn("Could not find an internal config file for {}", configName);
            } finally {
                try {
                    createdFile = createdFile(file);
                    path = file.toURI().toURL();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        }
        this.loader = HoconConfigurationLoader.builder()
                .sink(() -> new BufferedWriter(new FileWriter(file)))
                .defaultOptions(options -> options.serializers(builder -> builder.registerAll(serializers.build())))
                .url(path)
                .build();

        try {
            if (createdFile) {
                this.loader.save(generateConfig(CommentedConfigurationNode.root()));
            }
        } catch (ConfigurateException e) {
            e.printStackTrace();
        }

        try {
            this.rootNode = loader.load();
            parseConfig(rootNode);
        } catch (ConfigurateException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    protected  ConfigurationNode generateConfig(CommentedConfigurationNode node) {
        return node;
    }
    protected void parseConfig(ConfigurationNode node) {
        File configDirectory = new File(FabricLoader.getInstance().getConfigDir().toFile(), MiscNames.MOD_ID);
    }

    private boolean canCreateFile(File file) {
        return file.exists();
    }

    private boolean createdFile(File file) {
        try {
            if (!file.getParentFile().exists() && file.getParentFile().mkdirs()) {
                LOGGER.debug("Created directory for: " + file.getParentFile().getCanonicalPath());
            }

            if (!file.exists() && file.createNewFile()) {
                return true;
            }
        } catch (IOException e) {
            LOGGER.warn("Error creating new config file ", e);
            return false;
        }
        return false;
    }

    public ConfigurationNode getRootNode() {
        return rootNode;
    }


}
