package com.sandvoxel.generitech.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;

/**
 * Created by Sean on 13/05/2016.
 */
public class BlockMachineBase extends BlockTileEntityBase {
    protected static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockMachineBase(Material material, String resourcePath) {
        super(material, resourcePath);
    }
}
