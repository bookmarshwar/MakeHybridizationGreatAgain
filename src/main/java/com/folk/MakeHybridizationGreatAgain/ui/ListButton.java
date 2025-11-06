package com.folk.MakeHybridizationGreatAgain.ui;

import java.util.function.Consumer;
import java.util.function.Supplier;

import com.gtnewhorizons.modularui.api.drawable.IDrawable;
import com.gtnewhorizons.modularui.common.widget.CycleButtonWidget;
import gregtech.api.gui.modularui.GTUITextures;

public class ListButton extends CycleButtonWidget {

    private Consumer<Integer> selectIndexSetter;
    private Supplier<Integer> selectIndexGetter;

    public ListButton setSetterIndex(Consumer<Integer> setter) {
        this.selectIndexSetter = setter;
        return this;
    }

    public ListButton setGetterIndex(Supplier<Integer> getter) {
        this.selectIndexGetter = getter;
        return this;
    }

    /**
     * 【修复点 1】
     * 这个方法现在只负责设置按钮的选项数量和初始显示状态。
     * 它不再破坏父类的 Getter/Setter。
     */
    public <T extends Enum<T>> ListButton setForEnum_unsafe(Class<T> enumClass, T defaultEnum) {
        int defaultState = defaultEnum.ordinal();
        this.state = defaultState; // 仅用于设置按钮的初始视觉状态
        this.setLength(enumClass.getEnumConstants().length);
        return this;
    }

    /**
     * 【修复点 2】
     * 在组件初始化时，将我们设置的 Getter/Setter 传递给父类 CycleButtonWidget。
     * 这样，父类的所有同步逻辑都会正确地操作我们的 TileEntity 数据。
     */
    @Override
    public void onInit() {
        if (this.selectIndexGetter != null && this.selectIndexSetter != null) {
            // 将与 TileEntity 交互的 getter/setter 传递给父类
            super.setGetter(this.selectIndexGetter);
            super.setSetter(this.selectIndexSetter);
        }
        // 调用父类的 onInit，它会使用我们刚刚设置的 getter 来同步按钮的初始状态
        super.onInit();

        // 你的背景高亮逻辑可以保留
        if (this.selectIndexGetter != null && this.selectIndexGetter.get() == this.state) {
            setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
        } else {
            setBackground();
        }
    }

    /**
     * 【修复点 3】
     * 滚轮事件现在只负责调用父类的 prev/next 方法。
     * 父类会自动处理同步，我们不需要手动调用 setter。
     */
    @Override
    public boolean onMouseScroll(int direction) {
        if (direction > 0) {
            this.prev(); // 调用父类方法，触发同步
        } else {
            this.next(); // 调用父类方法，触发同步
        }
        // 你的背景高亮逻辑可以保留
        if (this.selectIndexGetter != null && this.selectIndexGetter.get() == this.state) {
            setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
        } else {
            setBackground();
        }
        return true;
    }

    /**
     * 【修复点 4】
     * 点击事件同样委托给父类处理。
     * 移除了在客户端直接调用 setter 的错误代码。
     */
    @Override
    public ClickResult onClick(int buttonId, boolean doubleClick) {
        // 直接调用父类的 onClick，它会处理 next/prev 和同步
        // 播放声音等逻辑也由父类处理
        ClickResult result = super.onClick(buttonId, doubleClick);

        // 你的背景高亮逻辑可以保留
        if (this.selectIndexGetter != null && this.selectIndexGetter.get() == this.state) {
            setBackground(GTUITextures.BACKGROUND_TEXT_FIELD);
        } else {
            setBackground();
        }
        return result;
    }
}
