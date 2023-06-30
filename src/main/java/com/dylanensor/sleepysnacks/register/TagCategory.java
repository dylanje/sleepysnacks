package com.dylanensor.sleepysnacks.register;

public enum TagCategory {
    NONE,
    CROPS,
    FRUITS,
    GRAIN,
    NUTS,
    VEGETABLES;

    private final String lowerCaseName;

    TagCategory() {
        lowerCaseName = name().toLowerCase();
    }

    public String getLowerCaseName() {
        return lowerCaseName;
    }
}
