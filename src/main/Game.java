package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import input.KeyInput;
import input.KeyType;
import java.awt.Color;
import gui.Menu;

public class Game extends JPanel implements Runnable {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private GameState gameState;
    private Thread gameThread;
    private KeyInput keyInput;
    private boolean running;
    private HashMap<KeyType, Boolean> keysPressed;
    private final int fps = 60;
    private int frameCount = 0;
    private Menu menu;

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(new Color(43, 43, 43));
        this.keyInput = new KeyInput();
        this.addKeyListener(this.keyInput);
        this.menu = new Menu();
        this.gameState = GameState.MENU;
        this.running = false;
        this.keysPressed = new HashMap<KeyType, Boolean>(this.keyInput.getKeys());
    }

    public void start() {
        this.gameState = GameState.MENU;
        this.running = true;
        this.frameCount = 0;
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public void stop() {
        this.gameState = GameState.MENU;
        this.running = false;
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / this.fps;
        long lastFrame = System.nanoTime();
        long now;

        while (this.running) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                this.frameCount++;
                if (this.frameCount >= this.fps) {
                    this.frameCount = 0;
                }

                this.handleInput();
                this.update();
                this.repaint();
                this.keysPressed = new HashMap<KeyType, Boolean>(this.keyInput.getKeys());
                lastFrame = now;
            }
        }
    }

    public void handleInput() {
        for (KeyType keyValue : this.keysPressed.keySet()) {
            if (!this.keysPressed.get(keyValue) && this.keyInput.getKeys().get(keyValue)) {
                if (this.gameState == GameState.MENU) {
                    if (this.keyInput.getKeys().get(KeyType.UP)) {
                        this.menu.goUp();
                    } else if (this.keyInput.getKeys().get(KeyType.DOWN)) {
                        this.menu.goDown();
                    } else if (this.keyInput.getKeys().get(KeyType.ENTER)) {
                        this.gameState = this.menu.getChosenGameState();
                    }
                }

            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (this.gameState) {
            case GameState.PLAY -> {

            }
            case GameState.OPTIONS -> {

            }
            case GameState.EXIT -> {
                System.exit(0);
            }
            case GameState.MENU -> {
                this.menu.draw(g);
            }
        }

    }

    private void update() {

    }
}