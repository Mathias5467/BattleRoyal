package entity;

public enum EntityType {
    SKELETON("Skeleton", 5),
    VIKING("Viking", 6),
    SOLDIER("Soldier", 7),
    MONSTER("Monster", 8),
    KNIGHT("Knight", 0);

    private final String name;
    private final int attack;
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

    public EntityType getByName(String name) {
        return switch (name) {
            case "skeleton" -> EntityType.SKELETON;
            case "viking" -> EntityType.VIKING;
            case "soldier" -> EntityType.SOLDIER;
            case "monster" -> EntityType.MONSTER;
            default -> null;
        };
    }
}
