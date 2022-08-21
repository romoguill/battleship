package Game;

public enum BoardValue {
    UNKNOWN("~"),
    SHIP("*"),
    WATER("W"),
    HIT("H"),
    SUNK("S");

    public final String label;

    BoardValue(String label) {
        this.label = label;
    }
}
