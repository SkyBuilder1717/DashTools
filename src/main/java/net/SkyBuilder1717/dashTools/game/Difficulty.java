package net.SkyBuilder1717.dashTools.game;

import java.util.Arrays;
import java.util.Optional;

public enum Difficulty {
    NA(0),
    AUTO(1),
    EASY(10),
    NORMAL(20),
    HARD(30),
    HARDER(40),
    INSANE(50),
    DEMON(60);

    private final int value;

    Difficulty(int value) {
        this.value = value;
    }

    public static Optional<Difficulty> valueOf(int value) {
        return Arrays.stream(values())
                .filter(diff -> diff.value == value)
                .findFirst();
    }
}