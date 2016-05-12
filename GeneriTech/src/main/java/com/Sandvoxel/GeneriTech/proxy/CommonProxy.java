package com.Sandvoxel.GeneriTech.proxy;

import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import com.Sandvoxel.GeneriTech.GeneriTech;
import com.Sandvoxel.GeneriTech.TileEntitys.GTTileEntities;
import com.Sandvoxel.GeneriTech.handler.GuiHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

/**
 * Created by CrazyGrape on 5/7/2016.
 */
public class CommonProxy {
    public void registerRenders() {

    }

    public void preInit(FMLPreInitializationEvent e) {
        GTBlocks.init();
        GTBlocks.register();
        GTTileEntities.init();
    }


    public void Init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(GeneriTech.Instance, new GuiHandler());
    }


    public void PostInit(FMLPostInitializationEvent e){


    }

}
