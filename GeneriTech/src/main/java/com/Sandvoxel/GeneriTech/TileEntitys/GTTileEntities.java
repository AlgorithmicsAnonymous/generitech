package com.Sandvoxel.GeneriTech.TileEntitys;

import com.Sandvoxel.GeneriTech.Misc.Reference;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class GTTileEntities {
    public static void init()
    {
        GameRegistry.registerTileEntity(TileEntityPulverizer.class, "tileentity." + Reference.MOD_ID + "." + TileEntityPulverizer.class.getSimpleName());
    }
}
