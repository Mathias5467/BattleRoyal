package entity;

/**
 * Enum {@code Direction} definuje dva základné smery pohybu alebo orientácie entity v hre:
 * doprava (RIGHT) a doľava (LEFT). Každý smer má priradený svoj symbolický reťazec.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public enum Direction {
    RIGHT("R"),
    LEFT("L");

    private String name;

    /**
     * Konštruktor pre enum {@code Direction}.
     * @param name Symbolický reťazec reprezentujúci daný smer.
     */
    Direction(String name) {
        this.name = name;
    }

    /**
     * Vráti symbolický reťazec priradený k danému smeru.
     * @return Symbolický reťazec smeru.
     */
    @Override
    public String toString() {
        return this.name;
    }
}