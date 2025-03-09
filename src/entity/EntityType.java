package entity;

public enum EntityType {
    SKELETON("Skeleton"),
    VIKING("Viking"),
    SOLDIER("Soldier"),
    MONSTER("Monster"),
    KNIGHT("Knight");

    private String name;

    EntityType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
