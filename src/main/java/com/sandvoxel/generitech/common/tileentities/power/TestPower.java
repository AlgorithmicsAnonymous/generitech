package com.sandvoxel.generitech.common.tileentities.power;

import com.sandvoxel.generitech.GeneriTech;
import com.sandvoxel.generitech.common.blocks.Blocks;
import com.sandvoxel.generitech.common.integrations.waila.IWailaBodyMessage;
import com.sandvoxel.generitech.common.inventory.InternalInventory;
import com.sandvoxel.generitech.common.inventory.InventoryOperation;
import com.sandvoxel.generitech.common.network.messages.power.PacketPower;
import com.sandvoxel.generitech.common.tileentities.TileEntityInventoryBase;
import com.sandvoxel.generitech.common.tileentities.TileEntityMachineBase;
import com.sandvoxel.generitech.common.util.LanguageHelper;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import net.darkhax.tesla.api.ITeslaConsumer;
import net.darkhax.tesla.api.ITeslaHolder;
import net.darkhax.tesla.api.ITeslaProducer;
import net.darkhax.tesla.api.implementation.BaseTeslaContainer;
import net.darkhax.tesla.capability.TeslaCapabilities;
import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;
import java.util.List;

public class TestPower extends TileEntityInventoryBase implements ITeslaProducer, net.minecraft.util.ITickable, IWailaBodyMessage, ITeslaConsumer, ITeslaHolder {
    private BaseTeslaContainer container = new BaseTeslaContainer(0, 10000, 1000, 1000);
    private InternalInventory inventory = new InternalInventory(this, 1);
    private boolean[] flag = {false, false, false, false, false, false};
    private int T0transfer = 100;


    @Override
    public long takePower(long power, boolean simulated) {
        return power;
    }

    @Override
    public void update() {
        BlockPos pos = getPos();
        World worldIn = getWorld();


        if (worldObj.isBlockPowered(pos)) {
            container.givePower(1000, false);

        }
        long stored = getStoredPower();

        TileEntity te = worldObj.getTileEntity(pos);


        if (flag[0]) {
            TileEntity te1 = worldObj.getTileEntity(pos.up());


            if (te1 instanceof TileEntityMachineBase && getTransfer(pos.up()) > 100 && container.getStoredPower() != 0) {

                if (te instanceof ITeslaHolder) {
                    if (((ITeslaHolder) te).getStoredPower() > 100) {
                        if (!worldObj.isRemote) {
                            GeneriTech.network.sendToServer(new PacketPower(T0transfer, pos.getX(), pos.getZ(), pos.getY() + 1));
                        }
                        this.container.takePower(T0transfer, false);
                    } else {
                        if (!worldObj.isRemote) {
                            GeneriTech.network.sendToServer(new PacketPower(((ITeslaHolder) te).getStoredPower(), pos.getX(), pos.getZ(), pos.getY() + 1));
                        }
                        this.container.takePower(((ITeslaHolder) te).getStoredPower(), false);
                    }
                }
            } else {
                if (te1 instanceof TileEntityMachineBase && container.getStoredPower() != 0) {
                    if (!worldObj.isRemote) {
                        GeneriTech.network.sendToServer(new PacketPower(getTransfer(pos.down()), pos.getX(), pos.getZ(), pos.getY() + -1));
                    }
                    this.container.takePower(getTransfer(pos.down()), false);
                }

            }


            if (te instanceof ITeslaHolder && te1 instanceof ITeslaHolder && te1 instanceof TileEntityMachineBase == false) {
                long abbs = ((ITeslaHolder) te).getStoredPower() - ((ITeslaHolder) te1).getStoredPower();
                //System.out.println(abbs);
                if (abbs > 100) {
                    if (!worldObj.isRemote) {
                        GeneriTech.network.sendToServer(new PacketPower(T0transfer, pos.getX(), pos.getZ(), pos.getY() + 1));

                    }
                    this.container.takePower(T0transfer, false);
                } else {
                    if (te1 instanceof ITeslaConsumer && abbs != 0 && abbs / 2 != 0) {
                        if (!worldObj.isRemote)

                            GeneriTech.network.sendToServer(new PacketPower(abbs / 2, pos.getX(), pos.getZ(), pos.getY() + 1));
                    }
                    this.container.takePower(abbs / 2, false);
                    if (te1 instanceof ITeslaConsumer && abbs == 1) {
                        {
                            System.out.println(abbs);

                            if (!worldObj.isRemote) {
                                GeneriTech.network.sendToServer(new PacketPower(1, pos.getX(), pos.getZ(), pos.getY() + 1));
                            }
                            this.container.takePower(1, false);
                        }
                    }
                }
            }
        }


        if (flag[1]) {
            TileEntity te2 = worldObj.getTileEntity(pos.down());

            if (te2 instanceof TileEntityMachineBase && getTransfer(pos.down()) > 100 && container.getStoredPower() != 0) {
                if (te instanceof ITeslaHolder) {
                    if (((ITeslaHolder) te).getStoredPower() > 100) {
                        if (!worldObj.isRemote) {
                            GeneriTech.network.sendToServer(new PacketPower(T0transfer, pos.getX(), pos.getZ(), pos.getY() + -1));
                        }
                        this.container.takePower(T0transfer, false);
                    } else {
                        if (!worldObj.isRemote) {
                            GeneriTech.network.sendToServer(new PacketPower(((ITeslaHolder) te).getStoredPower(), pos.getX(), pos.getZ(), pos.getY() + -1));
                        }
                        this.container.takePower(((ITeslaHolder) te).getStoredPower(), false);
                    }
                }
            } else {
                if (te2 instanceof TileEntityMachineBase && container.getStoredPower() != 0) {
                    if (!worldObj.isRemote) {
                        GeneriTech.network.sendToServer(new PacketPower(getTransfer(pos.down()), pos.getX(), pos.getZ(), pos.getY() + -1));
                    }
                    this.container.takePower(getTransfer(pos.down()), false);
                }
            }


            if (te instanceof ITeslaHolder && te2 instanceof ITeslaHolder && te2 instanceof TileEntityMachineBase == false) {
                long abbs = ((ITeslaHolder) te).getStoredPower() - ((ITeslaHolder) te2).getStoredPower();
                //System.out.println(abbs);
                if (abbs > 100) {
                    if (!worldObj.isRemote) {
                        GeneriTech.network.sendToServer(new PacketPower(T0transfer, pos.getX(), pos.getZ(), pos.getY() + -1));

                    }
                    this.container.takePower(T0transfer, false);
                } else {
                    if (te2 instanceof ITeslaConsumer && abbs != 0 && abbs / 2 != 0) {
                        if (!worldObj.isRemote) {
                            GeneriTech.network.sendToServer(new PacketPower(abbs / 2, pos.getX(), pos.getZ(), pos.getY() + -1));
                        }
                        this.container.takePower(abbs / 2, false);
                        if (te2 instanceof ITeslaConsumer && abbs == 1) {
                            if (!worldObj.isRemote) {
                                GeneriTech.network.sendToServer(new PacketPower(1, pos.getX(), pos.getZ(), pos.getY() + -1));
                            }
                            this.container.takePower(1, false);
                        }
                    }
                }

            }

        }


        flag[0] = this.canConnectTo(worldIn, pos.up());
        flag[1] = this.canConnectTo(worldIn, pos.down());
        flag[2] = this.canConnectTo(worldIn, pos.north());
        flag[3] = this.canConnectTo(worldIn, pos.south());
        flag[4] = this.canConnectTo(worldIn, pos.east());
        flag[5] = this.canConnectTo(worldIn, pos.west());


    }


