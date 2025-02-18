package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;

public class Game extends JPanel implements Runnable {
    private static final int width = 1000;
    private static final int height = 700;
    private Thread gameThread;
    private KeyInput keyInput;
    public Game() {
        this.setPreferredSize(new Dimension(Game.width, Game.height));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.keyInput = new KeyInput();
        this.addKeyListener(this.keyInput);
    }

    public void startGame() {
        this.gameThread = new Thread(this);
        this.gameThread.start();
    }

    @Override
    public void run() {
        while(this.gameThread != null) {
            //doriešiť FPS 60
        }
    }

    public void paint(Graphics g) {
        super.paintComponent(g);

    }

    public void update() {

    }
}
