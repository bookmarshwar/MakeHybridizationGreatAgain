package com.folk.MakeHybridizationGreatAgain.recipe;

import com.folk.MakeHybridizationGreatAgain.items.ItemList;

import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;

import gregtech.api.interfaces.IRecipeMap;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.util.GTModHandler;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTUtility;


public class RecipeLoader {

    public static void addRecipes() {
        final IRecipeMap assembler = RecipeMaps.assemblerRecipes;
        // 1. 添加极限工业温室控制器的工作台合成配方

        GTValues.RA.stdBuilder()
            .itemInputs(
                GTOreDictUnificator.get(OrePrefixes.circuit.get(Materials.HV), 64),
                gregtech.api.enums.ItemList.Robot_Arm_HV.get(64),
                gregtech.api.enums.ItemList.Electric_Pump_HV.get(64),
                gregtech.api.enums.ItemList.Conveyor_Module_HV.get(64),
                gregtech.api.enums.ItemList.Emitter_HV.get(64),
                gregtech.api.enums.ItemList.Sensor_HV.get(64)



            )
            .itemOutputs(ItemList.AutomatedBreedingFacility.getInternalStack_unsafe())
            .eut(512L)
            .duration(120)
            .addTo(assembler)
        ;

    }
}
