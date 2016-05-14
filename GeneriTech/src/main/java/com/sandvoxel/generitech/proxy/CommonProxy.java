package com.sandvoxel.generitech.proxy;

import com.sandvoxel.generitech.GeneriTech;
import com.sandvoxel.generitech.client.gui.GuiHandler;
import com.sandvoxel.generitech.common.blocks.Blocks;
import com.sandvoxel.generitech.common.config.Config;
import com.sandvoxel.generitech.common.enumtypes.EnumOreType;
import com.sandvoxel.generitech.common.enumtypes.EnumOres;
import com.sandvoxel.generitech.common.items.Items;
import com.sandvoxel.generitech.common.util.IProvideEvent;
import com.sandvoxel.generitech.common.util.IProvideRecipe;
import com.sandvoxel.generitech.common.util.IProvideSmelting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;

public abstract class CommonProxy implements IProxy {
    @Override
    public void registerBlocks() {
        Blocks.registerBlocks();
    }

    @Override
    public void registerItems() {
        Items.registerItems();
    }

    @Override
    public void registerFurnaceRecipes() {
        for (Items item : Items.values()) {
            if (item.getItem() instanceof IProvideSmelting)
                ((IProvideSmelting) item.getItem()).RegisterSmelting();
        }

        for (Blocks block : Blocks.values()) {
            if (block.getBlock() instanceof IProvideSmelting)
                ((IProvideSmelting) block.getBlock()).RegisterSmelting();
        }
    }

    @Override
    public void registerOreDict() {
        for (EnumOres ores : EnumOres.values()) {
            int meta = ores.getMeta();
            String oreName = ores.getOreName();

            // Register Ore
            if (ores.isTypeSet(EnumOreType.ORE))
                OreDictionary.registerOre("ore" + oreName, Blocks.BLOCK_ORE.getStack(1, meta));

            // Register Ore Block
            if (ores.isTypeSet(EnumOreType.BLOCK))
                OreDictionary.registerOre("block" + oreName, Blocks.BLOCK_ORE_BLOCK.getStack(1, meta));

            // Register Ingot
            if (ores.isTypeSet(EnumOreType.INGOT))
                OreDictionary.registerOre("ingot" + oreName, Items.ITEM_ORE_INGOT.getStack(1, meta));

            // Register Dusts
            if (ores.isTypeSet(EnumOreType.DUST))
                OreDictionary.registerOre("dust" + oreName, Items.ITEM_ORE_DUST.getStack(1, meta));

            // Register Nuggets
            if (ores.isTypeSet(EnumOreType.NUGGET))
                OreDictionary.registerOre("nugget" + oreName, Items.ITEM_ORE_NUGGET.getStack(1, meta));
        }
    }

    @Override
    public void registerRecipes() {
        for (Items item : Items.values()) {
            if (item.getItem() instanceof IProvideRecipe)
                ((IProvideRecipe) item.getItem()).RegisterRecipes();
        }

        for (Blocks block : Blocks.values()) {
            if (block.getBlock() instanceof IProvideRecipe)
                ((IProvideRecipe) block.getBlock()).RegisterRecipes();
        }
    }

    @Override
    public void registerEvents() {
        for (Items item : Items.values()) {
            if (item.getItem() instanceof IProvideEvent)
                MinecraftForge.EVENT_BUS.register(item.getItem());
        }

        for (Blocks block : Blocks.values()) {
            if (block.getBlock() instanceof IProvideEvent)
                MinecraftForge.EVENT_BUS.register(block.getBlock());
        }
    }

    @Override
    public void registerGUIs() {
        NetworkRegistry.INSTANCE.registerGuiHandler(GeneriTech.instance, new GuiHandler());
    }

    @Override
    public void registerRenderers() {
        /** Client Side Only **/
    }

    @Override
    public void registerConfiguration(File configFile) {
        GeneriTech.configuration = Config.initConfig(configFile);
    }
}
