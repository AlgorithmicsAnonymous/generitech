package com.sandvoxel.generitech.client.gui.machines;

import com.sandvoxel.generitech.api.util.MachineTier;
import com.sandvoxel.generitech.client.gui.GuiBase;
import com.sandvoxel.generitech.common.container.machines.ContainerPulverizer;
import com.sandvoxel.generitech.common.tileentities.machines.TileEntityPulverizer;
import com.sandvoxel.generitech.common.util.GuiHelper;
import com.sandvoxel.generitech.common.util.LanguageHelper;
import com.sandvoxel.generitech.common.util.LogHelper;
import net.minecraft.entity.player.InventoryPlayer;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.lwjgl.opengl.GL11;

public class GuiPulverizer extends GuiBase {
    private TileEntityPulverizer tileEntity;
    private GuiHelper guiHelper = new GuiHelper();
    private MachineTier machineTier;

    public GuiPulverizer(InventoryPlayer inventoryPlayer, TileEntityPulverizer tileEntity) {
        super(new ContainerPulverizer(inventoryPlayer, tileEntity));
        this.xSize = 176;
        this.ySize = 166;
        this.tileEntity = tileEntity;
        this.machineTier = MachineTier.byMeta(tileEntity.getBlockMetadata());
    }

    @Override
    public void drawBG(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        bindTexture("gui/machines/pulverizer.png");
        drawTexturedModalRect(paramInt1, paramInt2, 0, 0, this.xSize, this.ySize);

/*        int timeTotal = tileEntity.getTotalProcessTime();
        int timeCurrent = tileEntity.getTicksRemaining();
        int pixels = 0;

        int tmp = timeTotal - timeCurrent;

        if(timeTotal > 0)
            tmp = tmp / timeCurrent;

        pixels = Math.round(tmp * 39);

        LogHelper.info(">>>>> " + pixels);

        // Progress Arrow
        drawTexturedModalRect(paramInt1 + 74, paramInt2 + 35, 176, 0, pixels, 17);*/

        drawTexturedModalRect(paramInt1 + 25, paramInt2 + 63, 47, 34, 18, 18);

        if(machineTier == MachineTier.TIER_1)
        {
            drawTexturedModalRect(paramInt1 + 151, paramInt2 + 63, 47, 34, 18, 18);
        }
        else if(machineTier == MachineTier.TIER_2)
        {
            drawTexturedModalRect(paramInt1 + 151, paramInt2 + 63, 47, 34, 18, 18);
            drawTexturedModalRect(paramInt1 + 133, paramInt2 + 63, 47, 34, 18, 18);
        }
        else if(machineTier == MachineTier.TIER_3)
        {
            drawTexturedModalRect(paramInt1 + 151, paramInt2 + 63, 47, 34, 18, 18);
            drawTexturedModalRect(paramInt1 + 133, paramInt2 + 63, 47, 34, 18, 18);
            drawTexturedModalRect(paramInt1 + 115, paramInt2 + 63, 47, 34, 18, 18);
        }


    }

    @Override
    public void drawFG(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        this.fontRendererObj.drawString(tileEntity.hasCustomName() ? tileEntity.getCustomName() : LanguageHelper.NONE.translateMessage(tileEntity.getUnlocalizedName()), 16, 12, 0xFFFFFF);

        int timeTotal = tileEntity.getTotalProcessTime();
        int timeCurrent = tileEntity.getTicksRemaining();

        float timePercent = ((((float) timeTotal - (float) timeCurrent) / (float) timeTotal)) * 100;

        if(machineTier == MachineTier.TIER_0)
        {

        }
        else
        {
            guiHelper.drawVerticalProgressBar(12, 28, 8, 50, 50, colorBackground, colorBorder, colorProgressBackground);
        }


        guiHelper.drawHorizontalProgressBar(72, 39, 40, 8, Math.round(timePercent), colorBackground, colorBorder, colorProgressBackground);
        String progressLabel = String.format("%d%%", Math.round(timePercent));
        guiHelper.drawCenteredStringWithShadow(30, 39, 126, progressLabel, colorFont);

    }
}