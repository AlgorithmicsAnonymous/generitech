package com.Sandvoxel.GeneriTech.proxy;

import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * Created by CrazyGrape on 5/7/2016.
 */
public class ClientProxy extends CommonProxy{
    @Override
    public void preInit(FMLPreInitializationEvent e){
        super.preInit(e);
    }

    @Override
    public void registerRenders() {
        GTBlocks.registerRenders();
        GTBlocks.registerRenders();
    }
}
