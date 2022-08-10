package Game;

import java.util.Arrays;
import java.util.Scanner;

public class Board {

    private final String[][] boardMatrix;


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
        String[][] deepCopy = new String[11][11];
        for (int i = 0; i < 11; i++) {
            for (int j = 0; j < 11; j++) {
                deepCopy[i][j] = this.boardMatrix[i][j];
            }
        }
        return deepCopy;
    }

    public void setValueOfBoardMatrix(int x, int y, String value) {
        this.boardMatrix[x][y] = value;
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


//        for (int r = 0; r < boardMatrix.length; r++) {
//            for (int s = 0; s < boardMatrix.length; s++) {
//                System.out.print(boardMatrix[r][s] + " ");
//            }
//            System.out.println();
//        }
        boolean wasPlaced = false;

        int shipSize = ship.getSize();
        String placementCondition = "error";

        Scanner scanner = new Scanner(System.in);
        String inputString;

        int x1;
        int y1;
        String placementDirection;

        int overPlacement = 0;
        do {
            System.out.print("DIRECCION " + ship.getName() + String.format("( %d ), V/H: ", ship.getSize()));
            placementDirection = scanner.next();
            System.out.print("coordenada X comienzo:  ");
            inputString = scanner.next();
            y1 = Integer.parseInt(inputString);
            System.out.print("coordenada Y comienzo: ");
            inputString = scanner.next();
            x1 = Integer.parseInt(inputString);

            int shipWidth = 0;
            int shipHeight = 0;

            if (placementDirection.equalsIgnoreCase("V")) {
                shipWidth = 1;
                shipHeight = shipSize;
            } else {
                shipWidth = shipSize;
                shipHeight = 1;
            }
            int xStart = 0;
            int yStart = 0;
            int xEnd = 0;
            int yEnd = 0;

            if (y1 > 1) {
                xStart = y1 - 1;
            } else {
                xStart = 1;
            }
            ;
            if (y1 > boardMatrix.length - 2) {
                xEnd = boardMatrix.length - 1;
            } else {
                xEnd = y1 + 1;
            }
            ;
            if (x1 > 1) {
                yStart = x1 - 1;
            } else {
                yStart = 1;
            }
            ;
            if (x1 > boardMatrix.length - 2) {
                yEnd = boardMatrix.length - 1;
            } else {
                yEnd = 1 + x1;
            }
            ;
            if (placementDirection.equalsIgnoreCase("V")) {
                yEnd = yEnd + shipSize - 1;
            } else {
                xEnd = xEnd + shipSize - 1;
            }
            ;
            if (xEnd > 10 || yEnd > 10) {
                placementCondition = "error";
            } else {
                placementCondition = "ok";
            }
            ;


            overPlacement = 0;
            if (placementCondition.equals("ok")) {

                for (int i = xStart; i < xEnd + 1; i++) {
                    for (int j = yStart; j < yEnd + 1; j++) {
                        if (boardMatrix[i][j].equals("~")) {
                        } else {
                            overPlacement++;
                        }
                    }
                }
            }
            System.out.println("X" + "--" + xStart + "--" + xEnd + "Y" + "--" + yStart + "--" + yEnd);
            if (placementCondition.equals("ok")) {
                for (int i = 0; i < shipSize; i++) {
                    if (placementDirection.equalsIgnoreCase("V")) {
                        yEnd = yEnd + shipSize - 1;
                        if (x1 + shipSize - 1 == boardMatrix.length - 1) {
                            yEnd = boardMatrix.length - 1;
                        }
                    } else {
                        xEnd = xEnd + shipSize - 1;
                        if (y1 + shipSize - 1 == boardMatrix.length - 1) {
                            xEnd = boardMatrix.length - 1;
                        }
                    }
                }
            }


        } while (placementCondition.equals("error") || overPlacement > 0);

        ship.setX(x1);
        ship.setY(y1);
        ship.setHorizontal(!placementDirection.equalsIgnoreCase("v"));

    }


}



