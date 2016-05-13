package com.sandvoxel.generitech.blocks.ores;

import com.sandvoxel.generitech.blocks.BlockBase;
import com.sandvoxel.generitech.enumtypes.EnumOreType;
import com.sandvoxel.generitech.enumtypes.EnumOres;
import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.util.IBlockMetaName;
import com.sandvoxel.generitech.util.IBlockRenderer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import java.util.List;

public class BlockOre extends BlockBase {

    public static final PropertyEnum ORE = PropertyEnum.create("ore", EnumOres.class);

    public BlockOre() {
        super(Material.rock, "ore/ore");
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
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        for (int i = 0; i < EnumOres.values().length; i++) {
            if (EnumOres.byID(i).isTypeSet(EnumOreType.ORE)) {
                list.add(new ItemStack(itemIn, 1, i));
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        String name = super.getUnlocalizedName();
        String oreName = EnumOres.byID(stack.getItemDamage()).getName();
        tooltip.add(I18n.format(name + "." + oreName + ".tooltip"));
    }
}
