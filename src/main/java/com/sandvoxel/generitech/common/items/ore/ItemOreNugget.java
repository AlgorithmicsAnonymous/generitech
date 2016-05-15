package com.sandvoxel.generitech.common.items.ore;

import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.common.enumtypes.EnumOreType;
import com.sandvoxel.generitech.common.enumtypes.EnumOres;
import com.sandvoxel.generitech.common.items.ItemBase;
import com.sandvoxel.generitech.common.items.Items;
import com.sandvoxel.generitech.common.util.IProvideRecipe;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;


public class ItemOreNugget extends ItemBase implements IProvideRecipe {

    public ItemOreNugget(){
        super("ore/nugget");
        this.setHasSubtypes(true);
        this.setCreativeTab(GeneriTechTabs.ORE);
        this.setInternalName("orenugget");
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (int i = 0; i < EnumOres.values().length; i++) {
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.NUGGET)) {
                subItems.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = super.getUnlocalizedName();
        String oreName = EnumOres.byMeta(stack.getItemDamage()).getName();
        return name + "." + oreName;
    }

    @Override
    public void registerItemRenderer() {
        for (int i = 0; i < EnumOres.values().length; i++) {
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.NUGGET)) {
                ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(Reference.MOD_ID + ":" + resourcePath + "/nugget-" + EnumOres.byMeta(i).getName(), "inventory"));
            }
        }
    }

    @Override
    public void RegisterRecipes() {
        for (int i = 0; i < EnumOres.values().length; i++) {
            // Ingot -> 9x Nugget
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.INGOT) && EnumOres.byMeta(i).isTypeSet(EnumOreType.NUGGET)) {
                GameRegistry.addRecipe(new ShapelessOreRecipe(Items.ITEM_ORE_NUGGET.getStack(9, i), "ingot" + EnumOres.byMeta(i).getOreName()));
            }

            // Iron Ingot -> 9x Iron Nuggets
            if (EnumOres.byMeta(i) == EnumOres.IRON) {
                GameRegistry.addRecipe(new ShapelessOreRecipe(Items.ITEM_ORE_NUGGET.getStack(9, i), "ingotIron"));
            }
        }
    }
}
