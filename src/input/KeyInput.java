package input;


import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Trieda {@code KeyInput} implementuje rozhranie {@code KeyListener} na spracovanie vstupov z klávesnice.
 * Udržuje mapu stavov stlačených kláves pre herné ovládanie.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public class KeyInput extends KeyAdapter {
    private final EnumMap<KeyType, Boolean> keys;

    /**
     * Konštruktor triedy {@code KeyInput}. Inicializuje mapu pre sledovanie stavu kláves.
     */
    public KeyInput() {
        this.keys = new EnumMap<>(KeyType.class);
        this.keys.put(KeyType.DOWN, false);
        this.keys.put(KeyType.UP, false);
        this.keys.put(KeyType.LEFT, false);
        this.keys.put(KeyType.A, false);
        this.keys.put(KeyType.S, false);
        this.keys.put(KeyType.D, false);
        this.keys.put(KeyType.ESC, false);
        this.keys.put(KeyType.RIGHT, false);
        this.keys.put(KeyType.ENTER, false);
    }

    /**
     * Nastavuje stav stlačenej klávesy na {@code true} v mape {@code keys}.
     * @param e Objekt {@code KeyEvent} obsahujúci informácie o stlačenej klávese.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> this.keys.put(KeyType.UP, true);
            case KeyEvent.VK_DOWN -> this.keys.put(KeyType.DOWN, true);
            case KeyEvent.VK_LEFT -> this.keys.put(KeyType.LEFT, true);
            case KeyEvent.VK_RIGHT -> this.keys.put(KeyType.RIGHT, true);
            case KeyEvent.VK_ESCAPE -> this.keys.put(KeyType.ESC, true);
            case KeyEvent.VK_A -> this.keys.put(KeyType.A, true);
            case KeyEvent.VK_S -> this.keys.put(KeyType.S, true);
            case KeyEvent.VK_D -> this.keys.put(KeyType.D, true);
            case KeyEvent.VK_ENTER -> this.keys.put(KeyType.ENTER, true);
        }
    }

    /**
     * Nastavuje stav uvoľnenej klávesy na {@code false} v mape {@code keys}.
     * @param e Objekt {@code KeyEvent} obsahujúci informácie o uvoľnenej klávese.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> this.keys.put(KeyType.UP, false);
            case KeyEvent.VK_DOWN -> this.keys.put(KeyType.DOWN, false);
            case KeyEvent.VK_LEFT -> this.keys.put(KeyType.LEFT, false);
            case KeyEvent.VK_RIGHT -> this.keys.put(KeyType.RIGHT, false);
            case KeyEvent.VK_ESCAPE -> this.keys.put(KeyType.ESC, false);
            case KeyEvent.VK_A -> this.keys.put(KeyType.A, false);
            case KeyEvent.VK_S -> this.keys.put(KeyType.S, false);
            case KeyEvent.VK_D -> this.keys.put(KeyType.D, false);
            case KeyEvent.VK_ENTER -> this.keys.put(KeyType.ENTER, false);
        }
    }



    /**
     * Vráti nemodifikovateľnú mapu aktuálneho stavu stlačených kláves.
     * @return Nemodifikovateľná mapa, kde kľúčom je {@code KeyType} a hodnotou {@code Boolean} indikujúci stlačenie.
     */
    public Map<KeyType, Boolean> getKeys() {
        return Collections.unmodifiableMap(this.keys);
    }
}