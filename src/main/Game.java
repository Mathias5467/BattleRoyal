package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.util.HashMap;
import java.util.Scanner;

import entity.KnightType;
import entity.Movement;
import gui.*;

import input.KeyInput;
import input.KeyType;

public class Game extends JPanel implements Runnable {
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 700;
    private GameState gameState;
    private Thread gameThread;
    private KeyInput keyInput;
    private boolean running;
    private HashMap<KeyType, Boolean> keysPressed;
    private final int fps = 60;
    private int frameCount = 0;
    private Menu menu;
    private Options options;
    private Dialog dialog;
    private KnightType knightType;
    private Map map;
    private boolean nonKeyTyped;
    private int numberOfCoins;
    private boolean coinAdded;
    public Game() throws FileNotFoundException {
        this.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(new Color(43, 43, 43));
        this.keyInput = new KeyInput();
        this.addKeyListener(this.keyInput);
        this.menu = new Menu();
        this.options = new Options();
        this.map = new Map();
        this.gameState = GameState.MENU;
        this.running = false;
        this.keysPressed = new HashMap<>(this.keyInput.getKeys());
        this.knightType = this.options.getKnightType();
        this.dialog = new Dialog(this.gameState);
        this.nonKeyTyped = false;
        this.numberOfCoins = 0;
        this.coinAdded = true;
    }

    public void start() throws FileNotFoundException {
        this.running = true;
        this.frameCount = 0;
        this.gameThread = new Thread(this);
        this.gameThread.start();
        File coinsFile = new File("res/data/coins.txt");
        Scanner input = new Scanner(coinsFile);
        this.numberOfCoins = input.nextInt();
        input.close();
        this.options.setNumberOfCoins(this.numberOfCoins);
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

                try {
                    this.handleInput();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                this.update();
                this.repaint();
                if (this.gameState != GameState.PLAY || this.dialog.isVisible()) {
                    this.keysPressed = new HashMap<KeyType, Boolean>(this.keyInput.getKeys());
                }
                lastFrame = now;
            }
        }
    }

    public void handleInput() throws IOException {
        var numberOfPressedKeys = 0;
        for (KeyType keyValue : this.keysPressed.keySet()) {
            if (numberOfPressedKeys < 1) {
                if (!this.keysPressed.get(keyValue) && this.keyInput.getKeys().get(keyValue)) {
                    numberOfPressedKeys++;
                    this.nonKeyTyped = true;
                    if (!this.dialog.isVisible()) {
                        if (this.gameState == GameState.MENU) {
                            if (this.keyInput.getKeys().get(KeyType.UP)) {
                                this.menu.selectOption(-1);
                            } else if (this.keyInput.getKeys().get(KeyType.DOWN)) {
                                this.menu.selectOption(1);
                            } else if (this.keyInput.getKeys().get(KeyType.ENTER)) {
                                this.gameState = this.menu.getChosenGameState();
                                if (this.gameState == GameState.PLAY) {
                                    this.map.reset();
                                    this.dialog.setPlayState(PlayState.TIE);
                                } else if (this.gameState == GameState.EXIT) {
                                    File coinFile = new File("res/data/coins.txt");
                                    PrintWriter input = new PrintWriter(coinFile);
                                    input.println(this.numberOfCoins); // This will correctly write the number as text
                                    input.close();
                                    this.options.writeIntoFile();
                                }
                            }
                        } else if (this.gameState == GameState.OPTIONS) {
                            if (this.keyInput.getKeys().get(KeyType.LEFT)) {
                                this.options.changeColor(-1);
                            } else if (this.keyInput.getKeys().get(KeyType.RIGHT)) {
                                this.options.changeColor(1);
                            } else if (this.keyInput.getKeys().get(KeyType.ENTER)) {
                                if (this.options.tryChoose()) {
                                    this.gameState = GameState.MENU;
                                    this.knightType = this.options.getKnightType();
                                    this.map.changeKnight(this.knightType);
                                }
                            }
                        }
                        if (this.gameState == GameState.PLAY) {
                            if (!this.dialog.isVisible()) {
                                if (this.keyInput.getKeys().get(KeyType.LEFT)) {
                                    this.map.moveLeft();
                                } else if (this.keyInput.getKeys().get(KeyType.RIGHT)) {
                                    this.map.moveRight();
                                } else if (this.keyInput.getKeys().get(KeyType.DOWN)) {
                                    this.map.defend();
                                } else if (this.keyInput.getKeys().get(KeyType.A)) {
                                    this.map.getPlayer().attack(Movement.ATTACK1);
                                } else if (this.keyInput.getKeys().get(KeyType.S)) {
                                    this.map.getPlayer().attack(Movement.ATTACK2);
                                } else if (this.keyInput.getKeys().get(KeyType.D)) {
                                    this.map.getPlayer().attack(Movement.ATTACK3);
                                }

                            }
                        }
                        if (this.keyInput.getKeys().get(KeyType.ESC) && this.gameState != GameState.MENU) {
                            this.dialog.setVisible();
                        }
                    } else {
                        if (this.keyInput.getKeys().get(KeyType.LEFT)) {
                            this.dialog.changeOption(0);
                        } else if (this.keyInput.getKeys().get(KeyType.RIGHT)) {
                            this.dialog.changeOption(1);
                        } else if (this.keyInput.getKeys().get(KeyType.ESC)) {
                            this.dialog.hide();
                        } else if (this.keyInput.getKeys().get(KeyType.ENTER)) {
                            this.dialog.setConfirmed(true);
                        }
                    }
                }
            }
        }
        if (!this.nonKeyTyped && this.map.getPlayer().mayStop()) {
            this.map.stop();
        }
        this.nonKeyTyped = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (this.gameState) {
            case GameState.PLAY -> {
                if (this.dialog.isConfirmed()) {
                    if (this.dialog.getChosenOption().equals(MessageType.EXIT.getOk())) {
                        this.gameState = GameState.MENU;
                    }
                    this.dialog.hide();
                }
                this.dialog.setConfirmed(false);

                this.map.draw(g);
            }
            case GameState.EXIT -> {
                System.exit(0);
            }
            case GameState.OPTIONS -> {
                if (this.dialog.isConfirmed()) {
                    if (this.dialog.getChosenOption().equals(MessageType.EXIT.getOk())) {
                        this.gameState = GameState.MENU;
                    }
                    this.dialog.hide();
                }
                this.dialog.setConfirmed(false);

                this.options.draw(g);
            }
            case GameState.MENU -> {
                this.dialog.setPlayState(PlayState.TIE);
                this.menu.draw(g);
            }
        }
        if (this.dialog.isVisible()) {
            this.dialog.draw(g);
        }
    }

    private void update() {
        // Update player's attack animation if it's playing
        if (this.gameState == GameState.PLAY && !this.dialog.isVisible()) {
            this.map.update();
            if (this.map.getCurrentEnemy().isDead() && !this.coinAdded) {
                this.numberOfCoins++;
                this.options.setNumberOfCoins(this.numberOfCoins);
                this.coinAdded = true;
            }
            // Reset the coinAdded flag when moving to a new enemy
            if (!this.map.getCurrentEnemy().isDead() && this.coinAdded) {
                this.coinAdded = false;
            }
            if (!this.map.anotherEnemy()) {
                this.dialog.setPlayState(PlayState.WIN);
                this.dialog.setVisible();
            } else if (this.map.getPlayer().isDead()) {
                this.dialog.setPlayState(PlayState.LOST);
                this.dialog.setVisible();
            }
        }
    }
}