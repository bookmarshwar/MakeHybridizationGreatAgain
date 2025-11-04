package com.folk.MakeHybridizationGreatAgain.ui;

import com.gtnewhorizons.modularui.api.widget.Interactable;

import com.gtnewhorizons.modularui.common.widget.CycleButtonWidget;

import gregtech.api.gui.modularui.GTUITextures;

import java.util.function.Consumer;

import java.util.function.Supplier;

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
        super.setForEnum(
            enumClass,
            () -> enumClass.getEnumConstants()[this.state],
            val -> this.state = val.ordinal()
        );
        return this;
    }
    @Override
    public boolean onMouseScroll(int direction) {
        if (direction > 0) {
            prev();
        }

        else {
            next();
        }
        if (this.selectIndexGetter != null&&this.selectIndexGetter.get()==this.state){
            setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
        }else{
            setBackground();
        }
//        if (select!=null&&select==(T)getter.get()){
//            setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
//        }else{
//            setBackground();
//        }

        return true;
    }

    @Override
    public void onInit() {

        super.onInit();
        if (this.selectIndexGetter != null&&this.selectIndexGetter.get()==this.state){
            setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
        }else{
            setBackground();
        }

    }

    @Override
    public ClickResult onClick(int buttonId, boolean doubleClick) {

        if (this.selectIndexSetter != null) {
            this.selectIndexSetter.accept(this.state);
            setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
            Interactable.playButtonClickSound();
        }
        return  ClickResult.ACKNOWLEDGED;
    }

    @Override
    public <T extends Enum<T>> CycleButtonWidget setForEnum(Class<T> clazz, Supplier<T> getter, Consumer<T> setter) {
        return super.setForEnum(clazz, getter, setter);
    }
}
