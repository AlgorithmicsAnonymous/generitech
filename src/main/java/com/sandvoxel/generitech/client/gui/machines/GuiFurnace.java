package com.sandvoxel.generitech.client.gui.machines;

import com.sandvoxel.generitech.client.gui.GuiBase;
import com.sandvoxel.generitech.common.container.machines.ContainerFurnace;
import com.sandvoxel.generitech.common.container.machines.ContainerPulverizer;
import com.sandvoxel.generitech.common.tileentities.machines.TileEntityFurnace;
import com.sandvoxel.generitech.common.tileentities.machines.TileEntityPulverizer;
import com.sandvoxel.generitech.common.util.GuiHelper;
import com.sandvoxel.generitech.common.util.LanguageHelper;
import net.minecraft.entity.player.InventoryPlayer;

public class GuiFurnace extends GuiBase {
    private TileEntityFurnace tileEntity;
    private GuiHelper guiHelper = new GuiHelper();

    public GuiFurnace(InventoryPlayer inventoryPlayer, TileEntityFurnace tileEntity) {
        super(new ContainerFurnace(inventoryPlayer, tileEntity));
        this.xSize = 176;
        this.ySize = 166;
    }

    @Override
    public void drawBG(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        bindTexture("gui/machines/furnace.png");
        drawTexturedModalRect(paramInt1, paramInt2, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void drawFG(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
//        this.fontRendererObj.drawString(tileEntity.hasCustomName() ? tileEntity.getCustomName() : LanguageHelper.NONE.translateMessage(tileEntity.getUnlocalizedName()), 8, 6, 4210752);
//        this.fontRendererObj.drawString(LanguageHelper.NONE.translateMessage("container.inventory"), 8, 58, 4210752);
    }
}