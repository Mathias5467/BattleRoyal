package main;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import entity.utilities.KnightType;
import entity.utilities.Movement;
import gui.utilities.ConfirmDialog;
import gui.Dialog;
import gui.KnightOptions;
import gui.MapOptions;
import gui.Menu;
import gui.Play;
import input.KeyInput;
import input.KeyType;
import gui.utilities.Biom;
import state.GameState;
import state.PlayState;

/**
 * Hlavná trieda {@code Game}, ktorá rozširuje {@code JPanel} a implementuje {@code Runnable} pre hernú slučku.
 * Riadi herné stavy, spracováva vstup, aktualizuje hernú logiku a vykresľuje herné prvky.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
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

    /**
     * Konštruktor triedy {@code Game}. Inicializuje herné komponenty, nastavenia a načíta dáta.
     */
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

    /**
     * Spúšťa hernú slučku a nastavuje počiatočné stavy pre hru a možnosti.
     */
    public void start() {
        this.running = true;
        this.frameCount = 0;
        this.gameThread = new Thread(this);
        this.gameThread.start();
        this.knightOption.setNumberOfCoins(this.numberOfCoins);
        this.mapOption.setNumberOfCoins(this.numberOfCoins);
    }

    /**
     * Obsahuje hlavnú hernú slučku, ktorá riadi aktualizáciu a vykresľovanie hry.
     */
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

    /**
     * Spracováva vstup používateľa v časti menu.
     * @param pressed Mapa stlačených klávesov.
     */
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

    /**
     * Spracováva vstup používateľa v stave výberu rytierov.
     * @param pressed Mapa stlačených klávesov.
     */
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

    /**
     * Spracováva vstup používateľa v stave výberu máp.
     * @param pressed Mapa stlačených klávesov.
     */
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

    /**
     * Spracováva vstup používateľa počas samotnej hry.
     * @param pressed Mapa stlačených klávesov.
     */
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

    /**
     * Spracováva vstup používateľa počas zobrazeného dialógového okna.
     * @param pressed Mapa stlačených klávesov.
     */
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

    /**
     * Hlavná metóda pre spracovanie vstupu. Porovnáva aktuálne stlačené klávesy s predchádzajúcim stavom
     * a volá príslušné metódy pre daný herný stav pri stlačení klávesy.
     */
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

    /**
     * Prekresľuje herné komponenty na základe aktuálneho herného stavu.
     * @param g Grafický kontext, na ktorý sa majú herné prvky vykresliť.
     */
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

    /**
     * Aktualizuje hernú logiku na základe aktuálneho herného stavu.
     */
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

    /**
     * Určuje, či hra skončila a aký je výsledný stav hry.
     * @return Enum {@code PlayState} reprezentujúci stav konca hry, alebo {@code null}, ak hra stále prebieha.
     */
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