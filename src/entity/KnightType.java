package entity;

public enum KnightType {
    RED("red", "Thorne", 100, 100, 40, 0, true),
    GREEN("green", "Alaric", 100, 90, 70, 50, false),
    BLUE("blue", "Rhogar", 100, 50, 85, 30, false);

    private String color;
    private String name;
    private int hp;
    private int attack;
    private int defend;
    private int price;
    private boolean bought;
    KnightType(String color, String name, int hp, int attack, int defend, int price, boolean bought) {
        this.color = color;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defend = defend;
        this.price = price;
        this.bought = bought;
    }

    public int getPrice() {
        return this.price;
    }

    public boolean isBought() {
        return this.bought;
    }
    public void  setBought() {
        this.bought = true;
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
