package xyz.aadev.generitech.client.gui.config;

import com.google.common.collect.Lists;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;
import xyz.aadev.generitech.Reference;
import xyz.aadev.generitech.common.config.Config;
import xyz.aadev.generitech.common.config.ConfigWorldGen;

import java.util.List;

public class ConfigGui extends GuiConfig {
    public ConfigGui(GuiScreen parentScreen) {
        super(
                parentScreen,
                getConfigElements(),
                Reference.MOD_ID, false, false, "GeneriTech Configuration");
    }

    private static List<IConfigElement> getConfigElements() {
        List<IConfigElement> list = Lists.newArrayList();

        list.add(new ConfigElement(Config.General));
        list.add(new ConfigElement(ConfigWorldGen.Worldgen));

        return list;
    }
}
