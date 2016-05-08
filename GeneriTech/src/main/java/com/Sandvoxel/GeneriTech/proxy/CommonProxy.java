package com.Sandvoxel.GeneriTech.proxy;

import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by CrazyGrape on 5/7/2016.
 */
public class CommonProxy {
    public void registerRenders() {

    }

    public void preInit(FMLPreInitializationEvent e) {
        GTBlocks.init();
        GTBlocks.register();
    }


    public void Init(FMLInitializationEvent e) {
    }


    public void PostInit(FMLPostInitializationEvent e){


    }

}
