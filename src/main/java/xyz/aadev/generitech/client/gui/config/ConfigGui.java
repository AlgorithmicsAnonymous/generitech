package xyz.aadev.generitech.client.gui.config;

import xyz.aadev.generitech.GeneriTech;
import xyz.aadev.generitech.Reference;
import xyz.aadev.generitech.common.config.Config;
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
