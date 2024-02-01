package com.wargame.domain;

public enum Units {
    Archer1("Archer1", 10, 5, 8),
    Archer2("Archer2", 12, 5, 12),
    Archer3("Archer3", 14, 7, 14),
    Swordsman1("Swordsman1", 8, 8, 9),
    Swordsman2("Swordsman2", 10, 10, 13),
    Swordsman3("Swordsman3", 12, 10, 16),
    Horseman1("Horseman1", 12, 8, 15),
    Horseman2("Horseman2", 14, 10, 20),
    Horseman3("Horseman3", 16, 12, 30);

    private String displayName;
    private int attack;
    private int defense;
    private int cost;

    Units(String displayName, int attack, int defense, int cost) {
        this.displayName = displayName;
        this.attack = attack;
        this.defense = defense;
        this.cost = cost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefense() {
        return defense;
    }

    public int getCost() {
        return cost;
    }
}
