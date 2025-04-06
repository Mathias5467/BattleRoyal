package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import entity.KnightType;
import entity.Movement;
import gui.*;

import input.KeyInput;
import input.KeyType;
import backend.Biom;
public class Game extends JPanel implements Runnable {
    private static final int WIDTH = 1100;
    private static final int HEIGHT = 700;
    private GameState gameState;
    private Thread gameThread;
    private final KeyInput keyInput;
    private boolean running;
    private Map<KeyType, Boolean> keysPressedReaction;
    private int frameCount = 0;
    private final Menu menu;
    private final KnightOptions knightOption;
    private Biom biom;
    private final MapOptions mapOption;
    private final Dialog dialog;
    private KnightType knightType;
    private final Play play;
    private int numberOfCoins;
    public Game() throws FileNotFoundException {
        this.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(new Color(43, 43, 43));
        this.keyInput = new KeyInput();
        this.addKeyListener(this.keyInput);
        File coinsFile = new File("res/data/coins.txt");
        Scanner input = new Scanner(coinsFile);
        this.numberOfCoins = input.nextInt();
        input.close();
        this.menu = new Menu();
        this.knightOption = new KnightOptions(this.numberOfCoins);
        this.mapOption = new MapOptions(this.numberOfCoins);
        this.biom = Biom.FOREST;
        this.play = new Play();
        this.gameState = GameState.MENU;
        this.running = false;
        this.keysPressedReaction = new HashMap<>(this.keyInput.getKeys());
        this.knightType = this.knightOption.getOption();
        this.dialog = new Dialog();

    }

    public void start() {
        this.running = true;
        this.frameCount = 0;
        this.gameThread = new Thread(this);
        this.gameThread.start();
        this.knightOption.setNumberOfCoins(this.numberOfCoins);
        this.mapOption.setNumberOfCoins(this.numberOfCoins);
    }

    @Override
    public void run() {
        var fps = 60;
        double timePerFrame = 1000000000.0 / fps;
        long lastFrame = System.nanoTime();
        long now;

        while (this.running) {
            now = System.nanoTime();
            if (now - lastFrame >= timePerFrame) {
                this.frameCount++;
                if (this.frameCount >= fps) {
                    this.frameCount = 0;
                }
                try {
                    this.handleInput();
                } catch (FileNotFoundException e) {
                    System.out.println("System file is missing");
                }
                this.update();
                this.repaint();
                if (this.gameState != GameState.PLAY || this.dialog.isVisible()) {
                    this.keysPressedReaction = new HashMap<>(this.keyInput.getKeys());
                }
                lastFrame = now;
            }
        }
    }

    private void handleMenu(Map<KeyType, Boolean> pressed) throws FileNotFoundException {
        if (pressed.get(KeyType.UP)) {
            this.menu.selectOption(-1);
        } else if (pressed.get(KeyType.DOWN)) {
            this.menu.selectOption(1);
        } else if (pressed.get(KeyType.ENTER)) {
            this.gameState = this.menu.getChosenGameState();
            if (this.gameState == GameState.PLAY) {
                this.play.reset();
                this.play.setBiom(this.biom);
                this.dialog.setPlayState(PlayState.TIE);
            } else if (this.gameState == GameState.EXIT) {
                File coinFile = new File("res/data/coins.txt");
                PrintWriter input = new PrintWriter(coinFile);
                input.println(this.numberOfCoins);
                input.close();
                this.knightOption.writeIntoFile();
                this.mapOption.writeIntoFile();
            }
        }
    }

    private void handleKnightOptions(Map<KeyType, Boolean> pressed) {
        if (pressed.get(KeyType.LEFT)) {
            this.knightOption.selectOption(-1);
        } else if (pressed.get(KeyType.RIGHT)) {
            this.knightOption.selectOption(1);
        } else if (pressed.get(KeyType.ENTER)) {
            if (this.knightOption.tryChoose()) {
                this.gameState = GameState.MENU;
                this.knightType = this.knightOption.getOption();
                this.play.getPlayer().setKnight(this.knightType);
            } else if (this.knightOption.tryBuy()) {
                this.numberOfCoins = this.knightOption.getNumberOfCoins();
            }
        }
    }

