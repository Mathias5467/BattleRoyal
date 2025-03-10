package entity;

public enum EntityType {
    SKELETON("Skeleton", 5),
    VIKING("Viking", 6),
    SOLDIER("Soldier", 7),
    MONSTER("Monster", 8),
    KNIGHT("Knight", 0);

    private String name;
    private int attack;
    EntityType(String name, int attack) {
        this.name = name;
        this.attack = attack;
    }

    public String toString() {
        return this.name;
    }

    public int getAttack() {
        return this.attack;
    }
}
