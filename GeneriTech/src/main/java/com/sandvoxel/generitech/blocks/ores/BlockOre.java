package com.sandvoxel.generitech.blocks.ores;

import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.enumtypes.EnumOres;
import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.util.IBlockRenderer;
import com.sandvoxel.generitech.util.LogHelper;
import com.sandvoxel.generitech.util.Platform;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class BlockOre extends Block implements IBlockMetaName, IBlockRenderer{

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
    public String getUnlocalizedName() {
        String blockName = getUnwrappedUnlocalizedName(super.getUnlocalizedName());

        return String.format("tile.%s.%s", Reference.MOD_ID, blockName);
    }

    private String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
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
    public String getSpecialName(ItemStack stack) {
        return EnumOres.values() [stack.getItemDamage()].name().toLowerCase();
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockRenderer() {
        final String resourcePath = String.format("%s:%s", Reference.MOD_ID, "blockOre");

        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                return new ModelResourceLocation(resourcePath, getPropertyString(state.getProperties()));
            }
        });
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockItemRenderer() {
        final String resourcePath = String.format("%s:%s", Reference.MOD_ID, "blockOre");

        List<ItemStack> subBlocks = new ArrayList<ItemStack>();
        getSubBlocks(Item.getItemFromBlock(this), null, subBlocks);

        for (ItemStack itemStack : subBlocks) {
            IBlockState blockState = this.getStateFromMeta(itemStack.getItemDamage());
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), itemStack.getItemDamage(), new ModelResourceLocation(resourcePath, Platform.getPropertyString(blockState.getProperties())));

        }
    }
}
