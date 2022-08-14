package Game;

import java.util.ArrayList;
import java.util.HashMap;

public class PcBrain {

    private ArrayList<int[]> possibleCoordinates;
    private ArrayList<int[]> highProbabilityCoordinates;

    public PcBrain() {
        for (int i = 0; i <= 10; i++) {
            for (int j = 0; j <= 10; j++) {
                this.possibleCoordinates.add(new int[]{i, j});
            }
        }
    }

    public int[] useRandomAlgorithm() {
        int x = (int) Math.floor(Math.random() * 10) + 1;
        int y = (int) Math.floor(Math.random() * 10) + 1;
        return new int[]{x, y};
    }

    public int[] useTrackingAlgorithm() {
//        If there are no High Probability Coordinates to check, use a random possible coordinate
        if (highProbabilityCoordinates == null) {
            int randomIndex = (int) Math.floor(Math.random() * this.possibleCoordinates.size());
            return this.possibleCoordinates.get(randomIndex);
        } else {
            return null;
        }
    }

    public void addHighProbabilityCoordinates(int[] coordinate) {
        int x = coordinate[0];
        int y = coordinate[1];

//        Check every possible direction from the coordinate passed as parameter.
//        If the coordinate is valid and belongs to a possible coordinate, move that coordinate
//          from possible to high probability.
        HashMap<String, int[]> quadrants = new HashMap<>();
        quadrants.put("North", new int[]{x, y - 1});
        quadrants.put("South", new int[]{x, y + 1});
        quadrants.put("East", new int[]{x + 1, y});
        quadrants.put("West", new int[]{x - 1, y});

//        Loop over each neighbour, if that coordinate was already a possible coordinate,
//          remove it from that category and add it to the high probability ones.
        for (int[] coord : quadrants.values()) {
            if (isValidCoordinate(coord) && this.possibleCoordinates.contains(coord)) {
                this.possibleCoordinates.remove(coord);
                this.highProbabilityCoordinates.add(coord);
            }
        }
    }

    private boolean isValidCoordinate(int[] coordinate) {
        int x = coordinate[0];
        int y = coordinate[1];

        return x >= 0 && x <= 10 && y >= 0 && y <= 10;
    }


}
