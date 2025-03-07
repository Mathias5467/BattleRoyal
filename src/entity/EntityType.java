package entity;

public enum EntityType {
    SKELETON("Skeleton"),
    VIKING("Viking"),
    KNIGHT("Knight");

    private String name;

    EntityType(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
