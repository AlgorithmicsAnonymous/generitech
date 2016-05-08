package com.Sandvoxel.GeneriTech.proxy;

import com.Sandvoxel.GeneriTech.GTBlock.GTBlocks;
import com.Sandvoxel.GeneriTech.RendersGT.Blocks;
import com.jcraft.jorbis.Block;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
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
    public void Init(FMLInitializationEvent e){
        super.Init(e);
        Blocks.registerRenders();
    }


    @Override
    public void PostInit(FMLPostInitializationEvent e){
        super.PostInit(e);
    }



}
