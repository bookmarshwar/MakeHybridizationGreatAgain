package com.folk.MakeHybridizationGreatAgain.handler;

import com.folk.MakeHybridizationGreatAgain.machine.BaseMachine;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;

public class RenderHandler {
    public static final RenderHandler INSTANCE = new RenderHandler();
    private final RenderBlocks renderBlocks = RenderBlocks.getInstance();
    private RenderHandler() {}
    public static void register() {
        MinecraftForge.EVENT_BUS.register(INSTANCE);
    }
    @SubscribeEvent
    public void onRenderWorldLast(RenderWorldLastEvent event) {
        for (TileEntity te : event.context.tileEntities) {
            if (isTargetMyMachine(te)) {
                renderRun(te,event);
            }
        }
    }

    private void renderRun(TileEntity te, RenderWorldLastEvent event) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;


        double renderX = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks;
        double renderY = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks;
        double renderZ = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks;


        double baseX = te.xCoord;
        double baseY = te.yCoord;
        double baseZ = te.zCoord;
        double centerX = te.xCoord + 0.5;
        double centerY = te.yCoord + 1.5;
        double centerZ = te.zCoord + 0.5;

        double radius = 2.0;

        long time = System.currentTimeMillis();
        float angle = (time % 72000L) / 50.0F;
        double radians = Math.toRadians(angle);
        double worldX = centerX + radius * Math.cos(radians);
        double worldZ = centerZ + radius * Math.sin(radians);
        double worldY = centerY;

        Block blockToRender = Blocks.diamond_block;


        mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);


        renderBlockAt(worldX, worldY, worldZ, blockToRender, renderX, renderY, renderZ);
    }
    private void renderBlockAt(double worldX, double worldY, double worldZ, Block blockToRender, double renderX, double renderY, double renderZ) {

        double radius = 2.0;

        long time = System.currentTimeMillis();

        float angle = (time % 72000L) / 50.0F;
        double radians = Math.toRadians(angle);
        GL11.glPushMatrix();

        GL11.glTranslated(worldX - renderX, worldY - renderY, worldZ - renderZ);
        GL11.glRotatef(angle * 2, 0.0F, 1.0F, 0.0F);


        GL11.glScalef(0.5F, 0.5F, 0.5F);

        this.renderBlocks.renderBlockAsItem(blockToRender, 0, 1.0F);
        GL11.glPopMatrix();
    }

    private boolean isTargetMyMachine(TileEntity te) {
        if (!(te instanceof IGregTechTileEntity)) return false;
        IMetaTileEntity mte = ((IGregTechTileEntity) te).getMetaTileEntity();

        if (mte instanceof BaseMachine) {

            return true;
        }
        return false;
    }
}
