package com.sandvoxel.generitech.items.ore;

import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.enumtypes.EnumOreType;
import com.sandvoxel.generitech.enumtypes.EnumOres;
import com.sandvoxel.generitech.items.GTItems;
import com.sandvoxel.generitech.items.ItemBase;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;

import java.util.List;


public class ItemOreDust extends ItemBase {

    public ItemOreDust(){
        super("ore/dust");
        this.setHasSubtypes(true);
        this.setCreativeTab(GeneriTechTabs.ORE);
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (int i = 0; i < EnumOres.values().length; i++) {
            if (EnumOres.byID(i).isTypeSet(EnumOreType.DUST)) {
                subItems.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = super.getUnlocalizedName();
        String oreName = EnumOres.byID(stack.getItemDamage()).getName();
        return name + "." + oreName;
    }

    @Override
    public void registerItemRenderer() {
        for (int i = 0; i < EnumOres.values().length; i++) {
            if (EnumOres.byID(i).isTypeSet(EnumOreType.DUST)) {
                ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(Reference.MOD_ID + ":ore/dust" + EnumOres.byID(i).getName(), "inventory"));
            }
        }
    }
}
