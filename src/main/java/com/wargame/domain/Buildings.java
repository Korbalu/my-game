package com.wargame.domain;

public enum Buildings {
    Barracks("Barracks", 2, 40),
    Mine("Mine", 10, 50),
    Wall("Wall", 5, 40);
    private String displayName;
    private int production;
    private int cost;

    Buildings(String displayName, int production, int cost) {
        this.displayName = displayName;
        this.production = production;
        this.cost = cost;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getProduction() {
        return production;
    }

    public int getCost() {
        return cost;
    }
}
