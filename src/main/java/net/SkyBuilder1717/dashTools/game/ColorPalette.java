package net.SkyBuilder1717.dashTools.game;

import org.jetbrains.annotations.NotNull;
import java.util.Map;
import java.util.HashMap;

public class ColorPalette {
    private final static Map<String, Color> colors = new HashMap<>();

    // well shit
    public ColorPalette() {
        colors.put("0", new Color(125,255,0));
        colors.put("1", new Color(0,255,0));
        colors.put("2", new Color(0,255,125));
        colors.put("3", new Color(0,255,255));
        colors.put("4", new Color(0,125,255));
        colors.put("5", new Color(0,0,255));
        colors.put("6", new Color(125,0,255));
        colors.put("7", new Color(255,0,255));
        colors.put("8", new Color(255,0,125));
        colors.put("9", new Color(255,0,0));
        colors.put("10", new Color(255,125,0));
        colors.put("11", new Color(255,255,0));
        colors.put("12", new Color(255,255,255));
        colors.put("13", new Color(185,0,255));
        colors.put("14", new Color(255,185,0));
        colors.put("15", new Color(0,0,0));
        colors.put("16", new Color(0,200,255));
        colors.put("17", new Color(175,175,175));
        colors.put("18", new Color(90,90,90));
        colors.put("19", new Color(255,125,125));
        colors.put("20", new Color(0,175,75));
        colors.put("21", new Color(0,125,125));
        colors.put("22", new Color(0,75,175));
        colors.put("23", new Color(75,0,175));
        colors.put("24", new Color(125,0,125));
        colors.put("25", new Color(175,0,75));
        colors.put("26", new Color(175,75,0));
        colors.put("27", new Color(125,125,0));
        colors.put("28", new Color(75,175,0));
        colors.put("29", new Color(255,75,0));
        colors.put("30", new Color(150,50,0));
        colors.put("31", new Color(150,100,0));
        colors.put("32", new Color(100,150,0));
        colors.put("33", new Color(0,150,100));
        colors.put("34", new Color(0,100,150));
        colors.put("35", new Color(100,0,150));
        colors.put("36", new Color(150,0,100));
        colors.put("37", new Color(150,0,0));
        colors.put("38", new Color(0,150,0));
        colors.put("39", new Color(0,0,150));
        colors.put("40", new Color(125,255,175));
        colors.put("41", new Color(125,125,255));
        colors.put("42", new Color(255,250,127));
        colors.put("43", new Color(250,127,255));
        colors.put("44", new Color(0,255,192));
        colors.put("45", new Color(80,50,14));
        colors.put("46", new Color(205,165,118));
        colors.put("47", new Color(182,128,255));
        colors.put("48", new Color(255,58,58));
        colors.put("49", new Color(77,77,143));
        colors.put("50", new Color(0,10,76));
        colors.put("51", new Color(253,212,206));
        colors.put("52", new Color(190,181,255));
        colors.put("53", new Color(112,0,0));
        colors.put("54", new Color(82,2,0));
        colors.put("55", new Color(56,1,6));
        colors.put("56", new Color(128,79,79));
        colors.put("57", new Color(122,53,53));
        colors.put("58", new Color(81,36,36));
        colors.put("59", new Color(163,98,70));
        colors.put("60", new Color(117,73,54));
        colors.put("61", new Color(86,53,40));
        colors.put("62", new Color(255,185,114));
        colors.put("63", new Color(255,160,64));
        colors.put("64", new Color(102,49,30));
        colors.put("65", new Color(91,39,0));
        colors.put("66", new Color(71,32,0));
        colors.put("67", new Color(167,123,77));
        colors.put("68", new Color(109,83,57));
        colors.put("69", new Color(81,62,42));
        colors.put("70", new Color(255,255,192));
        colors.put("71", new Color(253,224,160));
        colors.put("72", new Color(192,255,160));
        colors.put("73", new Color(177,255,109));
        colors.put("74", new Color(192,255,224));
        colors.put("75", new Color(148,255,228));
        colors.put("76", new Color(67,161,138));
        colors.put("77", new Color(49,109,95));
        colors.put("78", new Color(38,84,73));
        colors.put("79", new Color(0,96,0));
        colors.put("80", new Color(0,64,0));
        colors.put("81", new Color(0,96,96));
        colors.put("82", new Color(0,64,64));
        colors.put("83", new Color(160,255,255));
        colors.put("84", new Color(1,7,112));
        colors.put("85", new Color(0,73,109));
        colors.put("86", new Color(0,50,76));
        colors.put("87", new Color(0,38,56));
        colors.put("88", new Color(80,128,173));
        colors.put("89", new Color(51,83,117));
        colors.put("90", new Color(35,60,86));
        colors.put("91", new Color(224,224,224));
        colors.put("92", new Color(61,6,140));
        colors.put("93", new Color(55,8,96));
        colors.put("94", new Color(64,64,64));
        colors.put("95", new Color(111,73,164));
        colors.put("96", new Color(84,54,127));
        colors.put("97", new Color(66,42,99));
        colors.put("98", new Color(252,181,255));
        colors.put("99", new Color(175,87,175));
        colors.put("100", new Color(130,67,130));
        colors.put("101", new Color(94,49,94));
        colors.put("102", new Color(128,128,128));
        colors.put("103", new Color(102,3,62));
        colors.put("104", new Color(71,1,52));
        colors.put("105", new Color(210,255,50));
        colors.put("106", new Color(118,189,255));
    }

    public static Color get(String key) {
        return colors.get(key);
    }

    public record Color(int r, int g, int b) {
        @Override
        public @NotNull String toString() {
            return "Color{r=" + r + ", g=" + g + ", b=" + b + '}';
        }
    }
}