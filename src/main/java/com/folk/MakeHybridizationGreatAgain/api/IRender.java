package com.folk.MakeHybridizationGreatAgain.api;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.event.RenderWorldEvent;
import net.minecraftforge.client.event.RenderWorldLastEvent;
@SideOnly(value = Side.CLIENT )
public interface IRender {

    public void onRenderWorldLast(TileEntity te, RenderWorldLastEvent event);
    public void onRenderBlockPre(TileEntity te, RenderWorldEvent event);
}
