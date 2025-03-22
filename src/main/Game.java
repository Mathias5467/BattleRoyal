package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.io.*;
import java.util.EnumMap;
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
    private final KeyInput keyInput;
    private boolean running;
    private EnumMap<KeyType, Boolean> keysPressedReaction;
    private final int fps = 60;
    private int frameCount = 0;
    private Menu menu;
    private Options options;
    private Dialog dialog;
    private KnightType knightType;
    private Play play;
    private boolean nonKeyTyped;
    private int numberOfCoins;
    public Game() throws FileNotFoundException {
        this.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(new Color(43, 43, 43));
        this.keyInput = new KeyInput();
        this.addKeyListener(this.keyInput);
        this.menu = new Menu();
        this.options = new Options();
        this.play = new Play();
        this.gameState = GameState.OPTIONS;
        this.running = false;
        this.keysPressedReaction = new EnumMap<>(this.keyInput.getKeys());
        this.knightType = this.options.getKnightType();
        this.dialog = new Dialog(this.gameState);
        this.nonKeyTyped = false;
        File coinsFile = new File("res/data/coins.txt");
        Scanner input = new Scanner(coinsFile);
        this.numberOfCoins = input.nextInt();
        input.close();
    }

    public void start() {
        this.running = true;
        this.frameCount = 0;
        this.gameThread = new Thread(this);
        this.gameThread.start();
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
                    this.keysPressedReaction = new EnumMap<>(this.keyInput.getKeys());
                }
                lastFrame = now;
            }
        }
    }

    private void handleMenu() throws FileNotFoundException {
        if (this.keyInput.getKeys().get(KeyType.UP)) {
            this.menu.selectOption(-1);
        } else if (this.keyInput.getKeys().get(KeyType.DOWN)) {
            this.menu.selectOption(1);
        } else if (this.keyInput.getKeys().get(KeyType.ENTER)) {
            this.gameState = this.menu.getChosenGameState();
            if (this.gameState == GameState.PLAY) {
                this.play.reset();
                this.dialog.setPlayState(PlayState.TIE);
            } else if (this.gameState == GameState.EXIT) {
                File coinFile = new File("res/data/coins.txt");
                PrintWriter input = new PrintWriter(coinFile);
                input.println(this.numberOfCoins); // This will correctly write the number as text
                input.close();
                this.options.writeIntoFile();
            }
        }
    }

    private void handleOptions() {
        if (this.keyInput.getKeys().get(KeyType.LEFT)) {
            this.options.changeColor(-1);
        } else if (this.keyInput.getKeys().get(KeyType.RIGHT)) {
            this.options.changeColor(1);
        } else if (this.keyInput.getKeys().get(KeyType.ENTER)) {

            if (this.options.tryChoose()) {
                this.gameState = GameState.MENU;
                this.knightType = this.options.getKnightType();
                this.play.changeKnight(this.knightType);
            } else {
                if (this.options.tryBuy()) {
                    this.numberOfCoins = this.options.getNumberOfCoins();
                }
            }
        }
    }

    private void handlePlay() {
        if (!this.dialog.isVisible()) {
            if (this.keyInput.getKeys().get(KeyType.LEFT)) {
                this.play.moveLeft();
            } else if (this.keyInput.getKeys().get(KeyType.RIGHT)) {
                this.play.moveRight();
            } else if (this.keyInput.getKeys().get(KeyType.DOWN)) {
                this.play.defend();
            } else if (this.keyInput.getKeys().get(KeyType.A)) {
                this.play.getPlayer().attack(Movement.ATTACK1);
            } else if (this.keyInput.getKeys().get(KeyType.S)) {
                this.play.getPlayer().attack(Movement.ATTACK2);
            } else if (this.keyInput.getKeys().get(KeyType.D)) {
                this.play.getPlayer().attack(Movement.ATTACK3);
            }

        }
    }

    private void handleDialog() {
        if (this.keyInput.getKeys().get(KeyType.LEFT)) {
            this.dialog.changeOption(0);
        } else if (this.keyInput.getKeys().get(KeyType.RIGHT)) {
            this.dialog.changeOption(1);
        } else if (this.keyInput.getKeys().get(KeyType.ESC)) {
            this.dialog.hide();
        } else if (this.keyInput.getKeys().get(KeyType.ENTER)) {
            this.dialog.setConfirmed(true);
            if (this.dialog.getChosenOption().equals(ConfirmDialog.YES.toString())) {
                if (this.gameState == GameState.PLAY) {
                    this.numberOfCoins += this.play.getNumberOfCoins();
                    this.options.setNumberOfCoins(this.numberOfCoins);
                }
                this.gameState = GameState.MENU;
                this.dialog.setPlayState(PlayState.TIE);
            }
            this.dialog.hide();
        }
    }
    //TODO: refactor to more methods
    public void handleInput() throws IOException {
        var numberOfPressedKeys = 0;
        for (KeyType keyValue : this.keysPressedReaction.keySet()) {
            if (numberOfPressedKeys < 1) {
                if (!this.keysPressedReaction.get(keyValue) && this.keyInput.getKeys().get(keyValue)) {
                    numberOfPressedKeys++;
                    this.nonKeyTyped = true;
                    if (!this.dialog.isVisible()) {
                        switch (this.gameState) {
                            case MENU -> this.handleMenu();
                            case OPTIONS -> this.handleOptions();
                            case PLAY -> this.handlePlay();
                        }
                        if (this.keyInput.getKeys().get(KeyType.ESC) && this.gameState != GameState.MENU) {
                            this.dialog.setVisible();
                        }
                    } else {
                        this.handleDialog();
                    }
                }
            }
        }
        if (!this.nonKeyTyped && this.play.getPlayer().mayStop()) {
            this.play.stop();
        }
        this.nonKeyTyped = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (this.gameState) {
            case GameState.PLAY -> {
                this.play.draw(g);
            }
            case GameState.EXIT -> {
                System.exit(0);
            }
            case GameState.OPTIONS -> {
                this.options.draw(g);
            }
            case GameState.MENU -> {
                this.menu.draw(g);
            }
        }
        if (this.dialog.isVisible()) {
            this.dialog.draw(g);
        }
    }

    private void update() {

        if (this.gameState == GameState.PLAY && !this.dialog.isVisible()) {
            this.play.update();
            if (this.play.isTimeOut()) {
                this.dialog.setPlayState(PlayState.TIME_OUT);
                this.dialog.setVisible();
            }

            if (!this.play.isAliveEnemy() && !this.play.getCurrentEnemy().isVisible()) {
                this.dialog.setPlayState(PlayState.WIN);
                this.dialog.setVisible();
            } else if (this.play.getPlayer().isDead()) {
                this.dialog.setPlayState(PlayState.LOST);
                this.dialog.setVisible();
            }
        }
    }


}