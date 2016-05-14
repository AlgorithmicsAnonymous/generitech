package com.sandvoxel.generitech.blocks;


import com.sandvoxel.generitech.blocks.machines.BlockFurnace;
import com.sandvoxel.generitech.blocks.machines.BlockPulverizer;
import com.sandvoxel.generitech.blocks.ores.BlockOre;
import com.sandvoxel.generitech.items.machines.ItemFurnace;
import com.sandvoxel.generitech.items.machines.ItemPulverizer;
import com.sandvoxel.generitech.items.ore.ItemOre;
import com.sandvoxel.generitech.util.RegistrationHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public enum GTBlocks {
    BLOCK_ORE("ore", BlockOre.class, ItemOre.class),

    BLOCK_FURNACE("furnace", BlockFurnace.class, ItemFurnace.class),
    BLOCK_PULVERIZER("pulverizer", BlockPulverizer.class, ItemPulverizer.class);

    private final Class<? extends BlockBase> blockClass;
    private final Class<? extends ItemBlock> itemBlockClass;
    private final String name;
    private Block block;

    GTBlocks(String name, Class<? extends BlockBase> blockClass) {
        this(name, blockClass, ItemBlock.class);
    }

    GTBlocks(String name, Class<? extends BlockBase> blockClass, Class<? extends ItemBlock> itemBlockClass) {
        this.blockClass = blockClass;
        this.itemBlockClass = itemBlockClass;
        this.name = name;
    }

    public static void registerBlocks() {
        for (GTBlocks block : GTBlocks.values()) {
            block.registerBlock();
        }
    }

    public ItemStack getStack() {
        return new ItemStack(block);
    }

    public ItemStack getStack(int size) {
        return new ItemStack(block, size);
    }

    public ItemStack getStack(int size, int meta) {
        return new ItemStack(block, size, meta);
    }

    public Block getBlock() {
        return this.block;
    }

    private void registerBlock() {
        block = RegistrationHelper.registerBlock(name, blockClass, itemBlockClass);
    }
}
