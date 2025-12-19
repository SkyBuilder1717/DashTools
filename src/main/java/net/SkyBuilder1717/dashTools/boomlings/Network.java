package net.SkyBuilder1717.dashTools.boomlings;

import net.SkyBuilder1717.dashTools.game.*;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.*;

public class Network {
    private static final int[] orbs = {0, 0, 50, 75, 125, 175, 225, 275, 350, 425, 500};
    private static final int RATE_LIMIT = 100;
    private static final Semaphore RATE_SEMAPHORE = new Semaphore(RATE_LIMIT, true);
    private static final ScheduledExecutorService RATE_RESET =
            Executors.newSingleThreadScheduledExecutor();

    static {
        RATE_RESET.scheduleAtFixedRate(() -> {
            int missing = RATE_LIMIT - RATE_SEMAPHORE.availablePermits();
            if (missing > 0) {
                RATE_SEMAPHORE.release(missing);
            }
        }, 1, 1, TimeUnit.MINUTES);
    }

    private static CompletableFuture<String> sendRequest(String endpoint, String params, String secret) {
        try {
            RATE_SEMAPHORE.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return CompletableFuture.completedFuture("");
        }

        String urlString = "http://" + Params.host + "/database/" + endpoint + ".php";
        String form = "gameVersion=" + Params.gameVersion + "&binaryVersion=" + Params.binaryVersion
                + params
                + "&secret=" + secret;
        byte[] postData = form.getBytes(StandardCharsets.UTF_8);

        HttpURLConnection conn = null;
        try {
            URL url = new URI(urlString).toURL();
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Host", Params.host);
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("User-Agent", null);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
            conn.setRequestProperty("Cookie", "gd=1;");
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData);
                os.flush();
            }

            int status = conn.getResponseCode();
            InputStream is = (status >= 200 && status < 400) ? conn.getInputStream() : conn.getErrorStream();

            try (BufferedReader in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                String line;
                StringBuilder resp = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    resp.append(line).append('\n');
                }

