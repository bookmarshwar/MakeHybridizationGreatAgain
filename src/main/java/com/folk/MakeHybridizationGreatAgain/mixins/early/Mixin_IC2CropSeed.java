package com.folk.MakeHybridizationGreatAgain.mixins.early;

import static com.folk.MakeHybridizationGreatAgain.util.Utils.calculateOverclock;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ic2.core.item.ItemCropSeed.class)
public class Mixin_IC2CropSeed {

    // 超频属性,基准产出n,超频产出为n^(1.1)^(k-1),k为超频次数:overcLock属性
    @Inject(method = "addInformation", at = @At("RETURN"))
    private void addOverclockInfo(ItemStack stack, EntityPlayer player, List info, boolean debugTooltips,
        CallbackInfo ci) {
        // 只有当扫描等级达到4级时才显示overclock信息
        int overclock = getOverclockFromStack(stack);

        // 使用与原版相似的颜色格式
        info.add("§cOv§7 " + overclock);
        // 添加一行说明，让玩家知道这是什么
        if (overclock <= 0) {
            overclock = 1;
        }
        info.add("§7超频效果: 产出率" + calculateOverclock(overclock) * 100 + "%");

    }

    private static int getOverclockFromStack(ItemStack is) {

        return is.getTagCompound() == null ? -1
            : is.getTagCompound()
                .getInteger("overclock");
    }

    @Inject(method = "getSubItems", at = @At("TAIL"))
    private void addOverclockToCreativeItems(Item item, CreativeTabs tabs, List items, CallbackInfo ci) {
        // 遍历所有即将被添加到创造模式物品栏的种子
        for (Object obj : items) {
            if (obj instanceof ItemStack) {
                ItemStack stack = (ItemStack) obj;

                // 确保物品有NBT标签
                if (!stack.hasTagCompound()) {
                    stack.setTagCompound(new NBTTagCompound());
                }

                // 给它一个随机的超频等级 (1到3)，这样你在创造模式就能直接看到效果
                // 如果你想要固定的超频等级，可以把下面这行改成: stack.getTagCompound().setInteger("overclock", 1);

                stack.getTagCompound()
                    .setInteger("overclock", 100);
            }
        }
    }
}
