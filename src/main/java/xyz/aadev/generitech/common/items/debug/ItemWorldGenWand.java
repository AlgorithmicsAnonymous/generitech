package xyz.aadev.generitech.common.items.debug;

import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import xyz.aadev.aalib.common.items.ItemBase;
import xyz.aadev.generitech.GeneriTechTabs;
import xyz.aadev.generitech.common.blocks.Blocks;

import java.util.List;


public class ItemWorldGenWand extends ItemBase {
    public ItemWorldGenWand() {
        super("debug/worldgenwand");
        this.setCreativeTab(GeneriTechTabs.GENERAL);
        this.setInternalName("worldgenwand");
        this.setMaxStackSize(1);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

        Thread thread = new Thread(() -> {
            if (!worldIn.isRemote) {
                int semiDiameter = 30;
                AxisAlignedBB area = new AxisAlignedBB(playerIn.posX - semiDiameter, 0, playerIn.posZ - semiDiameter,
                        playerIn.posX + semiDiameter, 255, playerIn.posZ + semiDiameter);
                for (int x = (int) area.minX; x < area.maxX; x++) {
                    for (int y = (int) area.minY; y < area.maxY; y++) {
                        for (int z = (int) area.minZ; z < area.maxZ; z++) {
                            BlockPos pos = new BlockPos(x, y, z);
                            Block block = worldIn.getBlockState(pos).getBlock();

                            if (block != Blocks.BLOCK_ORE.getBlock())
                                worldIn.setBlockToAir(pos);
                        }
                    }
                }
            }
        });

        thread.start();

        return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        String name = super.getUnlocalizedName();
        tooltip.add(I18n.format(name + ".tooltip"));
        tooltip.add(I18n.format(name + ".tooltip2"));
    }

}
