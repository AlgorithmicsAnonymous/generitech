package com.sandvoxel.generitech.common.items.debug;

import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.common.blocks.Blocks;
import com.sandvoxel.generitech.common.items.ItemBase;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


public class ItemWorldGenWand extends ItemBase {
    public ItemWorldGenWand() {
        super("debug/worldgenwand");
        this.setCreativeTab(GeneriTechTabs.GENERAL);
        this.setInternalName("worldgenwand");
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        int semiDiameter = 30;
        AxisAlignedBB area = new AxisAlignedBB(playerIn.posX - semiDiameter, 0, playerIn.posZ - semiDiameter,
                playerIn.posX + semiDiameter, 255, playerIn.posZ + semiDiameter);
        for (int x = (int) area.minX; x < area.maxX; x++) {
            for (int y = (int) area.minY; y < area.maxY; y++) {
                for (int z = (int) area.minZ; z < area.maxZ; z++) {
                    BlockPos pos = new BlockPos(x, y, z);
                    Block block = worldIn.getBlockState(pos).getBlock();

                    if(block != Blocks.BLOCK_ORE.getBlock())
                        worldIn.setBlockToAir(pos);
                }
            }
        }

        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }

}
