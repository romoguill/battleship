package Game;

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

        PcBrain pcBrain = new PcBrain();

        player1 = "Ariel/Rodrigo";
        player2 = "PC";

        player1Ships = loadShips();
        player2Ships = loadShips();

        player1Board = new Board();
        player2Board = new Board();

        System.out.println("Welcome to Battleship");

        shipPlacementPhase(player1, player1Board, player1Ships);
        shipPlacementPhase(player2, player2Board, player2Ships);

        int turn = 1;
        while (true) {
            if (turn % 2 != 0) {
                playerTurn(player2Board, player2Ships);
                if (isGameOver(player2Ships)) {
                    System.out.printf("%s has won!%n", player1);
                    break;
                }
            } else {
                int[] coordinates = pcBrain.useRandomAlgorithm();
                shoot(coordinates[0], coordinates[1], player1Board, player1Ships);
                player1Board.printBoard();
                if (isGameOver(player1Ships)) {
                    System.out.printf("%s has won!%n", player2);
                    break;
                }
            }
            turn++;
        }
    }

    public static void shipPlacementPhase(String player, Board board, ArrayList<Ship> ships) {
        System.out.printf("%s's turn to place ships%n", player);
        board.printBoard();

        for (Ship ship : ships) {
            board.placeShip(ship);
            System.out.println(ship.getName() + " placed successfully.\n");
            board.printBoard();
        }
    }

    public static void playerTurn(Board board, ArrayList<Ship> ships) {
        System.out.printf("%s's turn%n", player1);
        System.out.println("Select coordinates to shoot");

        Scanner scanner = new Scanner(System.in);

        System.out.print("X: ");
        int x = Integer.parseInt(scanner.next());
        System.out.print("Y: ");
        int y = Integer.parseInt(scanner.next());

        shoot(x, y, board, ships);

    }

    public static ArrayList<Ship> loadShips() {
        ArrayList<Ship> ships = new ArrayList<>();

        ships.add(new Ship("Carrier", 5));
        ships.add(new Ship("Battleship", 4));
//        ships.add(new Ship("Cruiser", 3));
//        ships.add(new Ship("Submarine", 3));
//        ships.add(new Ship("Destroyer", 2));

        return ships;
    }

    public static void shoot(int x, int y, Board board, ArrayList<Ship> ships) {
        if (board.getBoardMatrix()[y][x].equals("*")) {
            boolean hit;
            for (Ship ship : ships) {
                hit = ship.wasShot(x, y);
                if (hit) {
                    System.out.println("Nice aim, you hit a ship.\n");
                    if (ship.isSunk()) {
                        System.out.println("Great! You sunk a ship");
                    }
                    board.setValueOfBoardMatrix(x, y, "X");
                    break;
                }
            }
            System.out.println("Water");
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


