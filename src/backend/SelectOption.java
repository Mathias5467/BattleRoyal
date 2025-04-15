package backend;

/**
 * Abstraktná trieda `SelectOption` definuje základnú štruktúru pre výberové možnosti.
 * Poskytuje spoločnú funkčnosť a vyžaduje implementáciu metódy pre spracovanie výberu.
 *  @author Matúš Pytel
 *  @version 15.4.2025
 */
public abstract class SelectOption {
    /**
     * Konštruktor pre triedu `SelectOption`.
     * V súčasnosti nevykonáva žiadnu špecifickú inicializáciu.
     */
    public SelectOption() {
    }

    /**
     * Vráti nezáporný zvyšok po delení čísla `a` číslom `b`.
     *
     * @param a Delené číslo (dividend).
     * @param b Deliteľ (divisor).
     * @return Nezápory zvyšok po delení `a` číslom `b`.
     */
    protected int mod(int a, int b) {
        return (a % b < 0) ? (a % b) + Math.abs(b) : (a % b);
    }

    /**
     * Definuje akciu, ktorá sa vykoná pri výbere možnosti v danom smere.
     *
     * @param direction Celé číslo reprezentujúce smer výberu.
     * Konkrétny význam hodnôt (napr. -1 pre predchádzajúcu, 1 pre nasledujúcu)
     * závisí od implementácie v podtriede.
     */
    public abstract void selectOption(int direction);
}