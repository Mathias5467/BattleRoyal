package entity;

public enum KnightType {
    RED("red", "Thorne", 100, 50, 40, 0),
    GREEN("green", "Alaric", 100, 90, 70, 50),
    BLUE("blue", "Rhogar", 100, 75, 85, 30);

    private String color;
    private String name;
    private int hp;
    private int attack;
    private int defend;
    private int price;
    KnightType(String color, String name, int hp, int attack, int defend, int price) {
        this.color = color;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defend = defend;
        this.price = price;
    }

    public KnightType getBasedColor(String color) {
        switch (color) {
            case "red" -> {
                return KnightType.RED;
            }
            case "green" -> {
                return KnightType.GREEN;
            }
            case "blue" -> {
                return KnightType.BLUE;
            }
        }
        return null;
    }

    public int getPrice() {
        return this.price;
    }

    public String getColor() {
        return this.color;
    }

    public String getName() {
        return this.name;
    }

    public int getHp() {
        return this.hp;
    }

    public int getAttack() {
        return this.attack;
    }

    public int getDefend() {
        return this.defend;
    }
}
