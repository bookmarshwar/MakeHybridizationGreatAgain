package com.folk.MakeHybridizationGreatAgain.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import com.folk.MakeHybridizationGreatAgain.util.Utils;

public enum ItemList {

    IntensifyChemicalDistorter,
    CropEngineer,
    MegaIsaForge;

    private boolean mHasNotBeenSet;
    private boolean mDeprecated;
    private boolean mWarned;
    private ItemStack mStack;

    ItemList() {
        mHasNotBeenSet = true;
    }

    ItemList(boolean aDeprecated) {
        if (aDeprecated) {
            mDeprecated = true;
            mHasNotBeenSet = true;
        }
    }

    public int getMeta() {
        return mStack.getItemDamage();
    }

    public ItemList set(Item aItem) {
        mHasNotBeenSet = false;
        if (aItem == null) return this;
        ItemStack aStack = new ItemStack(aItem, 1, 0);
        mStack = Utils.copyAmount(1, aStack);
        return this;
    }

    public ItemList set(ItemStack aStack) {
        if (aStack != null) {
            mHasNotBeenSet = false;
            mStack = Utils.copyAmount(1, aStack);
        }
        return this;
    }

    public boolean hasBeenSet() {
        return !mHasNotBeenSet;
    }

    public ItemStack getInternalStack_unsafe() {
        return mStack;
    }

}
