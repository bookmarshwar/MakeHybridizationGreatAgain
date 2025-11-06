package com.folk.MakeHybridizationGreatAgain.loder;

import com.folk.MakeHybridizationGreatAgain.machine.AutomatedBreedingFacility;
import net.minecraft.item.ItemStack;

import com.folk.MakeHybridizationGreatAgain.items.ItemList;
import com.folk.MakeHybridizationGreatAgain.machine.CropEngineer;

public class MachineLoader {

    private static ItemStack CropEngineer;
    private static int ID = 31000;
    private static ItemStack AutomatedBreedingFacility;

    public static int newID(int Id) {
        ID = Id + 1;
        return ID;
    }

    public static void loadMachines() {
        CropEngineer = new CropEngineer(newID(ID), "CropEngineer", "育种站").getStackForm(1);
        AutomatedBreedingFacility = new AutomatedBreedingFacility(newID(ID),"AutomatedBreedingFacility","自动化育种设施").getStackForm(1);
        ItemList.AutomatedBreedingFacility.set(AutomatedBreedingFacility);
        ItemList.CropEngineer.set(CropEngineer);

    }
}
