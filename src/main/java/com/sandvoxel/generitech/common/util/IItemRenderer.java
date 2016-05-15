package com.sandvoxel.generitech.common.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IItemRenderer {
    @SideOnly(Side.CLIENT)
    void registerItemRenderer();
}
