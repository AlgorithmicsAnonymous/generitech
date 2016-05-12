package com.sandvoxel.generitech.proxy;

import com.sandvoxel.generitech.blocks.GTBlocks;
import com.sandvoxel.generitech.GeneriTech;
import com.sandvoxel.generitech.tileentities.GTTileEntities;
import com.sandvoxel.generitech.handler.GuiHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public abstract class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        GTBlocks.init();
        GTBlocks.register();
        GTTileEntities.init();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(GeneriTech.instance, new GuiHandler());
    }

    @Override
    public void postInit(FMLPostInitializationEvent e){

    }
}
