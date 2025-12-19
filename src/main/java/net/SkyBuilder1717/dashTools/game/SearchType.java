package net.SkyBuilder1717.dashTools.game;

import java.util.Arrays;
import java.util.Optional;

public enum SearchType {
    QUERY(0),
    MOST_DOWNLOADS(1),
    MOST_LIKES(2),
    TRENDING(3),
    RECENT(4),
    BY_USER(5),
    FEATURED(6),
    MAGIC(7),
    SENT_LEVELS_BY_MODERATORS(8),
    LIST(10),
    AWARDED(11),
    MOST_LIKES_GDWORLD(15),
    HALL_OF_FAME(16),
    FEATURED_GDWORLD(17),
    DAILY_HISTORY(21),
    WEEKLY_HISTORY(22);

    public final int value;

    SearchType(int value) {
        this.value = value;
    }

    public static Optional<SearchType> valueOf(int value) {
        return Arrays.stream(values())
                .filter(type -> type.value == value)
                .findFirst();
    }
}