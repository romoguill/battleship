package Game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Board {

    private final String[][] boardMatrix;
    private final HashSet<Coordinate> takenCoordinates = new HashSet<>();

    public String[][] getBoardMatrix() {
        String[][] deepCopy = new String[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                deepCopy[i][j] = this.boardMatrix[i][j];
            }
        }
        return deepCopy;
    }

    public void setValueOfBoardMatrix(int x, int y, String value) {
        this.boardMatrix[y][x] = value;
    }

    public Board() {
        this.boardMatrix = new String[11][11];

        for (int r = 0; r < boardMatrix.length; r++) {
            for (int s = 0; s < boardMatrix.length; s++) {
                if (r == 0 && s == 0) {
                    boardMatrix[0][0] = "*";
                }
                if (r == 0 && s != 0) {
                    boardMatrix[0][s] = Integer.toString(s);
                }
                if (s == 0 && r != 0) {
                    boardMatrix[r][0] = Integer.toString(r);
                }
                if (r > 0 && s > 0) {
                    boardMatrix[r][s] = "~";
                }
            }
        }
    }


    public void printBoard() {
        for (int r = 0; r < boardMatrix.length; r++) {
            for (int s = 0; s < boardMatrix.length; s++)
                System.out.print(boardMatrix[r][s] + " ");
            System.out.println();
        }
    }

    public void placeShip(Ship ship) {
        int shipSize = ship.getSize();
        String placementCondition = "error";
        Scanner scanner = new Scanner(System.in);
        String inputString;
        int overPlacement = 0;
        do {
            String placementDirection;
            do {
                System.out.print("DIRECCION " + ship.getName() + String.format("( %d ), V/H: ", ship.getSize()));
                placementDirection = scanner.next().toUpperCase();
            } while (!placementDirection.equals("V") && !(placementDirection.equals("H")));
            boolean placementVertical = placementDirection.equals("V");
            System.out.print("coordenada X comienzo:  ");
            inputString = scanner.next();
            int x1 = Integer.parseInt(inputString);
            System.out.print("coordenada Y comienzo: ");
            inputString = scanner.next();
            int y1 = Integer.parseInt(inputString);
            int xStart = Math.max(x1 - 1, 1);
            int yStart = Math.max(y1 - 1, 1);
            int xEnd, yEnd;
            if (placementVertical) {
                xEnd = x1 + 1;
                yEnd = y1 + shipSize;
            } else {
                yEnd = y1 + 1;
                xEnd = x1 + shipSize;
            }
            if (xEnd == boardMatrix.length) {
                xEnd--;
            }
            if (yEnd == boardMatrix.length) {
                yEnd--;
            }
            if (xEnd > 10 || yEnd > 10) {
                placementCondition = "error";
            } else {
                placementCondition = "ok";
            }
            if (placementCondition.equals("ok")) {
                if (placementVertical) {
                    for (int i = xStart; i < xEnd + 1; i++) {
                        if (boardMatrix[i][x1].equals("~")) {
                        } else {
                            overPlacement++;
                        }
                    }
                } else {
                    for (int j = yStart; j < yStart + 1; j++) {

                        if (boardMatrix[y1][j].equals("~")) {
                        } else {
                            overPlacement++;
                        }
                    }
                }
            }
            System.out.println("X" + "--" + xStart + "--" + xEnd + "Y" + "--" + yStart + "--" + yEnd);
            if (placementCondition.equals("ok") && overPlacement == 0) {
                for (int i = xStart; i < xEnd + 1; i++)
                    for (int j = yStart; j < yEnd + 1; j++) {
                        boardMatrix[j][i] = "o";
                    }
                for (int i = 0; i < shipSize; i++) {
                    if (placementVertical) {
                        boardMatrix[i + y1][x1] = "*";
                    } else {
                        boardMatrix[y1][x1 + i] = "*";
                    }
                }
            }
        } while (placementCondition.equals("error") || overPlacement > 0);
    }

    public boolean placeShip2(Coordinate coordinate, Ship ship, Orientation orientation) {
//        Get the possible footprint of the ship by adding coordinates to an array.
        ArrayList<Coordinate> shipFootprint = new ArrayList<Coordinate>();
        for (int i = 0; i < ship.getSize(); i++) {
            if (orientation == Orientation.HORIZONTAL) {
                shipFootprint.add(new Coordinate(coordinate.getX() + i, coordinate.getY()));
            } else if (orientation == Orientation.VERTICAL) {
                shipFootprint.add(new Coordinate(coordinate.getX(), coordinate.getY() + i));
            }
        }

//        Check if footprint of ship has invalid or already taken coordinates
        for (Coordinate c : shipFootprint) {
            if (!Board.isValidCoordinate(c) ||
                    this.takenCoordinates.stream().anyMatch(c::equals)) {
                return false;
            } else {
                ship.setShipCoordinates(shipFootprint);
            }
        }

//        For each coordinate in the footprint add its coordinate and all surrounding ones to the takenCoordinates Array
        for (Coordinate c : ship.getShipCoordinates()) {
            this.setValueOfBoardMatrix(c.getX(), c.getY(), "*");
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    Coordinate newCoordinate = new Coordinate(coordinate.getX() + i, coordinate.getY() + j);
                    if (Board.isValidCoordinate(newCoordinate)) {
                        this.takenCoordinates.add(newCoordinate);
                    }
                }
            }
        }
        this.printBoard();
        return true;
    }

    public static boolean isValidCoordinate(Coordinate coordinate) {
        return coordinate.getX() >= 1 && coordinate.getX() <= 10 &&
                coordinate.getY() >= 1 && coordinate.getY() <= 10;
    }
}



