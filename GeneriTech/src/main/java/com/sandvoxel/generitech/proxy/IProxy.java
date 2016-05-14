package com.sandvoxel.generitech.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

public interface IProxy {
    /**
     * Register Blocks
     */
    void registerBlocks();

    /**
     * Register Items
     */
    void registerItems();

    /**
     * Register Furnace Recipes
     */
    void registerFurnaceRecipes();

    /**
     * Register Recipes
     */
    void registerRecipes();

    /**
     * Register Events
     */
    void registerEvents();

    /**
     * Register GUIs
     */
    void registerGUIs();

    /**
     * Register Renderers
     */
    void registerRenderers();

    void registerOreDict();

    /**
     * Register Configuration
     * @param configFile Configuration File
     */
    void registerConfiguration(File configFile);
}
