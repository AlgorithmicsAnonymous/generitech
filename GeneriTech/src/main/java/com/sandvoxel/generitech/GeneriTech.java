package com.sandvoxel.generitech;

import com.google.common.base.Stopwatch;
import com.sandvoxel.generitech.proxy.IProxy;
import com.sandvoxel.generitech.common.util.LogHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.concurrent.TimeUnit;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION,name = Reference.MOD_NAME)
public class GeneriTech {
    @Mod.Instance
    public static GeneriTech instance = new GeneriTech();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static IProxy proxy;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
        final Stopwatch watch = Stopwatch.createStarted();
        LogHelper.info( "Pre Initialization ( started )" );

        proxy.preInit(event);

        LogHelper.info( "Pre Initialization ( ended after " + watch.elapsed( TimeUnit.MILLISECONDS ) + "ms )" );
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        final Stopwatch watch = Stopwatch.createStarted();
        LogHelper.info( "Initialization ( started )" );

        proxy.init(event);

        LogHelper.info( "Initialization ( ended after " + watch.elapsed( TimeUnit.MILLISECONDS ) + "ms )" );
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
        final Stopwatch watch = Stopwatch.createStarted();
        LogHelper.info( "Post Initialization ( started )" );

        proxy.postInit(event);

        LogHelper.info( "Post Initialization ( ended after " + watch.elapsed( TimeUnit.MILLISECONDS ) + "ms )" );
    }

}
