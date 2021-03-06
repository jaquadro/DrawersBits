package mods.belgabor.bitdrawers.storage;

import com.jaquadro.minecraft.storagedrawers.api.storage.IDrawer;
import com.jaquadro.minecraft.storagedrawers.api.storage.IFractionalDrawer;
import com.jaquadro.minecraft.storagedrawers.api.storage.attribute.IItemLockable;
import com.jaquadro.minecraft.storagedrawers.api.storage.attribute.IShroudable;
import com.jaquadro.minecraft.storagedrawers.api.storage.attribute.IVoidable;
import com.jaquadro.minecraft.storagedrawers.api.storage.attribute.LockAttribute;
import com.jaquadro.minecraft.storagedrawers.storage.BaseDrawerData;
import com.jaquadro.minecraft.storagedrawers.storage.ICentralInventory;
import mods.belgabor.bitdrawers.BitDrawers;
import mods.belgabor.bitdrawers.core.BDLogger;
import mods.belgabor.bitdrawers.core.BitHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Belgabor on 02.06.2016.
 * Based on CompDrawerData by jaquadro
 */
public class BitDrawerData extends BaseDrawerData implements IFractionalDrawer, IVoidable, IShroudable, IItemLockable
{
    private static final ItemStack nullStack = new ItemStack((Item)null);

    private ICentralInventory central;
    private int slot;

    public BitDrawerData (ICentralInventory centralInventory, int slot) {
        this.slot = slot;
        this.central = centralInventory;
    }

    @Override
    public ItemStack getStoredItemPrototype () {
        return central.getStoredItemPrototype(slot);
    }

    @Override
    public void setStoredItem (ItemStack itemPrototype, int amount) {
        if (BitDrawers.config.debugTrace)
            BDLogger.info("setStoredItem %d %s %d", slot, itemPrototype==null?"null":itemPrototype.getDisplayName(), amount);
        central.setStoredItem(slot, itemPrototype, amount);
        refresh();

        // markDirty
    }
    
    @Override
    public IDrawer setStoredItemRedir (ItemStack itemPrototype, int amount) {
        IDrawer target = central.setStoredItem(slot, itemPrototype, amount);
        refresh();

        return target;
    }


    @Override
    public boolean areItemsEqual(ItemStack item) {
        ItemStack protoStack = this.getStoredItemPrototype();
        return BitHelper.areItemsEqual(item, protoStack);
    }

    @Override
    public int getStoredItemCount () {
        return central.getStoredItemCount(slot);
    }

    @Override
    public void setStoredItemCount (int amount) {
        central.setStoredItemCount(slot, amount);
    }

    @Override
    public int getMaxCapacity () {
        return central.getMaxCapacity(slot);
    }

    @Override
    public int getMaxCapacity (ItemStack itemPrototype) {
        return central.getMaxCapacity(slot, itemPrototype);
    }

    @Override
    public int getRemainingCapacity () {
        return central.getRemainingCapacity(slot);
    }

    @Override
    public int getStoredItemStackSize () {
        return central.getStoredItemStackSize(slot);
    }

    @Override
    protected int getItemCapacityForInventoryStack () {
        return central.getItemCapacityForInventoryStack(slot);
    }

    @Override
    public boolean canItemBeStored (ItemStack itemPrototype) {
        if (getStoredItemPrototype() == null && !isItemLocked(LockAttribute.LOCK_EMPTY)) {
            return BitHelper.getBit(itemPrototype) != null || BitHelper.getBlock(itemPrototype) != null;
        }

        return areItemsEqual(itemPrototype);
    }

    @Override
    public boolean canItemBeExtracted (ItemStack itemPrototype) {
        return areItemsEqual(itemPrototype);
    }

    @Override
    public boolean isEmpty () {
        return getStoredItemPrototype() == null;
    }

    @Override
    public void writeToNBT (NBTTagCompound tag) {
        central.writeToNBT(slot, tag);
    }

    @Override
    public void readFromNBT (NBTTagCompound tag) {
        central.readFromNBT(slot, tag);
        refresh();
    }

    @Override
    public int getConversionRate () {
        return central.getConversionRate(slot);
    }

    @Override
    public int getStoredItemRemainder () {
        return central.getStoredItemRemainder(slot);
    }

    @Override
    public boolean isSmallestUnit () {
        return central.isSmallestUnit(slot);
    }

    public void refresh () {
        reset();
        refreshOreDictMatches();
    }

    @Override
    public boolean isVoid () {
        return central.isVoidSlot(slot);
    }

    @Override
    public boolean isShrouded () {
        return central.isShroudedSlot(slot);
    }

    @Override
    public boolean setIsShrouded (boolean state) {
        return central.setIsSlotShrouded(slot, state);
    }

    @Override
    public boolean isItemLocked (LockAttribute attr) {
        return central.isLocked(slot, attr);
    }

    @Override
    public boolean canItemLock (LockAttribute attr) {
        return false;
    }

    @Override
    public void setItemLocked (LockAttribute attr, boolean isLocked) { }
}