    public long getTransfer(BlockPos pos) {
        TileEntity tile = worldObj.getTileEntity(pos);

        if (tile instanceof ITeslaHolder) {
            return ((ITeslaHolder) tile).getCapacity() - ((ITeslaHolder) tile).getStoredPower();
        }

        return 0;
    }


    public long getLeft() {
        BlockPos pos = getPos();
        TileEntity tile = worldObj.getTileEntity(pos.up());

        if (tile instanceof ITeslaHolder) {
            return ((ITeslaHolder) tile).getStoredPower();
        }

        return 0;
    }


    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
        Block block = worldIn.getBlockState(pos).getBlock();
        if (block == Blocks.BLOCK_FURNACE.getBlock() || block == Blocks.BLOCK_PULVERIZER.getBlock() || block == Blocks.BLOCK_CABLES.getBlock()) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        this.container = new BaseTeslaContainer(nbtTagCompound.getCompoundTag("TeslaContainer"));
        super.readFromNBT(nbtTagCompound);

    }


    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {

        nbtTagCompound.setTag("TeslaContainer", this.container.serializeNBT());
        return super.writeToNBT(nbtTagCompound);
    }


    @Override
    public IInventory getInternalInventory() {
        return inventory;
    }

    @Override
    public void onChangeInventory(IInventory inv, int slot, InventoryOperation operation, ItemStack removed, ItemStack added) {

    }

    @Override
    public int[] getAccessibleSlotsBySide(EnumFacing side) {
        return new int[0];
    }

    @Nullable
    @Override
    public ItemStack removeStackFromSlot(int index) {
        return null;
    }


    @Override
    public List<String> getWailaBodyToolTip(ItemStack itemStack, List<String> currentTip, IWailaDataAccessor accessor, IWailaConfigHandler config) {

        currentTip.add(LanguageHelper.LABEL.translateMessage("Power ") + Long.toString(container.getStoredPower()));

        return currentTip;
    }

    @Override
    public long givePower(long power, boolean simulated) {
        System.out.println("packet sent :" + power);
        this.markForUpdate();
        this.markDirty();
        container.givePower(power, simulated);
        return power;
    }

    @Override
    public long getStoredPower() {
        return container.getStoredPower();
    }

    @Override
    public long getCapacity() {
        return container.getCapacity();
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {

        // This method is where other things will try to access your TileEntity's Tesla
        // capability. In the case of the analyzer, is a consumer, producer and holder so we
        // can allow requests that are looking for any of those things. This example also does
        // not care about which side is being accessed, however if you wanted to restrict which
        // side can be used, for example only allow power input through the back, that could be
        // done here.
        if (capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER)
            return (T) this.container;

        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {

        // This method replaces the instanceof checks that would be used in an interface based
        // system. It can be used by other things to see if the TileEntity uses a capability or
        // not. This example is a Consumer, Producer and Holder, so we return true for all
        // three. This can also be used to restrict access on certain sides, for example if you
        // only accept power input from the bottom of the block, you would only return true for
        // Consumer if the facing parameter was down.
        if (capability == TeslaCapabilities.CAPABILITY_CONSUMER || capability == TeslaCapabilities.CAPABILITY_HOLDER)
            return true;

        return super.hasCapability(capability, facing);
    }
}
