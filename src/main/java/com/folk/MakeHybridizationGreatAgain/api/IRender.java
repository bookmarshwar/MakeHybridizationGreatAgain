package com.folk.MakeHybridizationGreatAgain.api;

import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(value = Side.CLIENT)
public interface IRender {

    public void onRenderWorldLast(TileEntity te, RenderWorldLastEvent event);

    public void onRenderBlockPre(TileEntity te, RenderWorldEvent event);
}
