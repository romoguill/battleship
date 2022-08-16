package Game;

import java.util.ArrayList;

public class Ship {
    private String name;
    private int size;
    private int shotsTaken = 0;
    private Orientation orientation;
    private boolean sunk = false;
    private ArrayList<Coordinate> shipCoordinates = new ArrayList<>();


    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public boolean wasShot(Coordinate coordinate) {
        boolean wasReached = false;
        wasReached = this.shipCoordinates.stream().anyMatch(coordinate::equals);

        if (wasReached) {
            this.shotsTaken++;
            if (this.shotsTaken == this.size) {
                this.sunk = true;
            }
            return true;
        }
        return false;
    }

    public boolean wasSunk() {
        return this.shotsTaken == this.size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public int getShotsTaken() {
        return shotsTaken;
    }

    public void setShotsTaken(int shotsTaken) {
        this.shotsTaken = shotsTaken;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setSunk(boolean sunk) {
        this.sunk = sunk;
    }

    public ArrayList<Coordinate> getShipCoordinates() {
        return shipCoordinates;
    }

    public void setShipCoordinates(ArrayList<Coordinate> shipCoordinates) {
        this.shipCoordinates = shipCoordinates;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isSunk() {
        return sunk;
    }
}







