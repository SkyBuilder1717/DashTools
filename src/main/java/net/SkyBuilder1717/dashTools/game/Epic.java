package net.SkyBuilder1717.dashTools.game;

import java.util.Arrays;
import java.util.Optional;

public enum Epic {
    NONE(0),
    EPIC(1),
    LEGENDARY(2),
    MYTHIC(3);

    private final int value;

    Epic(int value) {
        this.value = value;
    }

    public static Optional<Epic> valueOf(int value) {
        return Arrays.stream(values())
                .filter(mode -> mode.value == value)
                .findFirst();
    }
}
