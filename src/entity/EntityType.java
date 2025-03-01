package entity;

public enum EntityType {
    SKELETON("skeleton"),
    VIKING("viking"),
    KNIGHT("knight");

    private String name;

    EntityType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
