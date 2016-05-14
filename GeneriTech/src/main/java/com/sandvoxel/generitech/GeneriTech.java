package com.sandvoxel.generitech;

import com.google.common.base.Stopwatch;
import com.sandvoxel.generitech.common.config.Config;
import com.sandvoxel.generitech.common.integrations.IntegrationsManager;
import com.sandvoxel.generitech.proxy.IProxy;
import com.sandvoxel.generitech.common.util.LogHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.concurrent.TimeUnit;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION_BUILD,name = Reference.MOD_NAME, certificateFingerprint = Reference.FINGERPRINT, dependencies = Reference.DEPENDENCIES, guiFactory = Reference.GUI_FACTORY)
public class GeneriTech {
    @Mod.Instance(Reference.MOD_ID)
    public static GeneriTech instance;

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    public static Configuration configuration;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        final Stopwatch watch = Stopwatch.createStarted();
        LogHelper.info( "Pre Initialization ( started )" );

        proxy.registerConfiguration(event.getSuggestedConfigurationFile());

        proxy.registerBlocks();
        proxy.registerItems();

        proxy.registerGUIs();

        proxy.registerFurnaceRecipes();
        proxy.registerEvents();

        proxy.registerRenderers();

        IntegrationsManager.instance().index();
        IntegrationsManager.instance().preInit();

        LogHelper.info( "Pre Initialization ( ended after " + watch.elapsed( TimeUnit.MILLISECONDS ) + "ms )" );
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        final Stopwatch watch = Stopwatch.createStarted();
        LogHelper.info( "Initialization ( started )" );

        proxy.registerRecipes();

        IntegrationsManager.instance().init();

        LogHelper.info( "Initialization ( ended after " + watch.elapsed( TimeUnit.MILLISECONDS ) + "ms )" );
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        final Stopwatch watch = Stopwatch.createStarted();
        LogHelper.info( "Post Initialization ( started )" );

        IntegrationsManager.instance().postInit();

        LogHelper.info( "Post Initialization ( ended after " + watch.elapsed( TimeUnit.MILLISECONDS ) + "ms )" );
    }

    @SubscribeEvent
    public void onConfigurationChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Reference.MOD_ID)) {
            Config.loadConfiguration();
        }
    }

}
