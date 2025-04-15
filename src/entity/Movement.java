package entity;

/**
 * Enum {@code Movement} definuje rôzne typy pohybov a akcií, ktoré môžu entity v hre vykonávať.
 * Každá konštanta má priradený svoj symbolický reťazec pre potreby animácií a stavov.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public enum Movement {
    STAY("stay"),
    WALK("walk"),
    ATTACK1("attack1"),
    ATTACK2("attack2"),
    ATTACK3("attack3"),
    DEFEND("defend"),
    DEATH("death"),
    DYING("dying");


    private String symbol;

    /**
     * Konštruktor pre enum {@code Movement}.
     * @param rep Symbolický reťazec reprezentujúci daný typ pohybu alebo akcie.
     */
    Movement(String rep) {
        this.symbol = rep;
    }

    /**
     * Vráti symbolický reťazec priradený k danému typu pohybu alebo akcie.
     * @return Symbolický reťazec.
     */
    public String getSymbol() {
        return this.symbol;
    }
}