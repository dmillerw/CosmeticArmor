package dmillerw.armor.core.inventory;

import dmillerw.armor.core.handler.PlayerHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ContainerCosmeticArmor extends Container {

    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
    public IInventory craftResult = new InventoryCraftResult();
    public InventoryArmor inventoryArmor;

    public boolean localWorld;

    private final EntityPlayer thePlayer;

    public ContainerCosmeticArmor(EntityPlayer player, boolean localWorld) {
        this.thePlayer = player;
        this.localWorld = localWorld;

        inventoryArmor = new InventoryArmor(player);
        inventoryArmor.setEventHandler(this);

        if (!player.worldObj.isRemote) {
            inventoryArmor.stackList = PlayerHandler.getArmor(player).stackList;
        }

        this.addSlotToContainer(new SlotCrafting(player, this.craftMatrix, this.craftResult, 0, 144, 36));

        int i;
        int j;
        for (i = 0; i < 2; ++i) {
            for (j = 0; j < 2; ++j) {
                this.addSlotToContainer(new Slot(this.craftMatrix, j + i * 2, 106 + j * 18, 26 + i * 18));
            }
        }

        for (i = 0; i < 4; ++i) {
            final int k = i;
            this.addSlotToContainer(new Slot(player.inventory, player.inventory.getSizeInventory() - 1 - i, 8, 8 + i * 18) {
                @Override
                public int getSlotStackLimit() {
                    return 1;
                }

                @Override
                public boolean isItemValid(ItemStack par1ItemStack) {
                    if (par1ItemStack == null) return false;
                    return par1ItemStack.getItem().isValidArmor(par1ItemStack, k, thePlayer);
                }
            });
        }

        this.addSlotToContainer(new Slot(inventoryArmor, 0, 80, 8 + 0 * 18));
        this.addSlotToContainer(new Slot(inventoryArmor, 1, 80, 8 + 1 * 18));
        this.addSlotToContainer(new Slot(inventoryArmor, 2, 80, 8 + 2 * 18));
        this.addSlotToContainer(new Slot(inventoryArmor, 3, 80, 8 + 3 * 18));

        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(player.inventory, j + (i + 1) * 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(player.inventory, i, 8 + i * 18, 142));
        }

        this.onCraftMatrixChanged(this.craftMatrix);
    }

    /**
     * Callback for when the crafting matrix is changed.
     */
    @Override
    public void onCraftMatrixChanged(IInventory par1IInventory) {
        this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.thePlayer.worldObj));
    }

    /**
     * Called when the container is closed.
     */
    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        for (int i = 0; i < 4; ++i) {
            ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);

            if (itemstack != null) {
                player.dropPlayerItemWithRandomChoice(itemstack, false);
            }
        }

        this.craftResult.setInventorySlotContents(0, (ItemStack) null);

        if (!player.worldObj.isRemote) {
            PlayerHandler.setArmor(player, inventoryArmor);
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return true;
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 == 0) {
                if (!this.mergeItemStack(itemstack1, 9 + 4, 45 + 4, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (par2 >= 1 && par2 < 5) {
                if (!this.mergeItemStack(itemstack1, 9 + 4, 45 + 4, false)) {
                    return null;
                }
            } else if (par2 >= 5 && par2 < 9) {
                if (!this.mergeItemStack(itemstack1, 9 + 4, 45 + 4, false)) {
                    return null;
                }
            } else if (itemstack.getItem() instanceof ItemArmor &&
                    !((Slot) this.inventorySlots.get(5 + ((ItemArmor) itemstack.getItem()).armorType)).getHasStack()) {
                int j = 5 + ((ItemArmor) itemstack.getItem()).armorType;

                if (!this.mergeItemStack(itemstack1, j, j + 1, false)) {
                    return null;
                }
            } else if (par2 >= 9 + 4 && par2 < 36 + 4) {
                if (!this.mergeItemStack(itemstack1, 36 + 4, 45 + 4, false)) {
                    return null;
                }
            } else if (par2 >= 36 + 4 && par2 < 45 + 4) {
                if (!this.mergeItemStack(itemstack1, 9 + 4, 36 + 4, false)) {
                    return null;
                }
            } else if (!this.mergeItemStack(itemstack1, 9 + 4, 45 + 4, false, slot)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack((ItemStack) null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }

    private void unequipBauble(ItemStack stack) {
//    	if (stack.getItem() instanceof IBauble) {
//    		((IBauble)stack.getItem()).onUnequipped(stack, thePlayer);
//    	}
    }


    @Override
    public void putStacksInSlots(ItemStack[] p_75131_1_) {
        inventoryArmor.blockEvents = true;
        super.putStacksInSlots(p_75131_1_);
    }


    protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4, Slot ss) {
        boolean flag1 = false;
        int k = par2;

        if (par4) {
            k = par3 - 1;
        }

        Slot slot;
        ItemStack itemstack1;

        if (par1ItemStack.isStackable()) {
            while (par1ItemStack.stackSize > 0 && (!par4 && k < par3 || par4 && k >= par2)) {
                slot = (Slot) this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 != null && itemstack1.getItem() == par1ItemStack.getItem() && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == itemstack1.getItemDamage()) && ItemStack.areItemStackTagsEqual(par1ItemStack, itemstack1)) {
                    int l = itemstack1.stackSize + par1ItemStack.stackSize;
                    if (l <= par1ItemStack.getMaxStackSize()) {
                        par1ItemStack.stackSize = 0;
                        itemstack1.stackSize = l;
                        slot.onSlotChanged();
                        flag1 = true;
                    } else if (itemstack1.stackSize < par1ItemStack.getMaxStackSize()) {
                        par1ItemStack.stackSize -= par1ItemStack.getMaxStackSize() - itemstack1.stackSize;
                        itemstack1.stackSize = par1ItemStack.getMaxStackSize();
                        slot.onSlotChanged();
                        flag1 = true;
                    }
                }

                if (par4) {
                    --k;
                } else {
                    ++k;
                }
            }
        }

        if (par1ItemStack.stackSize > 0) {
            if (par4) {
                k = par3 - 1;
            } else {
                k = par2;
            }

            while (!par4 && k < par3 || par4 && k >= par2) {
                slot = (Slot) this.inventorySlots.get(k);
                itemstack1 = slot.getStack();

                if (itemstack1 == null) {
                    slot.putStack(par1ItemStack.copy());
                    slot.onSlotChanged();
                    par1ItemStack.stackSize = 0;
                    flag1 = true;
                    break;
                }

                if (par4) {
                    --k;
                } else {
                    ++k;
                }
            }
        }
        return flag1;
    }

    @Override
    public boolean func_94530_a(ItemStack par1ItemStack, Slot par2Slot) {
        return par2Slot.inventory != this.craftResult && super.func_94530_a(par1ItemStack, par2Slot);
    }
}
