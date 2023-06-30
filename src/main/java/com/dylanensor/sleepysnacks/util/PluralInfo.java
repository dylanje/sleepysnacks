package com.dylanensor.sleepysnacks.util;

import java.util.Objects;

public interface PluralInfo {
    boolean hasPlural();

    static String plural(String word, boolean hasPlural) {
        Objects.requireNonNull(word);
        if (!hasPlural) {
            return word;
        }
        if (word.endsWith("y")) {
            return word.substring(0, word.length()-1) + "ies";
        }
        if (word.endsWith("sh") || word.endsWith("tomato") || word.endsWith("ch")) {
            return word + "es";
        }
        return word + "s";
    }
}
