package com.folk.MakeHybridizationGreatAgain.items.creativetap;

import net.minecraft.creativetab.CreativeTabs;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class TapLoader {

    public static CreativeTabs tap;

    public TapLoader(FMLPreInitializationEvent event) {
        tap = new Tap();
    }

}
