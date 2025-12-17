package com.folk.MakeHybridizationGreatAgain.materials;


import bartworks.system.material.Werkstoff;

import gregtech.api.enums.TextureSet;


import static bartworks.util.BWUtil.subscriptNumbers;

public class WerkstoffLoader  {

    public static Werkstoff HybridSeedMaterial;
    public static  Werkstoff TestingMaterial=new Werkstoff(
        new short[] { 216, 240, 240 },
        "Testing Material",
        subscriptNumbers("Tt"),
        new Werkstoff.Stats().setBlastFurnace(true)
            .setProtons(0)
            .setMass(0)
            .setMeltingPoint(1200)
            .setSpeedOverride(1000.0F)
            .setDurOverride(20000000)
            .setQualityOverride((byte) 120),
        Werkstoff.Types.ELEMENT,
        new Werkstoff.GenerationFeatures().onlyDust()
            .addMolten()
            .addMetalItems()
            .addCraftingMetalWorkingItems()
            .addSimpleMetalWorkingItems()
            .addMetalCraftingSolidifierRecipes()
            .addMetaSolidifierRecipes(),
        32702,
        TextureSet.SET_SHINY);
    public static void init() {
        HybridSeedMaterial = new Werkstoff(
            new short[]{96, 96, 240},
            "Holmium Garnet fuck",
            subscriptNumbers("Ho3Al5012"),
        new Werkstoff.Stats(),
            Werkstoff.Types.MATERIAL,
            new Werkstoff.GenerationFeatures().disable().onlyDust()
                .addMolten(),
            32700+ 1,
            TextureSet.SET_SHINY);


    }
}
