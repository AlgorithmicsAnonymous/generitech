package com.Sandvoxel.GeneriTech;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by koval on 5/2/2016.
 */
public class Main {
    @Mod.Instance
    public static Main.GeneriTech Instance = new Main.GeneriTech();



    @Mod(modid = Reference.MODID, version = Reference.VERSION,name = Reference.NAME)
    public static class GeneriTech {



        @Mod.EventHandler
        public void PreInit(FMLPreInitializationEvent event){

        }




        @Mod.EventHandler
        public void init(FMLInitializationEvent event){

        }

        @Mod.EventHandler
        public void PostInit(FMLPostInitializationEvent event){

        }

    }}
