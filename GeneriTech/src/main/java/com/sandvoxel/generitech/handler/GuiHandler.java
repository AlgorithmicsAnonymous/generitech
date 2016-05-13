package com.sandvoxel.generitech.handler;

import com.sandvoxel.generitech.client.gui.GuiPulverizer;
import com.sandvoxel.generitech.container.ContainerPulverizer;
import com.sandvoxel.generitech.tileentities.TileEntityInventoryBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

    public static final int PULVERIZER_GUI = 0;

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == PULVERIZER_GUI)
            return new ContainerPulverizer(player.inventory, (TileEntityInventoryBase) world.getTileEntity(new BlockPos(x, y, z)));

        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == PULVERIZER_GUI)
            return new GuiPulverizer(player.inventory, (TileEntityInventoryBase) world.getTileEntity(new BlockPos(x, y, z)));

        return null;
    }
}
