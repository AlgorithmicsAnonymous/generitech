package com.sandvoxel.generitech.common.blocks;

import com.google.common.collect.Maps;
import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.api.util.MachineTier;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


public abstract class BlockMachineBase extends BlockTileBase {
    protected static final PropertyEnum<MachineTier> MACHINETIER = PropertyEnum.create("machineTier", MachineTier.class);
    private MachineTier[] machineTiers;

    public BlockMachineBase(Material material, String resourcePath, MachineTier... machineTiers) {
        super(material, resourcePath);
        this.machineTiers = machineTiers;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockRenderer() {
        final String resourcePath = String.format("%s:%s-", Reference.MOD_ID, this.resourcePath);
        final String badPath = String.format("%s:badblock", Reference.MOD_ID);

        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Override
            protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
                Map<IProperty<?>, Comparable<?>> blockStates = Maps.newLinkedHashMap(state.getProperties());

                if (!Arrays.asList(machineTiers).contains(blockStates.get(MACHINETIER)))
                    return new ModelResourceLocation(badPath, "");

                if (blockStates.containsKey(MACHINETIER))
                    blockStates.remove(MACHINETIER);

                return new ModelResourceLocation(resourcePath + ((MachineTier) state.getValue(MACHINETIER)).getName(), getPropertyString(blockStates));
            }
        });
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockItemRenderer() {
        final String resourcePath = String.format("%s:%s-", Reference.MOD_ID, this.resourcePath);
        final String badPath = String.format("%s:badblock", Reference.MOD_ID);

        for (MachineTier machineTier : MachineTier.values()) {
            if (!Arrays.asList(machineTiers).contains(machineTier)) {
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), machineTier.getMeta(), new ModelResourceLocation(badPath, "inventory"));
            } else {
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), machineTier.getMeta(), new ModelResourceLocation(resourcePath + machineTier.getName(), "inventory"));
            }
        }
    }

    @Override
    public void getSubBlocks(Item itemIn, CreativeTabs tab, List<ItemStack> list) {
        if (machineTiers.length == 0)
            super.getSubBlocks(itemIn, tab, list);

        for (MachineTier machineTier : machineTiers) {
            list.add(new ItemStack(this, 1, machineTier.getMeta()));
        }
    }

}
