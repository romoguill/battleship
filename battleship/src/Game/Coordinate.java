package Game;

import java.awt.*;
import java.util.Objects;

public class Coordinate {
    private int x;
    private int y;
    private char value;

    public Coordinate(int x, int y, char value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return x == that.x && y == that.y && value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, value);
    }
}
