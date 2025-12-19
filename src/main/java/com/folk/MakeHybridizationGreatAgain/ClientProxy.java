package com.folk.MakeHybridizationGreatAgain;

import com.folk.MakeHybridizationGreatAgain.handler.RenderHandler;

import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RenderHandler.register();
    }

    // Override CommonProxy methods here, if you want a different behaviour on the client (e.g. registering renders).
    // Don't forget to call the super methods as well.
    @Override
    public void registerRenderers() {

    }
}
