package dmillerw.armor.core.inventory;

import dmillerw.armor.core.network.PacketHandler;
import dmillerw.armor.core.network.packet.PacketSyncArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class InventoryArmor implements IInventory {

    public ItemStack[] stackList;

    private Container eventHandler;

    public WeakReference<EntityPlayer> player;

    public boolean blockEvents = false;

    public InventoryArmor(EntityPlayer player) {
        this.stackList = new ItemStack[4];
        this.player = new WeakReference<EntityPlayer>(player);
    }

    public Container getEventHandler() {
        return eventHandler;
    }

    public void setEventHandler(Container eventHandler) {
        this.eventHandler = eventHandler;
    }

    @Override
    public int getSizeInventory() {
        return this.stackList.length;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return slot >= this.getSizeInventory() ? null : this.stackList[slot];
    }

    @Override
    public String getInventoryName() {
        return "";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        if (this.stackList[slot] != null) {
            ItemStack itemstack = this.stackList[slot];
            this.stackList[slot] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        if (this.stackList[slot] != null) {
            ItemStack itemstack;

            if (this.stackList[slot].stackSize <= amount) {
                itemstack = this.stackList[slot];

                this.stackList[slot] = null;

                if (eventHandler != null)
                    this.eventHandler.onCraftMatrixChanged(this);

                syncSlotToClients(slot);
                return itemstack;
            } else {
                itemstack = this.stackList[slot].splitStack(amount);

                if (this.stackList[slot].stackSize == 0) {
                    this.stackList[slot] = null;
                }

                if (eventHandler != null)
                    this.eventHandler.onCraftMatrixChanged(this);

                syncSlotToClients(slot);
                return itemstack;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack itemStack) {
        this.stackList[slot] = itemStack;

        if (eventHandler != null)
            this.eventHandler.onCraftMatrixChanged(this);

        syncSlotToClients(slot);
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void markDirty() {
        try {
            player.get().inventory.markDirty();
        } catch (Exception e) {
        }
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack itemStack) {
        return itemStack.getItem() instanceof ItemArmor && ((ItemArmor) itemStack.getItem()).armorType == slot;
    }

    public void writeToNBT(EntityPlayer entityPlayer) {
        NBTTagCompound tags = entityPlayer.getEntityData();
        writeToNBT(tags);
    }

    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        NBTTagList nbtTagList = new NBTTagList();
        NBTTagCompound nbtTagCompound1;
        for (int i = 0; i < this.stackList.length; ++i) {
            if (this.stackList[i] != null) {
                nbtTagCompound1 = new NBTTagCompound();
                nbtTagCompound1.setByte("Slot", (byte) i);
                this.stackList[i].writeToNBT(nbtTagCompound1);
                nbtTagList.appendTag(nbtTagCompound1);
            }
        }
        nbtTagCompound.setTag("Armor.Inventory", nbtTagList);
    }

    public void readFromNBT(EntityPlayer player) {
        NBTTagCompound tags = player.getEntityData();
        readFromNBT(tags);
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        NBTTagList nbtTagList = nbtTagCompound.getTagList("Armor.Inventory", 10);
        for (int i = 0; i < nbtTagList.tagCount(); ++i) {
            NBTTagCompound nbttagcompound = nbtTagList.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot") & 255;
            ItemStack itemstack = ItemStack.loadItemStackFromNBT(nbttagcompound);
            if (itemstack != null) {
                this.stackList[j] = itemstack;
            }
        }
    }

    public void dropItems(ArrayList<EntityItem> drops) {
        for (int i = 0; i < 4; ++i) {
            if (this.stackList[i] != null) {
                EntityItem ei = new EntityItem(player.get().worldObj,
                        player.get().posX, player.get().posY
                        + player.get().eyeHeight, player.get().posZ,
                        this.stackList[i].copy());
                ei.delayBeforeCanPickup = 40;
                float f1 = player.get().worldObj.rand.nextFloat() * 0.5F;
                float f2 = player.get().worldObj.rand.nextFloat()
                        * (float) Math.PI * 2.0F;
                ei.motionX = (double) (-MathHelper.sin(f2) * f1);
                ei.motionZ = (double) (MathHelper.cos(f2) * f1);
                ei.motionY = 0.20000000298023224D;
                drops.add(ei);
                this.stackList[i] = null;
                syncSlotToClients(i);
            }
        }
    }

    public void dropItemsAt(ArrayList<EntityItem> drops, Entity e) {
        for (int i = 0; i < 4; ++i) {
            if (this.stackList[i] != null) {
                EntityItem ei = new EntityItem(e.worldObj,
                        e.posX, e.posY + e.getEyeHeight(), e.posZ,
                        this.stackList[i].copy());
                ei.delayBeforeCanPickup = 40;
                float f1 = e.worldObj.rand.nextFloat() * 0.5F;
                float f2 = e.worldObj.rand.nextFloat() * (float) Math.PI * 2.0F;
                ei.motionX = (double) (-MathHelper.sin(f2) * f1);
                ei.motionZ = (double) (MathHelper.cos(f2) * f1);
                ei.motionY = 0.20000000298023224D;
                drops.add(ei);
                this.stackList[i] = null;
                syncSlotToClients(i);
            }
        }
    }

    public void syncSlotToClients(int slot) {
        try {
            PacketSyncArmor packetSyncArmor = new PacketSyncArmor();
            packetSyncArmor.entityId = player.get().getEntityId();
            packetSyncArmor.slot = slot;
            packetSyncArmor.itemStack = getStackInSlot(slot);
            PacketHandler.INSTANCE.sendToAll(packetSyncArmor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
