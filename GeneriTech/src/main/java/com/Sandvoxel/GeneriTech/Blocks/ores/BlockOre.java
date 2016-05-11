package com.Sandvoxel.GeneriTech.Blocks.ores;

import com.Sandvoxel.GeneriTech.EnumTypes.EnumOres;
import com.Sandvoxel.GeneriTech.GeneriTechTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.List;

public class BlockOre extends Block{

    public static final PropertyEnum ORE = PropertyEnum.create("ore", EnumOres.class);
    public String[] SUBNAMES;


    public BlockOre(Material material, float hardness, float resistance, String[] subnames) {
        super(material);
        SUBNAMES = subnames;
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setUnlocalizedName("blockOre");
        this.setCreativeTab(GeneriTechTabs.ORE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(ORE, EnumOres.COPPER));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, ORE);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        switch (meta){
            case 0:
                return getDefaultState().withProperty(ORE, EnumOres.COPPER);
            case 1:
                return getDefaultState().withProperty(ORE, EnumOres.TIN);
            case 2:
                return getDefaultState().withProperty(ORE, EnumOres.LEAD);
            default:
                return getDefaultState().withProperty(ORE, EnumOres.COPPER);
        }
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumOres ores = (EnumOres) state.getValue(ORE);
        return ores.getID();
    }

    @Override
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        list.add(new ItemStack(itemIn, 1, 0));
        list.add(new ItemStack(itemIn, 1, 1));
        list.add(new ItemStack(itemIn, 1, 2));
    }


}
