package entity;

import backend.NamedOption;

public enum KnightType implements NamedOption<KnightType> {
    RED("red", "Thorne", 100, 50, 40, 0),
    GREEN("green", "Alaric", 100, 90, 70, 50),
    BLUE("blue", "Aragon", 100, 75, 85, 30);

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


    @Override
    public KnightType getByName(String option) {
        return switch (option) {
            case "Thorne" -> KnightType.RED;
            case "Alaric" -> KnightType.GREEN;
            case "Aragon" -> KnightType.BLUE;
            default -> null;
        };
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

    public int getDefence() {
        return this.defend;
    }
}