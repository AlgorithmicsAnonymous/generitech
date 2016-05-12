package com.Sandvoxel.GeneriTech.EnumTypes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.IStringSerializable;

/**
 * Created by koval on 5/8/2016.
 */
public enum EnumMachine implements IStringSerializable {

        OFF(0, "off"),
        ON(1 ,"on");


        private final String name;
        private final int ID;

        EnumMachine(int ID, String name)
        {
            this.name = name;
            this.ID = ID;
        }

        public String toString()
        {
            return this.getName();
        }
        @Override
        public String getName()
        {
            return this.name;
        }
        public int getID() {
            return ID;
        }

    }



