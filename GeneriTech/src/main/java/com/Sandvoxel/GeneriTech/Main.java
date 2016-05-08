package com.Sandvoxel.GeneriTech;

import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import com.Sandvoxel.GeneriTech.proxy.CommonProxy;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.lang.reflect.Proxy;

public class Main {
    @Mod.Instance
    public static Main.GeneriTech Instance = new Main.GeneriTech();

    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;

    @Mod(modid = Reference.MOD_ID, version = Reference.VERSION,name = Reference.NAME)
    public static class GeneriTech {



        @Mod.EventHandler
        public void PreInit(FMLPreInitializationEvent event){
            proxy.preInit(event);

        }

        @Mod.EventHandler
        public void init(FMLInitializationEvent event){
            proxy.Init(event);
        }


        @Mod.EventHandler
        public void PostInit(FMLPostInitializationEvent event){
            proxy.PostInit(event);
        }

    }
}
