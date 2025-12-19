package net.SkyBuilder1717.dashTools.game;

import java.util.Arrays;
import java.util.Optional;

public enum Gamemode {
    CUBE(0),
    SHIP(1),
    BALL(2),
    BIRD(3),
    DART(4),
    ROBOT(5),
    SPIDER(6),
    SWING_COPTER(7),
    JETPACK(8);

    private final int value;

    Gamemode(int value) {
        this.value = value;
    }

    public static Optional<Gamemode> valueOf(int value) {
        return Arrays.stream(values())
                .filter(mode -> mode.value == value)
                .findFirst();
    }
}
