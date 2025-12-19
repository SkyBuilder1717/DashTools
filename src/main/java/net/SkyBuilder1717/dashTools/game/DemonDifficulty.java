package net.SkyBuilder1717.dashTools.game;

import java.util.Arrays;
import java.util.Optional;

public enum DemonDifficulty {
    EASY_DEMON(3),
    MEDIUM_DEMON(4),
    HARD_DEMON(0),
    INSANE_DEMON(5),
    EXTREME_DEMON(6);

    private final int value;

    DemonDifficulty(int value) {
        this.value = value;
    }

    public static Optional<DemonDifficulty> valueOf(int value) {
        return Arrays.stream(values())
                .filter(diff -> diff.value == value)
                .findFirst();
    }
}
