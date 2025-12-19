package net.SkyBuilder1717.dashTools.game;

public class Level {
    // Main Information
    public int levelID = 0;
    public int copiedID = 0;
    public String levelTitle = "";
    public String description = "";
    public int accountID = 0;

    // Difficulty
    public Difficulty difficulty = Difficulty.NA;
    public DemonDifficulty demonDifficulty = DemonDifficulty.HARD_DEMON;
    public boolean isDemon = true;
    public boolean isFeatured = true;
    public Epic epic = Epic.NONE;

    // Misc Information
    public int likes = 0;
    public int downloads = 0;
    public boolean rated = false;
    public int stars = 0;
    public int orbs = 0;
    public int coins = 0;
    public int version = 1;
    public String gameVersion = "";

    // Gameplay
    public boolean dualMode = false;
    public boolean ratedCoins = false;
    public Time time = Time.SHORT;
    public Song song = new Song();
}
