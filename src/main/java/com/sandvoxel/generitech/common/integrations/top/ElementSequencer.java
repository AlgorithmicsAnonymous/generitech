package com.sandvoxel.generitech.common.integrations.top;

import io.netty.buffer.ByteBuf;
import mcjty.theoneprobe.api.IElement;
import net.minecraft.client.gui.Gui;

public class ElementSequencer implements IElement {

    private final long bits;
    private final int current;
    private final boolean large;

    public ElementSequencer(long bits, int current, boolean large) {
        this.bits = bits;
        this.current = current;
        this.large = large;
    }

    public ElementSequencer(ByteBuf buffer) {
        this(buffer.readLong(), buffer.readInt(), buffer.readBoolean());
    }

    private int getSize() {
        return large ? 5 : 3;
    }

    @Override
    public void render(int x, int y) {
        int size = getSize();

        for (int j = 0; j < 8; j++) {
            for(int k = 0; k < 8; k++) {
                final int bit = j * 8 + k;
                if(large && bit == current) {
                    Gui.drawRect(6 + x + k * size, y + j * size, 6 + x + k * size + size - 1,  y + j * size + size - 1, 0xffff0000);
                    Gui.drawRect(6 + x + k * size + 1, y + j * size + 1, 6 + x + k * size + size - 2, y + j * size + size - 2, ((bits >> bit) & 1) == 1 ? 0xffffffff : 0xff000000);
                } else {
                    Gui.drawRect(6 + x + k * size, y + j * size, 6 + x + k * size + size - 1, y + j * size + size - 1, ((bits >> bit) & 1) == 1 ? 0xffffffff : 0xff000000);
                }
            }
        }
    }

    @Override
    public int getWidth() {
        return 12 + getSize() * 8;
    }

    @Override
    public int getHeight() {
        return getSize() * 8;
    }

    @Override
    public void toBytes(ByteBuf buffer) {
        buffer.writeLong(bits);
        buffer.writeInt(current);
        buffer.writeBoolean(large);
    }

    @Override
    public int getID() {
        return TopCompat.ELEMENT_SEQUENCER;
    }
}
