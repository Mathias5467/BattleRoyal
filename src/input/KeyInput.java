package input;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public class KeyInput implements KeyListener {
    private EnumMap<KeyType, Boolean> keys;

    public KeyInput() {
        this.keys = new EnumMap<>(KeyType.class);
        this.keys.put(KeyType.DOWN, false);
        this.keys.put(KeyType.UP, false);
        this.keys.put(KeyType.LEFT, false);
        this.keys.put(KeyType.M, false);
        this.keys.put(KeyType.A, false);
        this.keys.put(KeyType.S, false);
        this.keys.put(KeyType.D, false);
        this.keys.put(KeyType.ESC, false);
        this.keys.put(KeyType.RIGHT, false);
        this.keys.put(KeyType.ENTER, false);
    }



    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                this.keys.put(KeyType.UP, true); }
            case KeyEvent.VK_DOWN -> {
                this.keys.put(KeyType.DOWN, true); }
            case KeyEvent.VK_LEFT -> {
                this.keys.put(KeyType.LEFT, true); }
            case KeyEvent.VK_RIGHT -> {
                this.keys.put(KeyType.RIGHT, true); }
            case KeyEvent.VK_ESCAPE -> {
                this.keys.put(KeyType.ESC, true); }
            case KeyEvent.VK_M -> {
                this.keys.put(KeyType.M, true); }
            case KeyEvent.VK_A -> {
                this.keys.put(KeyType.A, true); }
            case KeyEvent.VK_S -> {
                this.keys.put(KeyType.S, true); }
            case KeyEvent.VK_D -> {
                this.keys.put(KeyType.D, true); }
            case KeyEvent.VK_ENTER -> {
                this.keys.put(KeyType.ENTER, true); }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP -> {
                this.keys.put(KeyType.UP, false); }
            case KeyEvent.VK_DOWN -> {
                this.keys.put(KeyType.DOWN, false); }
            case KeyEvent.VK_LEFT -> {
                this.keys.put(KeyType.LEFT, false); }
            case KeyEvent.VK_RIGHT -> {
                this.keys.put(KeyType.RIGHT, false); }
            case KeyEvent.VK_ESCAPE -> {
                this.keys.put(KeyType.ESC, false); }
            case KeyEvent.VK_M -> {
                this.keys.put(KeyType.M, false); }
            case KeyEvent.VK_A -> {
                this.keys.put(KeyType.A, false); }
            case KeyEvent.VK_S -> {
                this.keys.put(KeyType.S, false); }
            case KeyEvent.VK_D -> {
                this.keys.put(KeyType.D, false); }
            case KeyEvent.VK_ENTER -> {
                this.keys.put(KeyType.ENTER, false); }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


    public Map<KeyType, Boolean> getKeys() {
        return Collections.unmodifiableMap(this.keys);
    }


}
