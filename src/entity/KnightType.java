package entity;

public enum KnightType {
    RED("red", "Thorne", 100, 90, 70),
    GREEN("green", "Alaric", 100, 50, 40),
    BLUE("blue", "Rhogar", 100, 50, 85);

    private String color;
    private String name;
    private int hp;
    private int attack;
    private int defend;

    KnightType(String color, String name, int hp, int attack, int defend) {
        this.color = color;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defend = defend;
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
