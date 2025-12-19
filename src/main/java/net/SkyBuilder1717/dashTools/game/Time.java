package net.SkyBuilder1717.dashTools.game;

import java.util.Arrays;
import java.util.Optional;

public enum Time {
    TINY(0),
    SHORT(1),
    MEDIUM(2),
    LONG(3),
    XL(4),
    PLAT(5);

    private final int value;

    Time(int value) {
        this.value = value;
    }

    public static Optional<Time> valueOf(int value) {
        return Arrays.stream(values())
                .filter(time -> time.value == value)
                .findFirst();
    }
}