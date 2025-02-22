package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import input.KeyInput;
import input.KeyType;

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
    private int seconds = 0;

    public Game() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.keyInput = new KeyInput();
        this.addKeyListener(this.keyInput);
        this.gameState = GameState.MENU;
        this.running = false;
        this.keysPressed = new HashMap<KeyType, Boolean>(this.keyInput.getKeys());
    }

    public void start() {
        this.gameState = GameState.PLAY;
        this.running = true;
        this.frameCount = 0;
        this.seconds = 0;
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    public void stop() {
        this.gameState = GameState.GAME_STATE;
        this.running = false;
    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / this.fps;
        long lastFrame = System.nanoTime();
        long now;

        while (running) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                this.frameCount++;
                if (this.frameCount >= fps) {
                    this.seconds++;
                    this.frameCount = 0;
                }

                this.handleInput();
                this.keysPressed = new HashMap<KeyType, Boolean>(this.keyInput.getKeys());
                this.update();
                this.repaint();
                lastFrame = now;
            }
        }
    }

    private void handleInput() {
        for (KeyType keyValue : this.keysPressed.keySet()) {
            if (!this.keysPressed.get(keyValue) && this.keyInput.getKeys().get(keyValue)) {
                System.out.println(keyValue.getShorthand());
                this.keysPressed.put(keyValue, this.keyInput.getKeys().get(keyValue));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Graphical output
    }

    private void update() {
        // Add any other game update logic here
    }
}