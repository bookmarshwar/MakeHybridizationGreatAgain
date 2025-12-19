package com.folk.MakeHybridizationGreatAgain.enums;

public enum CrossingMode {

    Gr("生长值"),
    Ga("产量"),
    Re("作物抗性");

    private final String displayName;

    CrossingMode(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
