package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<Ship> ships;
    public static Board playerBoard;
    public static Board pcBoard;

    public static void main(String[] args) {

        ships = loadShips();

        playerBoard = new Board();
        pcBoard = new Board();

        System.out.println("Bienvenido a la Battalla Naval");

        System.out.println("Turno de Jugador para colocar barcos");
        playerBoard.printBoard();

        for (Ship ship : ships) {
            playerBoard.placeShip(ship);
            System.out.println(ship.getName() + " colocado exitosamente.\n");
            playerBoard.printBoard();
        }

        System.out.println("Turno de PC para colocar barcos");
        pcBoard.printBoard();
        for (Ship ship : ships) {
            pcBoard.placeShip(ship);
            System.out.println(ship.getName() + " colocado exitosamente.\n");
            pcBoard.printBoard();
        }


        while (true) {
            System.out.println("Turno Jugador");
            System.out.println("Elige coordenadas para diparar");

            Scanner scanner = new Scanner(System.in);

            System.out.print("X: ");
            int x = Integer.parseInt(scanner.next());
            System.out.print("Y: ");
            int y = Integer.parseInt(scanner.next());

            shoot(x, y, pcBoard);

            System.out.println("Turno PC");
            System.out.println("Elige coordenadas para diparar");

            System.out.print("X: ");
            int r = Integer.parseInt(scanner.next());
            System.out.print("Y: ");
            int s = Integer.parseInt(scanner.next());

            shoot(r, s, playerBoard);


        }
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

    public static void shoot(int x, int y, Board board) {
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

    public static boolean isGameOver() {
        for (Ship ship : ships) {
            if (!ship.isSunk()) {
                return false;
            }
        }
        return true;
    }
}


