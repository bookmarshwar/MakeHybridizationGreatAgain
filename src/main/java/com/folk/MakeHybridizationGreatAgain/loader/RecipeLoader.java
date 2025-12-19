package com.folk.MakeHybridizationGreatAgain.loader;

import com.folk.MakeHybridizationGreatAgain.enums.CrossingMode;
import com.folk.MakeHybridizationGreatAgain.enums.ItemList;

import com.gtnewhorizons.modularui.common.widget.ProgressBar;
import gregtech.api.enums.GTValues;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;

import gregtech.api.gui.modularui.GTUITextures;
import gregtech.api.interfaces.IRecipeMap;
import gregtech.api.recipe.RecipeMap;
import gregtech.api.recipe.RecipeMapBackend;
import gregtech.api.recipe.RecipeMapBuilder;
import gregtech.api.recipe.RecipeMapFrontend;
import gregtech.api.recipe.RecipeMaps;
import gregtech.api.recipe.RecipeMetadataKey;
import gregtech.api.util.GTOreDictUnificator;
import gregtech.api.util.GTRecipe;
import gregtech.nei.RecipeDisplayInfo;
import gregtech.nei.formatter.INEISpecialInfoFormatter;
import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.core.crop.IC2Crops;
import ic2.core.crop.TileEntityCrop;
import ic2.core.item.ItemCropSeed;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class RecipeLoader {
    public static class MyCustomNEIFormatter implements INEISpecialInfoFormatter {
        @Override
        public List<String> format(RecipeDisplayInfo recipeDisplayInfo) {
            List<String> specialInfo = new ArrayList<>();

            GTRecipe recipe = recipeDisplayInfo.recipe;

            CrossingMode mode = recipe.getMetadata(CROSSING_MODE);

            if (mode != null) {

                specialInfo.add("模式: " + mode.getDisplayName());
            }
            return specialInfo;
        }
    }
    public static final RecipeMetadataKey<CrossingMode> CROSSING_MODE = new RecipeMetadataKey<CrossingMode>(CrossingMode.class, "crossing_mode") {
        @Override
        public void drawInfo(RecipeDisplayInfo recipeDisplayInfo, @Nullable Object o) {

        }

    };
    public static final RecipeMap<RecipeMapBackend> AutomatedBreedingFacility;
    static {
        AutomatedBreedingFacility = RecipeMapBuilder.of("AutomatedBreedingFacility").maxIO(1, 1, 0, 0) // 最大 1 个物品输入，1 个物品输出，无流体
            .progressBar(gregtech.api.gui.modularui.GTUITextures.PROGRESSBAR_ARROW) // 进度条样式
            .neiTransferRect(80, 24, 24, 17) // NEI 点击区域（可选）
            .neiSpecialInfoFormatter(new MyCustomNEIFormatter())
            .build();
    }
    public static void addRecipes() {

        final IRecipeMap assembler = RecipeMaps.assemblerRecipes;


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

        GTValues.RA.stdBuilder().itemInputs(ItemCropSeed.generateItemStackFromValues(IC2Crops.weed, (byte)1, (byte)1, (byte)1, (byte)4))
            .itemOutputs(ItemCropSeed.generateItemStackFromValues(IC2Crops.weed, (byte)1, (byte)1, (byte)2, (byte)4))
            .eut(1024L)
            .duration(100)
            .metadata(CROSSING_MODE,CrossingMode.Re)
            .addTo(AutomatedBreedingFacility);
        GTValues.RA.stdBuilder().itemInputs(ItemCropSeed.generateItemStackFromValues(IC2Crops.weed, (byte)1, (byte)1, (byte)1, (byte)4))
            .itemOutputs(ItemCropSeed.generateItemStackFromValues(IC2Crops.weed, (byte)1, (byte)2, (byte)1, (byte)4))
            .eut(1024L)
            .duration(100)
            .metadata(CROSSING_MODE,CrossingMode.Ga)
            .addTo(AutomatedBreedingFacility);
        GTValues.RA.stdBuilder().itemInputs(ItemCropSeed.generateItemStackFromValues(IC2Crops.weed, (byte)1, (byte)1, (byte)1, (byte)4))
            .itemOutputs(ItemCropSeed.generateItemStackFromValues(IC2Crops.weed, (byte)2, (byte)1, (byte)1, (byte)4))
            .eut(1024L)
            .duration(100)
            .metadata(CROSSING_MODE,CrossingMode.Gr)
            .addTo(AutomatedBreedingFacility);

    }
}
