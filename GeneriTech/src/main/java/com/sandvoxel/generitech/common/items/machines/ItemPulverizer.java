package com.sandvoxel.generitech.common.items.machines;

import com.sandvoxel.generitech.common.enumtypes.EnumOres;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemPulverizer extends ItemBlock {
    public ItemPulverizer(Block block) {
        super(block);
        this.setMaxDamage(0);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

}
