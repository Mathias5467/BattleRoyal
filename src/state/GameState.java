package state;

/**
 * Enum {@code GameState} definuje rôzne stavy, v ktorých sa hra môže nachádzať.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public enum GameState {
    MENU("Menu"),
    PLAY("Play"),
    EXIT("Exit"),
    KNIGHTS("Knights"),
    MAPS("Map");

    private final String name;

    /**
     * Konštruktor pre enum {@code GameState}.
     * @param name Názov priradený k danému stavu hry.
     */
    GameState(String name) {
        this.name = name;
    }

    /**
     * Vráti názov daného stavu hry.
     * @return Názov stavu hry.
     */
    public String getName() {
        return this.name;
    }
}