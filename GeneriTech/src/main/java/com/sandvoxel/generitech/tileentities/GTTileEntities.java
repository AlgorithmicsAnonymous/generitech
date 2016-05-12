package com.sandvoxel.generitech.tileentities;

import com.sandvoxel.generitech.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GTTileEntities {
    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityPulverizer.class, "tileentity." + Reference.MOD_ID + "." + TileEntityPulverizer.class.getSimpleName());
    }
}
