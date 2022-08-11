package Game;

public class PcBrain {

    public int[] useRandomAlgorithm(Board board) {
        int x = (int) Math.floor(Math.random() * 10) + 1;
        int y = (int) Math.floor(Math.random() * 10) + 1;
        return new int[]{x, y};
    }
}
