package com.sandvoxel.generitech.client.gui.config;

import com.sandvoxel.generitech.GeneriTech;
import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.common.config.Config;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.Arrays;

public class ConfigGui extends GuiConfig {
    public ConfigGui(GuiScreen parentScreen) {
        super(
                parentScreen,
                Arrays.asList(new IConfigElement[]{
                        new ConfigElement(GeneriTech.configuration.getCategory(Config.Category.WORLDGEN))
                }),
                Reference.MOD_ID, false, false, "GeneriTech Configuration");
        titleLine2 = GeneriTech.configuration.getConfigFile().getAbsolutePath();
    }
}
