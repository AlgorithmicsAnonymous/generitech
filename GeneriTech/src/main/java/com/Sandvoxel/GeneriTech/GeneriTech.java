package com.Sandvoxel.GeneriTech;

import com.Sandvoxel.GeneriTech.Misc.GeneriTab;
import com.Sandvoxel.GeneriTech.Misc.Reference;
import com.Sandvoxel.GeneriTech.proxy.CommonProxy;
import com.Sandvoxel.GeneriTech.util.LogHelper;
import com.google.common.base.Stopwatch;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.concurrent.TimeUnit;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION,name = Reference.MOD_NAME)
public class GeneriTech {
    @Mod.Instance
    public static GeneriTech Instance = new GeneriTech();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod.EventHandler
    public void PreInit(FMLPreInitializationEvent event){
        final Stopwatch watch = Stopwatch.createStarted();
        LogHelper.info( "Pre Initialization ( started )" );

        proxy.preInit(event);

        LogHelper.info( "Pre Initialization ( ended after " + watch.elapsed( TimeUnit.MILLISECONDS ) + "ms )" );
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
        final Stopwatch watch = Stopwatch.createStarted();
        LogHelper.info( "Initialization ( started )" );

        proxy.Init(event);

        LogHelper.info( "Initialization ( ended after " + watch.elapsed( TimeUnit.MILLISECONDS ) + "ms )" );
    }


    @Mod.EventHandler
    public void PostInit(FMLPostInitializationEvent event){
        final Stopwatch watch = Stopwatch.createStarted();
        LogHelper.info( "Post Initialization ( started )" );

        proxy.PostInit(event);

        LogHelper.info( "Post Initialization ( ended after " + watch.elapsed( TimeUnit.MILLISECONDS ) + "ms )" );
    }

}
