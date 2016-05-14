package com.sandvoxel.generitech.common.blocks;


import com.sandvoxel.generitech.common.blocks.machines.BlockFurnace;
import com.sandvoxel.generitech.common.blocks.machines.BlockPulverizer;
import com.sandvoxel.generitech.common.blocks.ores.BlockOre;
import com.sandvoxel.generitech.common.items.machines.ItemFurnace;
import com.sandvoxel.generitech.common.items.machines.ItemPulverizer;
import com.sandvoxel.generitech.common.items.ore.ItemOre;
import com.sandvoxel.generitech.common.util.RegistrationHelper;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public enum Blocks {
    BLOCK_ORE(BlockOre.class, ItemOre.class),

    BLOCK_FURNACE(BlockFurnace.class, ItemFurnace.class),
    BLOCK_PULVERIZER(BlockPulverizer.class, ItemPulverizer.class);

    private final Class<? extends BlockBase> blockClass;
    private final Class<? extends ItemBlock> itemBlockClass;
    private Block block;

    Blocks(Class<? extends BlockBase> blockClass) {
        this(blockClass, ItemBlock.class);
    }

    Blocks(Class<? extends BlockBase> blockClass, Class<? extends ItemBlock> itemBlockClass) {
        this.blockClass = blockClass;
        this.itemBlockClass = itemBlockClass;
    }

    public static void registerBlocks() {
        for (Blocks block : Blocks.values()) {
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
        block = RegistrationHelper.registerBlock(blockClass, itemBlockClass);
    }
}
