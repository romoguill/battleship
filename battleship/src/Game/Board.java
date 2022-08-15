package Game;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Board {

    private final String[][] boardMatrix;
    private final ArrayList<int[]> takenCoordinates = new ArrayList<>();


//    NO VEO QUE SE USE EN NINGUN LADO

//    private String[][] boardMatrixPlayer = new String[11][11];

//    public void blankBoard(String[][] boardMatrix) {
//        for (Integer i = 0; i < 11; i++) {
//            for (Integer j = 0; j < 11; j++) {
//                if (i == 0 && j == 0) {
//                    boardMatrix[0][0] = "*";
//                } else {
//                    if (i == 0 && j != 0) {
//                        boardMatrix[0][j] = i.toString();

    public String[][] getBoardMatrix() {
        String[][] deepCopy = new String[10][10];
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

//                    }
//                }
//                ;
//                if (i > 0 && j > 0) {
//                    boardMatrix[i][j] = " ";
//                }
//                ;
//            }
//            ;
//        }
//    }

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

    public boolean placeShip2(int xOrigin, int yOrigin, Ship ship, Orientation orientation) {
//        Get the possible footprint of the ship by adding coordinates to an array.
        ArrayList<int[]> shipFootprint = new ArrayList<>();
        for (int i = 0; i < ship.getSize(); i++) {
            if (orientation == Orientation.HORIZONTAL) {
                shipFootprint.add(new int[]{xOrigin + i, yOrigin});
            } else if (orientation == Orientation.VERTICAL) {
                shipFootprint.add(new int[]{xOrigin, yOrigin + i});
            }
        }

//        Check if footprint of ship has invalid or already taken coordinates
        for (int[] coordinate : shipFootprint) {
            if (!Board.isValidCoordinate(coordinate) || this.takenCoordinates.contains(coordinate)) {
                return false;
            }
        }

//        For each coordinate in the footprint add its coordinate and all surrounding ones to the takenCoordinates Array
        for (int[] coordinate : shipFootprint) {
            int x = coordinate[0];
            int y = coordinate[1];
            this.setValueOfBoardMatrix(x, y, "*");
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int[] newCoordinate = new int[]{x + i, j + 1};
                    if (Board.isValidCoordinate(newCoordinate) && !takenCoordinates.contains(newCoordinate)) {
                        this.takenCoordinates.add(newCoordinate);
                    }
                }
            }
        }
        this.printBoard();
        return true;
    }

    public static boolean isValidCoordinate(int[] coordinate) {
        int x = coordinate[0];
        int y = coordinate[1];

        return x >= 1 && x <= 10 && y >= 1 && y <= 10;
    }

    public void cleanMatrix() {
        System.out.println(boardMatrix);
        System.out.println(boardMatrix.length);
        for (int i = 1; i < boardMatrix.length; i++) {
            for (int j = 1; j < boardMatrix.length; j++) {
                if (boardMatrix[j][i].equals("o")) {
                    boardMatrix[j][i] = "";
                }
            }
        }
    }


}



