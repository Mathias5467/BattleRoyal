package gui.utilities;

/**
 * Enum `Biom` reprezentuje rôzne typy biomov s ich menami a cenami.
 * Implementuje rozhranie `NamedOption` pre možnosť získania biomu podľa mena.
 *
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public enum Biom implements NamedOption<Biom> {
    FOREST("Forest", 0),
    DUNE("Dune", 40),
    MOUNTAIN("Mountain", 80);

    private final String name;
    private final int price;

    /**
     * Konštruktor pre enum `Biom`.
     *
     * @param name  Názov biomu.
     * @param price Cena biomu.
     */
    Biom(String name, int price) {
        this.name = name;
        this.price = price;
    }

    /**
     * Vráti názov tohto biomu.
     *
     * @return Reťazec reprezentujúci názov biomu.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Vráti cenu tohto biomu.
     *
     * @return Celé číslo reprezentujúce cenu biomu.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Implementácia metódy z rozhrania `NamedOption`.
     * Vráti inštanciu enumu `Biom` na základe zadaného názvu.
     *
     * @param option Reťazec reprezentujúci názov hľadaného biomu.
     * @return Inštancia enumu `Biom` s daným názvom, alebo `null` ak sa nenašiel.
     */
    @Override
    public Biom getByName(String option) {
        return switch (option) {
            case "Forest" -> FOREST;
            case "Mountain" -> MOUNTAIN;
            case "Dune" -> DUNE;
            default -> null;
        };
    }
}