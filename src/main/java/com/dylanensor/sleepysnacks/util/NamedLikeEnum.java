package com.dylanensor.sleepysnacks.util;

public interface NamedLikeEnum {
    String name();

    default String getLowercaseName() {
        return name().toLowerCase();
    }
}
