package com.sandvoxel.generitech.proxy;

import com.sandvoxel.generitech.api.registries.PulverizerRegistry;
import com.sandvoxel.generitech.blocks.GTBlocks;
import com.sandvoxel.generitech.GeneriTech;
import com.sandvoxel.generitech.enumtypes.EnumOreType;
import com.sandvoxel.generitech.enumtypes.EnumOres;
import com.sandvoxel.generitech.items.GTItems;
import com.sandvoxel.generitech.tileentities.GTTileEntities;
import com.sandvoxel.generitech.handler.GuiHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public abstract class CommonProxy implements IProxy {

    @Override
    public void preInit(FMLPreInitializationEvent e) {
        GTBlocks.init();
        GTBlocks.register();

        GTItems.registerItems();
    }

    @Override
    public void init(FMLInitializationEvent e) {
        NetworkRegistry.INSTANCE.registerGuiHandler(GeneriTech.instance, new GuiHandler());

        //todo: Move elsewhere
        PulverizerRegistry.register(new ItemStack(Blocks.redstone_ore), new ItemStack(Items.redstone), 0.8f);
        PulverizerRegistry.register(new ItemStack(GTBlocks.blockOre, 1, EnumOres.COPPER.getID()), new ItemStack(GTItems.ITEM_ORE_DUST.item, 1, EnumOres.COPPER.getID()), 0.8f);
        PulverizerRegistry.register(new ItemStack(GTBlocks.blockOre, 1, EnumOres.LEAD.getID()), new ItemStack(GTItems.ITEM_ORE_DUST.item, 1, EnumOres.LEAD.getID()), 0.8f);
        PulverizerRegistry.register(new ItemStack(GTBlocks.blockOre, 1, EnumOres.TIN.getID()), new ItemStack(GTItems.ITEM_ORE_DUST.item, 1, EnumOres.TIN.getID()), 0.8f);


    }

    @Override
    public void postInit(FMLPostInitializationEvent e){

    }
}