package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener {
    private boolean upPressed;
    private boolean downPressed;
    private boolean rightPressed;
    private boolean leftPressed;
    private boolean escPressed;
    private boolean mPressed;


    public KeyInput() {
        this.upPressed = false;
        this.downPressed = false;
        this.rightPressed = false;
        this.leftPressed = false;
        this.escPressed = false;
        this.mPressed = false;

    }

    public boolean getKey() {
        return false;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP -> {this.upPressed = true;}
            case KeyEvent.VK_DOWN -> {this.downPressed = true;}
            case KeyEvent.VK_RIGHT -> {this.rightPressed = true;}
            case KeyEvent.VK_LEFT -> {this.leftPressed = true;}
            case KeyEvent.VK_ESCAPE -> {this.escPressed = true;}
            case KeyEvent.VK_M -> {this.mPressed = true;}
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP -> {this.upPressed = false;}
            case KeyEvent.VK_DOWN -> {this.downPressed = false;}
            case KeyEvent.VK_RIGHT -> {this.rightPressed = false;}
            case KeyEvent.VK_LEFT -> {this.leftPressed = false;}
            case KeyEvent.VK_ESCAPE -> {this.escPressed = false;}
            case KeyEvent.VK_M -> {this.mPressed = false;}
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }
}
