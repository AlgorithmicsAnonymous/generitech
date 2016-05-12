package com.Sandvoxel.GeneriTech.handler;

import com.Sandvoxel.GeneriTech.TileEntitys.TileEntityInventoryBase;
import com.Sandvoxel.GeneriTech.client.gui.GuiPulverizer;
import com.Sandvoxel.GeneriTech.container.ContainerPulverizer;
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
