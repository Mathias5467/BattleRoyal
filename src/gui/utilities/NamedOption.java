package gui.utilities;

/**
 * Rozhranie `NamedOption` definuje kontrakt pre objekty, ktoré majú meno, cenu a umožňujú vyhľadávanie podľa mena.
 * Typ `T` predstavuje typ objektu, ktorý sa získava pomocou mena.
 *
 * @param <T> Typ objektu, ktorý sa dá získať pomocou mena.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public interface NamedOption<T> {
    /**
     * Vráti objekt typu `T`, ktorého meno zodpovedá zadanému reťazcu `option`.
     *
     * @param option Reťazec reprezentujúci meno hľadaného objektu.
     * @return Objekt typu `T` s daným menom, alebo `null` ak sa nenašiel.
     */
    T getByName(String option);

    /**
     * Vráti meno tohto objektu.
     *
     * @return Reťazec reprezentujúci meno objektu.
     */
    String getName();

    /**
     * Vráti cenu tohto objektu.
     *
     * @return Celé číslo reprezentujúce cenu objektu.
     */
    int getPrice();
}