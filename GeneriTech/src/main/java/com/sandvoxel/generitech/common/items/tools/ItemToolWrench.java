package com.sandvoxel.generitech.common.items.tools;

import com.google.common.collect.Sets;
import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.common.items.ItemBaseTool;
import com.sandvoxel.generitech.common.items.Items;
import com.sandvoxel.generitech.common.util.IProvideRecipe;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Set;

public class ItemToolWrench extends ItemBaseTool implements IProvideRecipe {

    public static Set blocksEffectiveAgainst = Sets.newHashSet(new Block[]{});
    public static boolean canHarvest = false;
    public static ToolMaterial toolMaterial = EnumHelper.addToolMaterial("GENERITECH_WRENCH", 3, 100, 15.0F, 4.0F, 30);
    private static IBlockState blockHarvest = null;

    public ItemToolWrench() {
        super(3.0F, 1.0F, toolMaterial, blocksEffectiveAgainst, "tools/toolWrench");
        this.setUnlocalizedName("tool_wrench");
        this.setCreativeTab(GeneriTechTabs.TOOL);
        this.setInternalName("tool_wrench");
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public void RegisterRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(Items.ITEM_TOOL_WRENCH.getStack(),
                " yz",
                " xy",
                "x  ",
                'x', "stickWood",
                'y', "ingotIron",
                'z', "nuggetIron"
        ));

        GameRegistry.addRecipe(new ShapedOreRecipe(Items.ITEM_TOOL_WRENCH.getStack(),
                "zy ",
                "yx ",
                "  x",
                'x', "stickWood",
                'y', "ingotIron",
                'z', "nuggetIron"
        ));
    }
}
