package com.sandvoxel.generitech.common.config;

import com.sandvoxel.generitech.GeneriTech;
import com.sandvoxel.generitech.Reference;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.io.File;
import java.util.Arrays;

public class Config extends GuiConfig {
    public static Configuration configuration;

    public Config(GuiScreen parentScreen) {
        super(
                parentScreen,
                Arrays.asList(new IConfigElement[]{
                        new ConfigElement(GeneriTech.configuration.getCategory("SampleData"))
                }),
                Reference.MOD_ID, false, false, "GeneriTech Configuration");
        titleLine2 = GeneriTech.configuration.getConfigFile().getAbsolutePath();
    }

    public static Configuration initConfig(File configFile) {
        if (configuration == null) {
            configuration = new Configuration(configFile);
            loadConfiguration();
        }
        return configuration;
    }

    public static void loadConfiguration() {
        ConfigSample.init(configuration);

        configuration.save();
    }
}