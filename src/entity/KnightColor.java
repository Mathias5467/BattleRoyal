package entity;

public enum KnightColor {
    RED("red"),
    GREEN("green"),
    BLUE("blue");

    private String color;

    KnightColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }
}
