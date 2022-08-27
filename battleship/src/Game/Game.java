package Game;

import java.util.ArrayList;
import java.util.Scanner;

public class Game extends GameConfig {
    public static ArrayList<Ship> player1Ships;
    public static ArrayList<Ship> player2Ships;
    public static Board player1Board;
    public static Board player2Board;

    public static String player1;
    public static String player2;

    public static void main(String[] args) {
        renderWelcome();
    }

    public static void renderWelcome() {
        System.out.println("\nWelcome to Battleship Game");
        System.out.println("1. Play");
        System.out.println("2. Configure");
        System.out.println("3. Quit\n");


        Scanner scanner = new Scanner(System.in);

        System.out.print("Select option: ");

        int input;
        do {
            input = scanner.nextInt();
        } while (input != 1 && input != 2 && input != 3);

        if (input == 1) {
            Game.startGame();
        } else if (input == 2) {
            Game.configure();
        } else {
            System.exit(0);
        }
    }

    public static void configure() {
        System.out.println("\nGame Configuration");
        System.out.println("1. Change name. Current: " + GameConfig.getPlayerName());
        System.out.println("2. Set difficulty. Current: " + GameConfig.getDifficulty());
        System.out.println("3. Back\n");

        System.out.print("Select option: ");


        Scanner scanner = new Scanner(System.in);

        int input;
        do {
            input = scanner.nextInt();
        } while (input != 1 && input != 2 && input != 3);

        if (input == 1) {
            scanner.nextLine();
            System.out.print("\nEnter your name: ");

            GameConfig.setPlayerName(scanner.nextLine());
        } else if (input == 2) {
            System.out.println("\nSelect difficulty");
            System.out.println("1. Easy");
            System.out.println("2. Hard");
            System.out.println("3. Ironman");

            System.out.print("Select option: ");

            int difficulty;
            do {
                difficulty = scanner.nextInt();
            } while (difficulty != 1 && difficulty != 2 && difficulty != 3);

            if (difficulty == 1) {
                GameConfig.setDifficulty(Difficulty.EASY);
            } else if (difficulty == 2) {
                GameConfig.setDifficulty(Difficulty.HARD);
            } else {
                GameConfig.setDifficulty(Difficulty.IRONMAN);
            }
        } else {
            renderWelcome();
        }
        renderWelcome();
    }

    public static void startGame() {
        PcBrain pcBrain = new PcBrain();

        player1 = Game.getPlayerName();
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
                player2Board.printBoard();
            } else {
                Coordinate pcChoice = null;
                if (GameConfig.getDifficulty() == Difficulty.EASY) {
                    pcChoice = pcBrain.useRandomAlgorithm();
                } else if (GameConfig.getDifficulty() == Difficulty.HARD) {
                    pcChoice = pcBrain.useTrackingAlgorithm();
                } else {
                    System.out.println("Difficulty not yet implemented");
                    System.exit(0);
                }

                int x = pcChoice.getX();
                int y = pcChoice.getY();


                Coordinate coordinate = new Coordinate(x, y);

                BoardValue result = shoot(coordinate, player1Board, player1Ships);
                if (GameConfig.getDifficulty() == Difficulty.HARD) {
                    pcBrain.processShotResult(coordinate, result);
                    player1Board.printBoard();
                }
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
            boolean wasPlaced = false;
            while (!wasPlaced) {
                Scanner scanner = new Scanner(System.in);

                String orientationInput;

                while (true) {
                    System.out.print("Choose Orientation, V / H: ");
                    orientationInput = scanner.nextLine();
                    if (!orientationInput.equalsIgnoreCase("V") && !orientationInput.equalsIgnoreCase("H")) {
                        System.out.println("Invalid orientation\n");
                        continue;
                    }
                    break;
                }

                Orientation orientation = orientationInput.equalsIgnoreCase("V") ? Orientation.VERTICAL : Orientation.HORIZONTAL;

                System.out.println("Choose ship's origin coordinate");
                System.out.print("X: ");
                int xInput = scanner.nextInt();
                System.out.print("Y: ");
                int yInput = scanner.nextInt();

                wasPlaced = board.placeShip2(new Coordinate(xInput, yInput), ship, orientation);
                if (!wasPlaced) System.out.println("Couldn't place ship, invalid/already taken coordinates\n");
            }
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

        shoot(new Coordinate(x, y), board, ships);

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

    public static BoardValue shoot(Coordinate coordinate, Board board, ArrayList<Ship> ships) {
        boolean hit;
        for (Ship ship : ships) {
            hit = ship.wasShot(coordinate);
            if (hit) {
                System.out.println("Nice aim, you hit a ship.\n");
                if (ship.isSunk()) {
                    System.out.println("Great! You sunk a ship");
                    return BoardValue.SUNK;
                }
                board.setValueOfBoardMatrix(coordinate.getX(), coordinate.getY(), "H");
                return BoardValue.HIT;
            }
        }


        System.out.println("Water");
        board.setValueOfBoardMatrix(coordinate.getX(), coordinate.getY(), "W");
        return BoardValue.WATER;
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


