package com.folk.MakeHybridizationGreatAgain.items.creativetap;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class Tap extends CreativeTabs {

    public Tap() {
        super("codrsdqw");
    }

    public Tap(String lable) {
        super(lable);
    }

    public Tap(int index, String label) {
        super(index, label);
    }

    @Override
    public Item getTabIconItem() {
        return Items.book;
    }
}
