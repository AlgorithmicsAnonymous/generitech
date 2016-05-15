package com.sandvoxel.generitech.common.items;

import com.sandvoxel.generitech.common.items.ore.ItemOreDust;
import com.sandvoxel.generitech.common.items.ore.ItemOreIngot;
import com.sandvoxel.generitech.common.items.ore.ItemOreNugget;
import com.sandvoxel.generitech.common.items.tools.ItemToolWrench;
import com.sandvoxel.generitech.common.util.RegistrationHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


public enum Items {

    ITEM_ORE_INGOT(ItemOreIngot.class),
    ITEM_ORE_DUST(ItemOreDust.class),
    ITEM_ORE_NUGGET(ItemOreNugget.class),

    ITEM_TOOL_WRENCH(ItemToolWrench.class);

    private final Class<? extends Item> itemClass;
    private Item item;

    Items(Class<? extends Item> itemClass) {
        this.itemClass = itemClass;
    }

    public static void registerItems() {
        for (Items i : Items.values()) {
            i.registerItem();
        }
    }

    public ItemStack getStack() {
        return new ItemStack(item);
    }

    public ItemStack getStack(int size) {
        return new ItemStack(item, size);
    }

    public ItemStack getStack(int size, int damage) {
        return new ItemStack(item, size, damage);
    }

    public Item getItem() {
        return this.item;
    }

    private void registerItem() {
        item = RegistrationHelper.registerItem(itemClass);
    }
}
