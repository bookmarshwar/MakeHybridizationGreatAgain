package com.folk.MakeHybridizationGreatAgain.machine;

import com.folk.MakeHybridizationGreatAgain.api.IRender;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;

import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;

public abstract class BaseMachine<T extends BaseMachine<T>> extends MTEExtendedPowerMultiBlockBase<T>
    implements IConstructable, ISurvivalConstructable, IRender {

    public BaseMachine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public BaseMachine(String aName) {
        super(aName);
    }
    //添加render渲染
    @Override
    public void onRenderWorldLast(TileEntity te, RenderWorldLastEvent event) {
        return;
    }

    @Override
    public void onRenderBlockPre(TileEntity te, RenderWorldEvent event) {
        return;
    }

    // 机器维修
    public void repairMachine() {
        mHardHammer = true;
        mSoftMallet = true;
        mScrewdriver = true;
        mCrowbar = true;
        mSolderingTool = true;
        mWrench = true;
    }
    //在指定位置渲染指定视角的方块指定旋转角度
    BaseMachine<T> renderBlockAt(double worldX, double worldY, double worldZ, Block blockToRender, double renderX, double renderY, double renderZ, float angle) {
        GL11.glTranslated(worldX - renderX, worldY - renderY, worldZ - renderZ);
        GL11.glRotatef(angle * 2, 0.0F, 1.0F, 0.0F);
        RenderBlocks.getInstance().renderBlockAsItem(blockToRender, 0, 1.0F);
        return this;
    }
    BaseMachine<T> renderRun(){
        GL11.glPushMatrix();
        return this;
    }
    BaseMachine<T> renderEnd(){
        GL11.glPopMatrix();
        return this;
    }

}
