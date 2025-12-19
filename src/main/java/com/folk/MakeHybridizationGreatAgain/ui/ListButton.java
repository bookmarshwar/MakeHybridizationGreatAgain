package com.folk.MakeHybridizationGreatAgain.ui;

import java.util.function.Consumer;
import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;

import com.gtnewhorizons.modularui.api.widget.Interactable;
import com.gtnewhorizons.modularui.common.widget.CycleButtonWidget;

import gregtech.api.gui.modularui.GTUITextures;

public class ListButton extends CycleButtonWidget {

    private Consumer<Integer> selectIndexSetter;
    private Supplier<Integer> selectIndexGetter;

    public ListButton setSetterIndex(Consumer<Integer> setter) {
        this.selectIndexSetter = setter;
        return this;
    }

    public ListButton setGetterIndex(Supplier<Integer> setter) {
        this.selectIndexGetter = setter;
        return this;
    }

    public <T extends Enum<T>> ListButton setForEnum_unsafe(Class<T> enumClass, T defaultEnum) {

        int defaultState = defaultEnum.ordinal();
        this.state = defaultState;
        this.setLength(enumClass.getEnumConstants().length);

        super.setGetter(() -> this.state);
        super.setSetter(newState -> this.state = newState);
        super.setForEnum(enumClass, () -> enumClass.getEnumConstants()[this.state], val -> this.state = val.ordinal());
        return this;
    }

    @Override
    public boolean onMouseScroll(int direction) {
        if (direction > 0) {
            if (--state == -1) {
                state = length - 1;
            }
        }

        else {
            if (++state == length) {
                state = 0;
            }
        }
        if (this.selectIndexGetter != null && this.selectIndexGetter.get() == this.state) {
            setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
        } else {
            setBackground();
        }
        if (isClient()) {
            this.texture = textureGetter.apply(this.state);
            if (backgroundGetter != null) {
                setBackground(backgroundGetter.apply(this.state));
            }
        }
        // if (select!=null&&select==(T)getter.get()){
        // setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
        // }else{
        // setBackground();
        // }

        return true;
    }

    @Override
    public void onInit() {
        if (isClient()) {
            syncToServer(2, buffer -> buffer.writeVarIntToBuffer(this.state));
        } else {
            if (this.selectIndexGetter != null) {
                this.state = this.selectIndexGetter.get();
            }
        }
        super.onInit();
        if (this.selectIndexGetter != null && this.selectIndexGetter.get() == this.state) {
            setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
        } else {
            setBackground();
        }

    }

    // @Override
    // public void onInit() {
    //
    // super.onInit();
    // if (this.selectIndexGetter != null && this.selectIndexGetter.get() == this.state) {
    // setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
    // } else {
    // setBackground();
    // }
    //
    // }

    @Override
    public ClickResult onClick(int buttonId, boolean doubleClick) {
        if (this.selectIndexSetter != null) {
            if (isClient()) {
                syncToServer(1, buffer -> buffer.writeVarIntToBuffer(this.state));
            }
            setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
            Interactable.playButtonClickSound();
        }
        return ClickResult.ACKNOWLEDGED;
    }

    @Override
    public void readOnServer(int id, PacketBuffer buf) {
        switch (id) {
            case 1 -> {
                int confirmedState = buf.readVarIntFromBuffer();
                this.selectIndexSetter.accept(confirmedState);
                syncToClient(1, buffer -> buffer.writeVarIntToBuffer(confirmedState));
            }
            case 2 -> {
                syncToClient(1, buffer -> buffer.writeVarIntToBuffer(this.selectIndexGetter.get()));

            }
        }

    }

    @Override
    public void readOnClient(int id, PacketBuffer buf) {
        switch (id) {
            case 1 -> {
                int confirmedState = buf.readVarIntFromBuffer();
                this.selectIndexSetter.accept(confirmedState);

            }
            case 2 -> {
                int confirmedState = buf.readVarIntFromBuffer();
                this.selectIndexSetter.accept(confirmedState);
                this.state = confirmedState;
            }
        }

    }

    @Override
    public <T extends Enum<T>> CycleButtonWidget setForEnum(Class<T> clazz, Supplier<T> getter, Consumer<T> setter) {
        return super.setForEnum(clazz, getter, setter);
    }

}
