package com.sandvoxel.generitech.common.blocks.ores;

import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.common.blocks.BlockBase;
import com.sandvoxel.generitech.common.blocks.Blocks;
import com.sandvoxel.generitech.common.enumtypes.EnumOreType;
import com.sandvoxel.generitech.common.enumtypes.EnumOres;
import com.sandvoxel.generitech.common.util.IProvideRecipe;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.List;

public class BlockOreBlock extends BlockBase implements IProvideRecipe {

    public static final PropertyEnum<EnumOres> ORE = PropertyEnum.create("ore", EnumOres.class);

    public BlockOreBlock() {
        super(Material.rock, "ore/oreblock");
        this.setDefaultState(this.blockState.getBaseState().withProperty(ORE, EnumOres.COPPER));
        this.setCreativeTab(GeneriTechTabs.ORE);
        this.setInternalName("oreblock");
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(ORE, EnumOres.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EnumOres ores = (EnumOres) state.getValue(ORE);
        return ores.getMeta();
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
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.BLOCK)) {
                list.add(new ItemStack(itemIn, 1, i));
            }
        }
    }

    @Override
    public void RegisterRecipes() {
        for (int i = 0; i < EnumOres.values().length; i++) {
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.INGOT) && EnumOres.byMeta(i).isTypeSet(EnumOreType.BLOCK)) {
                // Register 9x Ingot -> Block
                GameRegistry.addRecipe(new ShapedOreRecipe(Blocks.BLOCK_ORE_BLOCK.getStack(1, i),
                        "xxx",
                        "xxx",
                        "xxx",
                        'x', "ingot" + EnumOres.byMeta(i).getOreName())
                );
            }
        }
    }
}
