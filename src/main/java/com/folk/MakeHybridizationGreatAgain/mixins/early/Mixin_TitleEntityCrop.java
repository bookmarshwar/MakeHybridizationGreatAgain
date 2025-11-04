package com.folk.MakeHybridizationGreatAgain.mixins.early;

import com.folk.MakeHybridizationGreatAgain.MakeHybridizationGreatAgain;
import com.llamalad7.mixinextras.sugar.Local;
import ic2.api.crops.BaseSeed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.ArrayList;
import java.util.List;

import static com.folk.MakeHybridizationGreatAgain.util.Utils.calculateOverclock;
import static com.folk.MakeHybridizationGreatAgain.util.Utils.getOverclockFromStack;

@Mixin(ic2.core.crop.TileEntityCrop.class)
public class Mixin_TitleEntityCrop {
    @Unique
    private int overclock = 0;
    @Inject(method = "readFromNBT", at = @At("RETURN"))
    private void injectReadFromNBT(NBTTagCompound nbt, CallbackInfo ci) {

        this.overclock = nbt.getInteger("overclock");
        MakeHybridizationGreatAgain.LOG.info("超频"+(long)(Math.pow(1.1, this.overclock - 1) )+"s");
    }
    @Inject(method = "writeToNBT", at = @At("RETURN"))
    private void injectWriteToNBT(NBTTagCompound nbt, CallbackInfo ci) {
        // 将当前的超频等级写入NBT
        nbt.setInteger("overclock", this.overclock);
    }
    @Inject(
        method = "applyBaseSeed",
        at = @At("RETURN"),
        remap = false
    )
    private void injectApplyBaseSeed(EntityPlayer player, CallbackInfoReturnable<Boolean> cir) {

        if (!cir.getReturnValue()) {
            ItemStack current = player.getCurrentEquippedItem();
            this.overclock=getOverclockFromStack(current);
            MakeHybridizationGreatAgain.LOG.info("超频"+this.overclock+"s");

        }
    }
    @Inject(method = "harvest_automated", at = @At("RETURN"), cancellable = true,remap = false)
    private void applyOverclockToHarvestDrops(boolean manual, CallbackInfoReturnable<ItemStack[]> cir) {
        ItemStack[] originalDrops = cir.getReturnValue();


        List<ItemStack> newDrops = new ArrayList<>();

        if (originalDrops != null) {
            for (ItemStack drop : originalDrops) {
                if (drop != null) {
                    MakeHybridizationGreatAgain.LOG.info("超频 产出"+calculateOverclock(overclock)+"倍率");
                    for (long i=0;i<calculateOverclock(overclock);i++)
                    newDrops.add(drop);
                }
            }
            ItemStack[] finalDrops = newDrops.toArray(new ItemStack[0]);

            cir.setReturnValue(finalDrops);
        }



    }

}
