package Game;

public class GameConfig {

    protected enum Difficulty {
        EASY,
        HARD,
        IRONMAN
    }

    private static String playerName = "Player 1";
    private static Difficulty difficulty = Difficulty.HARD;

    public static String getPlayerName() {
        return playerName;
    }

    public static void setPlayerName(String playerName) {
        GameConfig.playerName = playerName;
    }

    public static Difficulty getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(Difficulty difficulty) {
        GameConfig.difficulty = difficulty;
    }
}
