package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

import entity.KnightType;
import entity.Movement;
import gui.Dialog;
import gui.Options;
import gui.Pause;
import gui.Map;
import gui.Menu;
import gui.ConfirmDialog;

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
    private Pause pause;
    private Dialog dialog;
    private KnightType knightType;
    private Map map;
    private boolean nonKeyTyped;
    public Game() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(new Color(43, 43, 43));
        this.keyInput = new KeyInput();
        this.addKeyListener(this.keyInput);
        this.menu = new Menu();
        this.options = new Options();
        this.gameState = GameState.MENU;
        this.running = false;
        this.keysPressed = new HashMap<KeyType, Boolean>(this.keyInput.getKeys());
        this.knightType = this.options.getKnightType();
        this.pause = new Pause();
        this.map = new Map();
        this.dialog = new Dialog(this.gameState);
        this.nonKeyTyped = false;
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
                if (this.gameState != GameState.PLAY || this.dialog.isVisible()) {
                    this.keysPressed = new HashMap<KeyType, Boolean>(this.keyInput.getKeys());
                }
                lastFrame = now;
            }
        }
    }

    public void handleInput() {
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
                            }
                        } else if (this.gameState == GameState.OPTIONS) {
                            if (this.keyInput.getKeys().get(KeyType.LEFT)) {
                                this.options.changeColor(-1);
                            } else if (this.keyInput.getKeys().get(KeyType.RIGHT)) {
                                this.options.changeColor(1);
                            } else if (this.keyInput.getKeys().get(KeyType.ENTER)) {
                                this.gameState = GameState.MENU;
                                this.knightType = this.options.getKnightType();
                                this.map.getPlayer().setKnight(this.knightType);
                                this.map.getPlayer().setStartPosition();// improve this and restart the level when added
                            } else if (this.keyInput.getKeys().get(KeyType.ESC)) {
                                this.dialog.setVisible();
                            }
                        }
                        if (this.gameState == GameState.PLAY) {
                            if (!this.dialog.isVisible()) {
                                if (this.keyInput.getKeys().get(KeyType.LEFT)) {
                                    this.map.moveLeft();
                                } else if (this.keyInput.getKeys().get(KeyType.RIGHT)) {
                                    this.map.moveRight();
                                } else if (this.keyInput.getKeys().get(KeyType.ESC)) {
                                    this.dialog.setVisible();
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
        if (!this.nonKeyTyped && !this.map.getPlayer().isDying() && !this.map.getPlayer().isDead() && !this.map.getPlayer().isAttacking() && this.gameState == GameState.PLAY) {
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
                    if (this.dialog.getChosenOption() == ConfirmDialog.YES) {
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
                    if (this.dialog.getChosenOption() == ConfirmDialog.YES) {
                        this.gameState = GameState.MENU;
                    }
                    this.dialog.hide();
                }
                this.dialog.setConfirmed(false);

                this.options.draw(g);
            }
            case GameState.MENU -> {
                this.menu.draw(g);
            }
            case GameState.PAUSE -> {
                this.dialog.draw(g);
            }
        }
        if (this.dialog.isVisible()) {
            this.dialog.draw(g);
        }
    }

    private void update() {
        // Update player's attack animation if it's playing
        if (this.gameState == GameState.PLAY) {
            this.map.update();
        }
    }
}