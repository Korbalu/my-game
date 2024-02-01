package com.wargame.domain;

public enum Race {
    Orc("Orc"),
    Human("Human"),
    Elf("Elf");
    private String displayName;

    Race(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
