package com.sandvoxel.generitech.common.items.ore;

import com.sandvoxel.generitech.common.enumtypes.EnumOres;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

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