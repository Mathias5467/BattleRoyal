package entity.utilities;

/**
 * Enum {@code EntityType} definuje rôzne typy entít, ktoré sa môžu v hre vyskytovať,
 * ako sú nepriatelia (kostlivec, viking, vojak, monštrum) a hráč (rytier).
 * Každý typ má priradený svoj názov a základnú silu útoku.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public enum EntityType {
    SKELETON("Skeleton", 5),
    VIKING("Viking", 6),
    SOLDIER("Soldier", 7),
    MONSTER("Monster", 8),
    KNIGHT("Knight", 0);

    private final String name;
    private final int attack;

    /**
     * Konštruktor pre enum {@code EntityType}.
     * @param name Názov typu entity.
     * @param attack Základná sila útoku entity.
     */
    EntityType(String name, int attack) {
        this.name = name;
        this.attack = attack;
    }

    /**
     * Vráti názov typu entity.
     * @return Názov typu entity.
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Vráti základnú silu útoku entity.
     * @return Základná sila útoku entity.
     */
    public int getAttack() {
        return this.attack;
    }

    /**
     * Vráti inštanciu {@code EntityType} na základe zadaného názvu.
     * @param name Názov hľadaného typu entity.
     * @return Inštancia {@code EntityType} s daným názvom, alebo {@code null} ak sa nenájde.
     */
    public static EntityType getByName(String name) {
        return switch (name.toLowerCase()) {
            case "skeleton" -> EntityType.SKELETON;
            case "viking" -> EntityType.VIKING;
            case "soldier" -> EntityType.SOLDIER;
            case "monster" -> EntityType.MONSTER;
            default -> null;
        };
    }
}