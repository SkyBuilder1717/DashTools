package net.SkyBuilder1717.dashTools.game;

import java.util.Arrays;
import java.util.Optional;

public enum Moderator {
    NONE(0),
    MOD(1),
    ELDER_MOD(2),
    LEADERBOARD_MOD(3);

    private final int value;

    Moderator(int value) {
        this.value = value;
    }

    public static Optional<Moderator> valueOf(int value) {
        return Arrays.stream(values())
                .filter(mod -> mod.value == value)
                .findFirst();
    }
}
