package com.sandvoxel.generitech.common.items;

import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.common.util.IItemRenderer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class ItemBase extends Item implements IItemRenderer {

    protected String resourcePath;

    public ItemBase(String resourcePath) {
        this.resourcePath = resourcePath;
        this.setCreativeTab(GeneriTechTabs.GENERAL);
    }

    @Override
    public String getUnlocalizedName() {
        String itemName = getUnwrappedUnlocalizedName(super.getUnlocalizedName());

        String test = String.format("item.%s.%s", Reference.MOD_ID, itemName);
        return test;
    }

    protected String getUnwrappedUnlocalizedName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerItemRenderer() {
        final String resourcePath = String.format("%s:%s", Reference.MOD_ID, this.resourcePath);

        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(resourcePath, "inventory"));
    }
}
