package com.sandvoxel.generitech.proxy;

import com.sandvoxel.generitech.renderer.Blocks;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

    @Override
    public void preInit(FMLPreInitializationEvent e){
        super.preInit(e);


    }

    @Override
    public void init(FMLInitializationEvent e){
        super.init(e);
        Blocks.preInit();
        Blocks.registerRenders();

    }

    @Override
    public void postInit(FMLPostInitializationEvent e) {
        super.postInit(e);
    }
}
