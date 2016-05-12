package com.sandvoxel.generitech.blocks.ores;

import com.sandvoxel.generitech.enumtypes.EnumOres;
import com.sandvoxel.generitech.GeneriTechTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockOre extends Block implements IBlockMetaName{

    public static final PropertyEnum ORE = PropertyEnum.create("ore", EnumOres.class);



    public BlockOre(String unlocalizedName, Material material, float hardness, float resistance) {
        super(material);
        this.setHardness(hardness);
        this.setUnlocalizedName(unlocalizedName);
        this.setResistance(resistance);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ORE, EnumOres.COPPER));
        this.setCreativeTab(GeneriTechTabs.ORE);

    }




    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(ORE, EnumOres.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumOres ores = (EnumOres) state.getValue(ORE);
        return ores.getID();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ORE);
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
        for (EnumOres t : EnumOres.values())
            list.add(new ItemStack(itemIn, 1, t.ordinal()));

    }

    @Override
    public String getUnlocalizedName() {
        return super.getUnlocalizedName();
    }

    @Override
    public String getSpecialName(ItemStack stack) {
        return EnumOres.values() [stack.getItemDamage()].name().toLowerCase();
    }
}
