package Game;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    public static ArrayList<Ship> player1Ships;
    public static ArrayList<Ship> player2Ships;
    public static Board player1Board;
    public static Board player2Board;

    public static String player1;
    public static String player2;

    public static void main(String[] args) {

        player1 = "Ariel/Rodrigo";
        player2 = "PC";

        player1Ships = loadShips();
        player2Ships = loadShips();

        player1Board = new Board();
        player2Board = new Board();

        System.out.println("Bienvenido a la Battalla Naval");

        shipPlacementPhase(player1, player1Board, player1Ships);
        shipPlacementPhase(player2, player2Board, player2Ships);

        int turn = 1;
        while (true) {
            if (turn % 2 != 0) {
                takeTurn(player1, player1Board, player1Ships);
                if (isGameOver(player2Ships)) {
                    System.out.printf("%s ha ganado la partida%n", player1);
                    break;
                }
            } else {
                takeTurn(player2, player2Board, player2Ships);
                if (isGameOver(player1Ships)) {
                    System.out.printf("%s ha ganado la partida%n", player2);
                    break;
                }
            }
            turn++;
        }
    }

    public static void shipPlacementPhase(String player, Board board, ArrayList<Ship> ships) {
        System.out.printf("Turno de %s para colocar barcos%n", player);
        board.printBoard();

        for (Ship ship : ships) {
            board.placeShip(ship);
            System.out.println(ship.getName() + " colocado exitosamente.\n");
            board.printBoard();
        }
    }

    public static void takeTurn(String player, Board board, ArrayList<Ship> ships) {
        System.out.printf("Turno %s%n", player);
        System.out.println("Elige coordenadas para diparar");

        Scanner scanner = new Scanner(System.in);

        System.out.print("X: ");
        int x = Integer.parseInt(scanner.next());
        System.out.print("Y: ");
        int y = Integer.parseInt(scanner.next());

        shoot(x, y, board, ships);
        
    }

    public static ArrayList<Ship> loadShips() {
        ArrayList<Ship> ships = new ArrayList<Ship>();

        ships.add(new Ship("Carrier", 5));
        ships.add(new Ship("Battleship", 4));
//        ships.add(new Ship("Cruiser", 3));
//        ships.add(new Ship("Submarine", 3));
//        ships.add(new Ship("Destroyer", 2));

        return ships;
    }

    public static void shoot(int x, int y, Board board, ArrayList<Ship> ships) {
//        Preguntas si es X, O, ~
        if (board.getBoardMatrix()[y][x].equals("*")) {
            boolean hit;
            for (Ship ship : ships) {
                hit = ship.wasShot(x, y);
                if (hit) {
                    System.out.println("Buen disparo, has dado en el blanco.\n");
                    if (ship.isSunk()) {
                        System.out.println("Has hundido el " + ship.getName());
                    }
                    board.setValueOfBoardMatrix(x, y, "X");
                    break;
                }
            }
            System.out.println("Agua");
            board.setValueOfBoardMatrix(x, y, "O");
        }
    }

    public static boolean isGameOver(ArrayList<Ship> ships) {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
}


