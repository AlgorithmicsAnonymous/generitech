package com.Sandvoxel.GeneriTech.client.gui;

import com.Sandvoxel.GeneriTech.TileEntitys.TileEntityInventoryBase;
import com.Sandvoxel.GeneriTech.container.ContainerPulverizer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiPulverizer extends GuiContainer {

    public GuiPulverizer(IInventory playerInv, TileEntityInventoryBase te) {
        super(new ContainerPulverizer(playerInv, te));

        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        this.mc.getTextureManager().bindTexture(new ResourceLocation("generitech", "textures/gui/container/pulverizer.png"));
        drawTexturedModalRect(this.guiLeft, this.guiTop, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRendererObj.drawString("Metal Shredder", 8, 6, 4210752);
    }
}