package com.folk.MakeHybridizationGreatAgain.machine;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import org.lwjgl.opengl.GL11;

import com.folk.MakeHybridizationGreatAgain.api.IRender;
import com.gtnewhorizon.structurelib.alignment.constructable.IConstructable;
import com.gtnewhorizon.structurelib.alignment.constructable.ISurvivalConstructable;

import gregtech.api.metatileentity.implementations.MTEExtendedPowerMultiBlockBase;

public abstract class BaseMachine<T extends BaseMachine<T>> extends MTEExtendedPowerMultiBlockBase<T>
    implements IConstructable, ISurvivalConstructable, IRender {

    public BaseMachine(int aID, String aName, String aNameRegional) {
        super(aID, aName, aNameRegional);
    }

    public BaseMachine(String aName) {
        super(aName);
    }

    // 添加render渲染
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

    // 在指定位置渲染指定视角的方块指定旋转角度
    BaseMachine<T> renderBlockAt(double worldX, double worldY, double worldZ, Block blockToRender, double renderX,
        double renderY, double renderZ, float angle) {
        GL11.glPushMatrix();
        GL11.glTranslated(worldX - renderX, worldY - renderY, worldZ - renderZ);
        GL11.glRotatef(angle * 2, 0.0F, 1.0F, 0.0F);
        RenderBlocks.getInstance()
            .renderBlockAsItem(blockToRender, 0, 1.0F);
        GL11.glPopMatrix();
        return this;
    }

    BaseMachine<T> renderItemAt(double worldX, double worldY, double worldZ, ItemStack itemToRender, double renderX,
        double renderY, double renderZ, float angle, float scale) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslated(worldX - renderX, worldY - renderY, worldZ - renderZ);
        GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F); // 水平旋转
        // 平移到物品中心再渲染
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(-0.5F, -0.5F, 0.0F); // 把物品中心移到原点
        IIcon icon = itemToRender.getIconIndex();
        if (itemToRender.getItemSpriteNumber() == 0) {
            Minecraft.getMinecraft()
                .getTextureManager()
                .bindTexture(TextureMap.locationBlocksTexture);
        } else {
            Minecraft.getMinecraft()
                .getTextureManager()
                .bindTexture(TextureMap.locationItemsTexture);
        }

        Tessellator tessellator = Tessellator.instance;
        float f = icon.getMinU();
        float f1 = icon.getMaxU();
        float f2 = icon.getMinV();
        float f3 = icon.getMaxV();

        ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, icon.getIconWidth(), icon.getIconHeight(), 0.0625F);

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
        return this;
    }

}
