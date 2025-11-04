package com.folk.MakeHybridizationGreatAgain.recipe;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

import gregtech.api.util.GTRecipe;

/**
 * 基础配方类，继承自 GregTech 核心配方类
 * Base recipe class, inherited from GregTech's core recipe class
 */
public class BaseRecipe extends GTRecipe {

    /**
     * 构造基础配方实例
     * Construct a base recipe instance
     *
     * @param aOptimize     是否优化配方（如按倍数缩减输入/输出和时间，提升计算效率）
     *                      Whether to optimize the recipe (e.g., reduce inputs/outputs and duration by multiples to
     *                      improve calculation efficiency)
     * @param aInputs       物品输入数组（配方所需的原料物品栈）
     *                      Item input array (raw material item stacks required for the recipe)
     * @param aOutputs      物品输出数组（配方产出的物品栈）
     *                      Item output array (item stacks produced by the recipe)
     * @param aSpecialItems 特殊槽位物品（如打印机的模板，仅用于 NEI 显示，不参与实际匹配）
     *                      Special slot items (e.g., printer templates, used only for NEI display, not actual matching)
     * @param aChances      输出物品的概率数组（范围 1-10000，10000 表示 100%；null 时默认全为 100%）
     *                      Probability array for output items (range 1-10000, 10000 = 100%; null defaults to 100% for
     *                      all)
     * @param aFluidInputs  流体输入数组（配方所需的液体原料，如 molten iron 铁水）
     *                      Fluid input array (liquid raw materials required for the recipe, e.g., molten iron)
     * @param aFluidOutputs 流体输出数组（配方产出的液体，如 steam 蒸汽）
     *                      Fluid output array (liquids produced by the recipe, e.g., steam)
     * @param aDuration     配方加工时间（单位：游戏刻，20 刻 = 1 秒）
     *                      Recipe processing duration (in ticks, 20 ticks = 1 second)
     * @param aEUt          每刻能量消耗（单位：EU，GregTech 能量单位；负值表示产能）
     *                      Energy consumed per tick (in EU, GregTech's energy unit; negative value means energy
     *                      production)
     * @param aSpecialValue 特殊值（根据机器类型有不同用途，如燃料热值、电压等级等）
     *                      Special value (varies by machine type, e.g., fuel calorific value, voltage level)
     */
    public BaseRecipe(boolean aOptimize, ItemStack[] aInputs, ItemStack[] aOutputs, Object aSpecialItems,
        int[] aChances, FluidStack[] aFluidInputs, FluidStack[] aFluidOutputs, int aDuration, int aEUt,
        int aSpecialValue) {
        super(
            aOptimize,
            aInputs,
            aOutputs,
            aSpecialItems,
            aChances,
            aFluidInputs,
            aFluidOutputs,
            aDuration,
            aEUt,
            aSpecialValue);
    }
}
