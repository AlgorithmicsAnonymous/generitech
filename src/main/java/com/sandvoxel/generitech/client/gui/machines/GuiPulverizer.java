package com.sandvoxel.generitech.client.gui.machines;

import com.sandvoxel.generitech.api.util.MachineTier;
import com.sandvoxel.generitech.client.gui.GuiBase;
import com.sandvoxel.generitech.common.container.machines.ContainerPulverizer;
import com.sandvoxel.generitech.common.tileentities.machines.TileEntityPulverizer;
import com.sandvoxel.generitech.common.util.GuiHelper;
import com.sandvoxel.generitech.common.util.LanguageHelper;
import com.sandvoxel.generitech.common.util.LogHelper;
import net.darkhax.tesla.api.BaseTeslaContainer;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.darkhax.tesla.lib.TeslaUtils;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumFacing;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class GuiPulverizer extends GuiBase {
    private TileEntityPulverizer tileEntity;
    private GuiHelper guiHelper = new GuiHelper();
    private MachineTier machineTier;
    private HashMap<Rectangle, List<String>> tooltips = new HashMap<Rectangle, List<String>>();
    ITeslaHolder container;
    Rectangle powerBar;

    public GuiPulverizer(InventoryPlayer inventoryPlayer, TileEntityPulverizer tileEntity) {
        super(new ContainerPulverizer(inventoryPlayer, tileEntity));
        this.xSize = 176;
        this.ySize = 166;
        this.tileEntity = tileEntity;
        this.machineTier = MachineTier.byMeta(tileEntity.getBlockMetadata());

        if(machineTier == MachineTier.TIER_0)
        {
        }
        else
        {
            this.container = tileEntity.getCapability(TeslaCapabilities.CAPABILITY_HOLDER, EnumFacing.DOWN);
            powerBar = new Rectangle(9, 25, 14, 55);
        }
    }

    @Override
    public void drawBG(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
        bindTexture("gui/machines/pulverizer1.png");
        drawTexturedModalRect(paramInt1, paramInt2, 0, 0, this.xSize, this.ySize);

        drawTexturedModalRect(paramInt1 + 25, paramInt2 + 63, 47, 34, 18, 18);

        if(machineTier == MachineTier.TIER_0)
        {
            drawTexturedModalRect(paramInt1 + 28, paramInt2 + 38, 176, 31, 14, 14);
            int fireOffset = tileEntity.getFuelOffset() + 1; // (x + 1) 1 and 11
            drawTexturedModalRect(paramInt1 + 28, paramInt2 + 38 + fireOffset, 176, 18 + fireOffset, 14, 14 - fireOffset);
        }
        else if(machineTier == MachineTier.TIER_1)
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
            long powerCapacity = container.getCapacity();
            long powerCurrent = container.getStoredPower();

            int powerPercent = powerCurrent > 0 ? (int)(powerCurrent * 100d / powerCapacity) : 0;

            guiHelper.drawVerticalProgressBar(12, 28, 8, 50, powerPercent, colorBackground, colorBorder, colorProgressBackground);
        }


        guiHelper.drawHorizontalProgressBar(72, 39, 40, 8, Math.round(timePercent), colorBackground, colorBorder, colorProgressBackground);
        String progressLabel = String.format("%d%%", Math.round(timePercent));
        guiHelper.drawCenteredStringWithShadow(30, 39, 126, progressLabel, colorFont);

    }

    @Override
    public void drawScreen(int mouse_x, int mouse_y, float btn) {
        super.drawScreen(mouse_x, mouse_y, btn);

        Point currentMouse = new Point(mouse_x - guiLeft, mouse_y - guiTop);
        if(tooltips != null) {
            for (Rectangle rectangle : tooltips.keySet()) {
                if (rectangle.contains(currentMouse)) {
                    ArrayList<String> messages = new ArrayList<String>(tooltips.get(rectangle));
                    renderToolTip(messages, mouse_x, mouse_y);
                }
            }
        }

        if(machineTier == MachineTier.TIER_0)
        {

        }
        else
        {
            if(powerBar.contains(currentMouse))
            {
                ArrayList<String> powerMessage = new ArrayList<String>();
                powerMessage.add(TeslaUtils.getDisplayableTeslaCount(container.getStoredPower()));
                renderToolTip(powerMessage, mouse_x, mouse_y);
            }
        }
    }
}