package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;

public class Game extends JPanel implements Runnable {
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 700;
    private GameState gameState;
    private Thread gameThread;
    private KeyInput keyInput;
    private boolean running;

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
                    System.out.println("Seconds elapsed: " + this.seconds);
                    this.frameCount = 0;
                }
                this.update();
                this.repaint();
                lastFrame = now;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Time: " + seconds, 20, 40);
    }

    private void update() {
        // Add any other game update logic here
    }
}