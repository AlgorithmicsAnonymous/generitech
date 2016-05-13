package com.sandvoxel.generitech.items.ore;

import com.sandvoxel.generitech.Reference;
import com.sandvoxel.generitech.blocks.GTBlocks;
import com.sandvoxel.generitech.blocks.ores.BlockOre;
import com.sandvoxel.generitech.enumtypes.EnumOres;
import com.sandvoxel.generitech.items.GTItems;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import java.util.List;

public class ItemOre extends ItemBlock {
    public ItemOre(Block block) {
        super(block);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = super.getUnlocalizedName();
        String oreName = EnumOres.byID(stack.getItemDamage()).getName();

        return name + "." + oreName;
    }

}