package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PcBrain {

    private Orientation discoveredOrientation;
    private ArrayList<Coordinate> possibleCoordinates;
    private ArrayList<Coordinate> highProbabilityCoordinates = new ArrayList<>();
    private HashSet<Coordinate> trackedShipCoordinates = new HashSet<>();

    public PcBrain() {
        possibleCoordinates = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {
            for (int j = 1; j <= 11; j++) {
                this.possibleCoordinates.add(new Coordinate(i, j));
            }
        }
        this.discoveredOrientation = Orientation.UNKNOWN;
    }

    public Coordinate useRandomAlgorithm() {
        int x = (int) Math.floor(Math.random() * 10) + 1;
        int y = (int) Math.floor(Math.random() * 10) + 1;
        return new Coordinate(x, y);
    }

    public Coordinate useTrackingAlgorithm() {
//        If there are no High Probability Coordinates to check, use a random possible coordinate
        if (highProbabilityCoordinates == null || highProbabilityCoordinates.isEmpty()) {
            int randomIndex = (int) Math.floor(Math.random() * this.possibleCoordinates.size());
            return this.possibleCoordinates.get(randomIndex);
        } else {
            int randomIndex = (int) Math.floor(Math.random() * this.highProbabilityCoordinates.size());
            return this.highProbabilityCoordinates.get(randomIndex);
        }
    }

    public void processShotResult(Coordinate coordinate, BoardValue result) {
        this.highProbabilityCoordinates.remove(coordinate);
        this.possibleCoordinates.remove(coordinate);
        if (result == BoardValue.HIT) {
            this.trackedShipCoordinates.add(coordinate);
//            To find the orientation of the ship, check if there are at least 2 coordinates that have the same x o y values
            if (this.discoveredOrientation == Orientation.UNKNOWN) {
                if (this.trackedShipCoordinates.stream().filter(coord -> coord.getX() == coordinate.getX()).count() >= 2)
                    this.discoveredOrientation = Orientation.HORIZONTAL;
                if (this.trackedShipCoordinates.stream().filter(coord -> coord.getY() == coordinate.getY()).count() >= 2)
                    this.discoveredOrientation = Orientation.VERTICAL;
            }
        }
    }

    public void addHighProbabilityCoordinates(int[] coordinate) {
//        Check every possible direction from the coordinate passed as parameter.
//        If the coordinate is valid and belongs to a possible coordinate, move that coordinate
//          from possible to high probability.

        int x = coordinate[0];
        int y = coordinate[1];


//        Depending on if the orientation of the ship is known, add the 4 quadrants as possible
//          high probability coordinates, or 2 if its known (East, West for horizontal; North
//          South for vertical)
        HashMap<String, Coordinate> quadrants = new HashMap<>();

        if (this.discoveredOrientation == Orientation.UNKNOWN) {
            quadrants.put("North", new Coordinate(x, y - 1));
            quadrants.put("South", new Coordinate(x, y + 1));
            quadrants.put("East", new Coordinate(x + 1, y));
            quadrants.put("West", new Coordinate(x - 1, y));
        } else if (this.discoveredOrientation == Orientation.HORIZONTAL) {
            quadrants.put("East", new Coordinate(x + 1, y));
            quadrants.put("West", new Coordinate(x - 1, y));
        } else if (this.discoveredOrientation == Orientation.VERTICAL) {
            quadrants.put("North", new Coordinate(x, y - 1));
            quadrants.put("South", new Coordinate(x, y + 1));
        }

//        Loop over each neighbour, if that coordinate was already a possible coordinate,
//          remove it from that category and add it to the high probability ones.
        for (Coordinate coord : quadrants.values()) {
            if (Board.isValidCoordinate(coord) && this.possibleCoordinates.stream().anyMatch(coord::equals)) {
                this.possibleCoordinates.remove(coord);
                this.highProbabilityCoordinates.add(coord);
            }
        }


    }


}
