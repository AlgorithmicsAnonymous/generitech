package com.sandvoxel.generitech.common.items.tools;

import com.google.common.collect.Sets;
import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.common.items.ItemBaseTool;
import com.sandvoxel.generitech.common.items.Items;
import com.sandvoxel.generitech.common.util.IProvideEvent;
import com.sandvoxel.generitech.common.util.IProvideRecipe;
import com.sandvoxel.generitech.common.util.Platform;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.Set;

public class ItemToolWrench extends ItemBaseTool implements IProvideRecipe, IProvideEvent {

    public static Set blocksEffectiveAgainst = Sets.newHashSet(new Block[]{});
    public static boolean canHarvest = false;
    public static ToolMaterial toolMaterial = EnumHelper.addToolMaterial("GENERITECH_WRENCH", 3, 160, 8.0F, 3.0F, 18);
    private static IBlockState blockHarvest = null;

    public ItemToolWrench() {
        super(3.0F, -2F, toolMaterial, blocksEffectiveAgainst, "tools/toolWrench");
        this.setUnlocalizedName("tool_wrench");
        this.setCreativeTab(GeneriTechTabs.TOOL);
        this.setInternalName("tool_wrench");
    }

    @Override
    public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return true;
    }

    @Override
    public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        Block block = world.getBlockState(pos).getBlock();

        if (block != null && !player.isSneaking()) {
            if (Platform.isClient())
                return !world.isRemote ? EnumActionResult.FAIL : EnumActionResult.PASS;

            if (block.rotateBlock(world, pos, side)) {
                player.swingArm(hand);
                return !world.isRemote ? EnumActionResult.FAIL : EnumActionResult.PASS;
            }
        }

        return EnumActionResult.FAIL;
    }

    @Override
    public void RegisterRecipes() {
        GameRegistry.addRecipe(new ShapedOreRecipe(Items.ITEM_TOOL_WRENCH.getStack(),
                " y ",
                " xy",
                "x  ",
                'x', "ingotIron",
                'y', "nuggetIron"
        ));
    }
}
