package com.sandvoxel.generitech.common.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;


public abstract class BlockMachineBase extends BlockTileEntityBase {
    protected static final PropertyBool ACTIVE = PropertyBool.create("active");

    public BlockMachineBase(Material material, String resourcePath) {
        super(material, resourcePath);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean active = false;
        if(meta == 1)
            active = true;

        return this.getDefaultState().withProperty(ACTIVE, active);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        boolean active = state.getValue(ACTIVE);
        return active ? 1 : 0;
    }

}
