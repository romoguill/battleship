package Game;

import java.util.Scanner;
import java.util.stream.IntStream;

public class Ship {
    private String name;
    private int size;
    private int shotsTaken = 0;
    private int x;
    private int y;
    private boolean horizontal;
    private boolean sunk = false;


    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public boolean wasShot(int x, int y) {
        boolean wasReached = false;
        if (this.horizontal) {
            wasReached = IntStream.range(this.x, this.x + this.size).anyMatch(num -> num == x);
        } else {
            wasReached = IntStream.range(this.y, this.y + this.size).anyMatch(num -> num == y);
        }

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

    public void setSize(int size) {
        this.size = size;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isHorizontal() {
        return horizontal;
    }

    public void setHorizontal(boolean horizontal) {
        this.horizontal = horizontal;
    }

    public boolean isSunk() {
        return sunk;
    }
}







