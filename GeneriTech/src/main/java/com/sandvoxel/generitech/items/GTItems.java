package com.sandvoxel.generitech.items;

import com.sandvoxel.generitech.GeneriTechTabs;
import com.sandvoxel.generitech.items.ore.ItemOreDust;
import com.sandvoxel.generitech.util.IItemRenderer;
import com.sandvoxel.generitech.util.Platform;
import com.sandvoxel.generitech.util.RegistrationHelper;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;


public enum GTItems {

    ITEM_ORE_DUST("oredust", new ItemOreDust(), GeneriTechTabs.ORE);

    private Item item;
    private final String name;

    GTItems(String name, Item item) {
        this(name, item, null);
    }

    GTItems(String name, Item item, CreativeTabs creativeTabs) {
        this.item = item;
        this.name = name;
        item.setUnlocalizedName(name);
        item.setCreativeTab(creativeTabs);
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

    public static void registerItems() {
        for (GTItems i : GTItems.values()) {
            i.register();
        }
    }

    private void register() {
        item = RegistrationHelper.registerItem(name, item.getClass());
    }
}
