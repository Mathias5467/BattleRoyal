package gui.utilities;

/**
 * Enum {@code ConfirmDialog} definuje možnosti pre potvrdzovacie dialógové okná,
 * konkrétne "Áno" a "Nie". Každá konštanta má priradený svoj textový názov.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public enum ConfirmDialog {
    YES("Yes"),
    NO("No");

    private final String name;

    /**
     * Konštruktor pre enum {@code ConfirmDialog}.
     * @param name Textový názov priradený k danej možnosti.
     */
    ConfirmDialog(String name) {
        this.name = name;
    }

    /**
     * Vráti textový názov danej možnosti.
     * @return Textový názov možnosti.
     */
    public String toString() {
        return this.name;
    }
}