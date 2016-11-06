package xyz.aadev.generitech.common.config;


import com.google.common.collect.Lists;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import xyz.aadev.generitech.Reference;

import java.util.List;

public final class Config {
    public static Config INSTANCE = new Config();
    public static ConfigCategory General;
    static Configuration config;

    private Config() {
    }

    public static void init(FMLPreInitializationEvent event) {
        config = new Configuration(event.getSuggestedConfigurationFile(), "0.1", false);

        MinecraftForge.EVENT_BUS.register(INSTANCE);

        syncConfig();
    }

    public static boolean syncConfig() {
        Property prop;

        // General
        {
            String cat = "general";
            List<String> propOrder = Lists.newArrayList();
            General = config.getCategory(cat);

            prop = config.get(cat, "testValue", true);
            prop.setComment("A test value!");
            prop.getBoolean();
            propOrder.add(prop.getName());
        }

        ConfigWorldGen.syncConfig(config);

        boolean changed = false;
        if (config.hasChanged()) {
            config.save();
            changed = true;
        }

        return changed;
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            syncConfig();
        }
    }
}