                return CompletableFuture.completedFuture(resp.toString());
            }
        } catch (IOException e) {
            return CompletableFuture.completedFuture("");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            if (conn != null) conn.disconnect();
        }
    }

    public Profile getUser(String accountID) throws ExecutionException, InterruptedException {
        String response = sendRequest(
                "getGJUserInfo20",
                "&targetAccountID=" + accountID,
                Secret.COMMON
        ).get();

        if (response == null) return null;

        return decodeUserResponse(response);
    }

    public Level[] searchLevel(String search, SearchType type, int page) throws ExecutionException, InterruptedException {
        String response = sendRequest(
                "getGJLevels21",
                "&str=" + search +
                        "&page=" + page +
                        "&type=" + type.value,
                Secret.COMMON
        ).get();

        if (response == null) return null;

        return decodeLevelsResponse(response);
    }

    private Level[] decodeLevelsResponse(String response) {
        if (response == null || response.isBlank() || response.equals("-1")) {
            return new Level[0];
        }

        // levels#creators#songs#meta
        String[] sections = response.split("#");
        if (sections.length == 0) return new Level[0];

        Map<String, String> creators = new HashMap<>();

        if (sections.length > 1) {
            String[] rawCreators = sections[1].split("\\|");

            for (String raw : rawCreators) {
                if (raw.isBlank()) continue;

                String[] parts = raw.split(":");
                creators.put(parts[0], parts[2]);
            }
        }

        String[] rawLevels = sections[0].split("\\|");
        List<Level> levels = new ArrayList<>();

        for (String raw : rawLevels) {
            if (raw.isBlank()) continue;

            String[] parts = raw.split(":");
            Map<Integer, String> data = new HashMap<>();

            for (int i = 0; i < parts.length - 1; i += 2) {
                try {
                    data.put(Integer.parseInt(parts[i]), parts[i + 1]);
                } catch (NumberFormatException ignored) {}
            }

            Level level = new Level();
            level.levelID = parseInt(data.get(1));
            level.levelTitle = data.getOrDefault(2, "Unknown");
            level.description = new String(!data.get(3).isEmpty() ? Base64.getDecoder().decode(data.get(3).replaceAll("/.*$", "")) : Base64.getDecoder().decode("KE5vIGRlc2NyaXB0aW9uIHByb3ZpZGVkKQ=="));

            int officialSong = Integer.parseInt(data.get(12)) + 1;
            int customSong = Integer.parseInt(data.get(35));

            level.song = new Song();
            level.song.id = customSong > 0 ? customSong : officialSong;
            level.song.officialSong = customSong < 1;

            if (parseInt(data.get(8)) > 0) {
                if (Boolean.parseBoolean(data.get(17))) {
                    level.isDemon = Boolean.parseBoolean(data.get(17));
                    level.difficulty = Difficulty.DEMON;
                    level.demonDifficulty = DemonDifficulty.valueOf(parseInt(data.get(43))).get();
                } else if (Boolean.parseBoolean(data.get(25))) {
                    level.difficulty = Difficulty.AUTO;
                } else {
                    level.difficulty = Difficulty.valueOf(parseInt(data.get(9))).get();
                }
            } else {
                level.difficulty = Difficulty.NA;
            }

            level.version = parseInt(data.get(5));
            level.accountID = sections.length > 1 ? parseInt(creators.get(data.getOrDefault(6, "71"))) : 0;
            level.downloads = parseInt(data.get(10));
            level.likes = parseInt(data.get(14));
            level.time = Time.valueOf(parseInt(data.get(15))).get();
            level.stars = parseInt(data.get(18));
            level.orbs = orbs[parseInt(data.get(18))];
            level.isFeatured = parseInt(data.get(17)) > 0;
            level.epic = Epic.valueOf(parseInt(data.get(42))).get();
            level.gameVersion = String.valueOf(parseInt(data.get(13)) / 10);
            level.coins = parseInt(data.get(37));
            level.ratedCoins = Boolean.parseBoolean(data.get(38));
            level.dualMode = Boolean.parseBoolean(data.get(31));

            levels.add(level);
        }

        return levels.toArray(new Level[0]);
    }

    private Profile decodeUserResponse(String response) {
        if (response == null || response.isBlank() || response.equals("-1")) {
            throw new IllegalStateException("Empty response from server");
        }

        Profile profile = new Profile();

        String[] parts = response.split(":");
        Map<Integer, String> indexedData = new HashMap<>();
        for (int i = 0; i < parts.length - 1; i += 2) {
            try {
                int key = Integer.parseInt(parts[i]);
                String value = parts[i + 1];
                indexedData.put(key, value);
            } catch (NumberFormatException ignored) {}
        }

        profile.username = indexedData.getOrDefault(1, "Unknown");
        profile.accountID = parseInt(indexedData.get(16));
        profile.userID = parseInt(indexedData.get(2));
        profile.stars = parseInt(indexedData.get(3));
        profile.moons = parseInt(indexedData.get(52));
        profile.demons = parseInt(indexedData.get(4));
        profile.diamonds = parseInt(indexedData.get(46));
        profile.userCoins = parseInt(indexedData.get(17));
        profile.goldCoins = parseInt(indexedData.get(13));
        profile.creatorPoints = parseInt(indexedData.get(8));

        profile.youtube = indexedData.getOrDefault(20, "");
        profile.twitter = indexedData.getOrDefault(44, "");
        profile.twitch = indexedData.getOrDefault(45, "");

        profile.rank = parseInt(indexedData.get(6));
        profile.globalRank = parseInt(indexedData.get(30));
        profile.mod = Moderator.valueOf(parseInt(indexedData.get(49))).get();

        IconKit kit = new IconKit();
        kit.cube = parseInt(indexedData.get(21));
        kit.ship = parseInt(indexedData.get(22));
        kit.ball = parseInt(indexedData.get(23));
        kit.bird = parseInt(indexedData.get(24));
        kit.dart = parseInt(indexedData.get(25));
        kit.robot = parseInt(indexedData.get(26));
        kit.spider = parseInt(indexedData.get(43));
        kit.swing = parseInt(indexedData.get(53));
        kit.jetpack = parseInt(indexedData.get(54));
        // no matter what, it doesn't want to work with static environment, idk fucking why
        ColorPalette clr = new ColorPalette();
        kit.primaryColor = clr.get(indexedData.get(10));
        kit.secondaryColor = clr.get(indexedData.get(11));
        kit.glowColor = clr.get(indexedData.get(51));
        kit.glow = parseInt(indexedData.get(28));
        kit.explosion = parseInt(indexedData.get(48));
        profile.iconKit = kit;

        return profile;
    }

    private Integer parseInt(String value) {
        if (value == null || value.isEmpty() || value.equals("-1")) {
            return 0;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}