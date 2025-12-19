package com.folk.MakeHybridizationGreatAgain.loader;

import net.minecraft.item.Item;

import com.folk.MakeHybridizationGreatAgain.items.Cropcore;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

public class ItemsLoader {

    public static Item CROPCORE = new Cropcore();

    public ItemsLoader(FMLPreInitializationEvent event) {
        IRegistry(CROPCORE);
    }

    private static void IRegistry(Item item) {
        GameRegistry.registerItem(item, "111");
    }
}