    private void handleMapOptions(Map<KeyType, Boolean> pressed) {
        if (pressed.get(KeyType.LEFT)) {
            this.mapOption.selectOption(-1);
        } else if (pressed.get(KeyType.RIGHT)) {
            this.mapOption.selectOption(1);
        }  else if (pressed.get(KeyType.ENTER)) {
            if (this.mapOption.tryChoose()) {
                this.gameState = GameState.MENU;
                this.biom = this.mapOption.getOption();
            } else if (this.mapOption.tryBuy()) {
                this.numberOfCoins = this.mapOption.getNumberOfCoins();
            }
        }
    }

    private void handlePlay(Map<KeyType, Boolean> pressed) {
        if (!this.dialog.isVisible()) {
            if (pressed.get(KeyType.LEFT)) {
                this.play.moveLeft();
            } else if (pressed.get(KeyType.RIGHT)) {
                this.play.moveRight();
            } else if (pressed.get(KeyType.DOWN)) {
                this.play.getPlayer().defend();
            } else if (pressed.get(KeyType.A)) {
                this.play.getPlayer().attack(Movement.ATTACK1);
            } else if (pressed.get(KeyType.S)) {
                this.play.getPlayer().attack(Movement.ATTACK2);
            } else if (pressed.get(KeyType.D)) {
                this.play.getPlayer().attack(Movement.ATTACK3);
            }
        }
    }

    private void handleDialog(Map<KeyType, Boolean> pressed) {
        if (pressed.get(KeyType.LEFT)) {
            this.dialog.selectOption(-1);
        } else if (pressed.get(KeyType.RIGHT)) {
            this.dialog.selectOption(1);
        } else if (pressed.get(KeyType.ESC)) {
            this.dialog.setVisible(false);
        } else if (pressed.get(KeyType.ENTER)) {
            if (this.dialog.getChosenOption() == ConfirmDialog.YES) {
                if (this.gameState == GameState.PLAY) {
                    this.numberOfCoins += this.play.getNumberOfCoins();
                    this.knightOption.setNumberOfCoins(this.numberOfCoins);
                    this.mapOption.setNumberOfCoins(this.numberOfCoins);
                }
                this.gameState = GameState.MENU;
                this.dialog.setPlayState(PlayState.TIE);
            }
            this.dialog.setVisible(false);
        }
    }

    public void handleInput() throws FileNotFoundException {
        var alreadyPressed = this.keyInput.getKeys();
        var keyTyped = false;
        for (KeyType keyValue : this.keysPressedReaction.keySet()) {
            if (!this.keysPressedReaction.get(keyValue) && alreadyPressed.get(keyValue)) {
                keyTyped = true;
                if (!this.dialog.isVisible()) {
                    switch (this.gameState) {
                        case MENU -> this.handleMenu(alreadyPressed);
                        case KNIGHTS -> this.handleKnightOptions(alreadyPressed);
                        case PLAY -> this.handlePlay(alreadyPressed);
                        case MAPS -> this.handleMapOptions(alreadyPressed);
                    }
                    if (alreadyPressed.get(KeyType.ESC) && this.gameState != GameState.MENU) {
                        this.dialog.setVisible(true);
                    }
                } else {
                    this.handleDialog(alreadyPressed);
                }
                break;
            }
        }
        if (!keyTyped && this.play.getPlayer().mayStop()) {
            this.play.getPlayer().stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (this.gameState) {
            case PLAY -> this.play.draw(g);
            case KNIGHTS -> this.knightOption.draw(g);
            case MENU -> this.menu.draw(g);
            case MAPS -> this.mapOption.draw(g);
        }
        if (this.dialog.isVisible()) {
            this.dialog.draw(g);
        }
    }

    private void update() {
        if (this.gameState == GameState.PLAY && !this.dialog.isVisible()) {
            this.play.update();
            var endGameState = this.determineEndGameState();
            if (endGameState != null) {
                this.dialog.setPlayState(endGameState);
                this.dialog.setVisible(true);
            }
        } else if (this.gameState == GameState.EXIT) {
            System.exit(0);
        }
    }

    private PlayState determineEndGameState() {
        if (this.play.outOfTime()) {
            return PlayState.TIME_OUT;
        } else if (!this.play.isAnyAliveEnemy()) {
            return PlayState.WIN;
        } else if (this.play.getPlayer().isDead()) {
            return PlayState.LOST;
        }
        return null;
    }


}