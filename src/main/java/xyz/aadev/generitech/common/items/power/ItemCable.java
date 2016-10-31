package xyz.aadev.generitech.common.items.power;

import xyz.aadev.generitech.api.util.MachineTier;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemCable extends ItemBlock {
    public ItemCable(Block block) {
        super(block);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String name = super.getUnlocalizedName();
        String machineTier = MachineTier.byMeta(stack.getItemDamage()).getName();

        return name + "." + machineTier;
    }
}
