package com.folk.MakeHybridizationGreatAgain.loader;

import net.minecraft.creativetab.CreativeTabs;

import com.folk.MakeHybridizationGreatAgain.items.creativetap.Tap;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class TapLoader {

    public static CreativeTabs tap;

    public TapLoader(FMLPreInitializationEvent event) {
        tap = new Tap();
    }

}
