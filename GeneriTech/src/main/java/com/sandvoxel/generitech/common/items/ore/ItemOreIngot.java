package com.sandvoxel.generitech.common.items.ore;

import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.common.blocks.Blocks;
import com.sandvoxel.generitech.common.enumtypes.EnumOreType;
import com.sandvoxel.generitech.common.enumtypes.EnumOres;
import com.sandvoxel.generitech.common.items.ItemBase;
import com.sandvoxel.generitech.common.items.Items;
import com.sandvoxel.generitech.common.util.IProvideRecipe;
import com.sandvoxel.generitech.common.util.IProvideSmelting;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.util.List;


public class ItemOreIngot extends ItemBase implements IProvideRecipe, IProvideSmelting {

    public ItemOreIngot(){
        super("ore/ingot");
        this.setHasSubtypes(true);
        this.setCreativeTab(GeneriTechTabs.ORE);
        this.setInternalName("oreingot");
    }

    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
        for (int i = 0; i < EnumOres.values().length; i++) {
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.INGOT)) {
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
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.INGOT)) {
                ModelLoader.setCustomModelResourceLocation(this, i, new ModelResourceLocation(Reference.MOD_ID + ":" + resourcePath + "/ingot-" + EnumOres.byMeta(i).getName(), "inventory"));
            }
        }
    }

    @Override
    public void RegisterRecipes() {
        for (int i = 0; i < EnumOres.values().length; i++) {
            // Block -> 9x Ingots
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.INGOT) && EnumOres.byMeta(i).isTypeSet(EnumOreType.BLOCK)) {
                GameRegistry.addRecipe(new ShapelessOreRecipe(Items.ITEM_ORE_INGOT.getStack(9, i), "block" + EnumOres.byMeta(i).getOreName()));
            }

            // 9x Nuggets -> Ingot
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.INGOT) && EnumOres.byMeta(i).isTypeSet(EnumOreType.NUGGET)) {
                GameRegistry.addRecipe(new ShapedOreRecipe(Items.ITEM_ORE_INGOT.getStack(1, i),
                        "xxx",
                        "xxx",
                        "xxx",
                        'x', "nugget" + EnumOres.byMeta(i).getOreName())
                );
            }

            // 9x Iron Nuggets -> Iron Ingot
            if (EnumOres.byMeta(i) == EnumOres.IRON) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(net.minecraft.init.Items.iron_ingot, 1),
                        "xxx",
                        "xxx",
                        "xxx",
                        'x', "nugget" + EnumOres.byMeta(i).getOreName())
                );
            }
        }
    }

    @Override
    public void RegisterSmelting() {
        for (int i = 0; i < EnumOres.values().length; i++) {
            // Register Ore -> Ingot
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.ORE) && EnumOres.byMeta(i).isTypeSet(EnumOreType.INGOT))
                GameRegistry.addSmelting(Blocks.BLOCK_ORE.getStack(1, i), Items.ITEM_ORE_INGOT.getStack(1, i), 0);

            // Register Dust -> Ingot
            if (EnumOres.byMeta(i).isTypeSet(EnumOreType.DUST) && EnumOres.byMeta(i).isTypeSet(EnumOreType.INGOT))
                GameRegistry.addSmelting(Items.ITEM_ORE_DUST.getStack(1, i), Items.ITEM_ORE_INGOT.getStack(1, i), 0);
        }
    }
}
