package com.main.server.model;

public enum Sex {
    MALE("male"), FEMALE("female");

    private final String name;

    Sex(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
