package gui;

import backend.Biom;
import entity.*;
import main.Picture;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Trieda {@code Play} riadi hernú logiku počas samotnej hry.
 * Obsahuje inicializáciu prostredia, správu entít (hráča a nepriateľov),
 * pohyb pozadia, časovač a detekciu konca úrovne a súbojov.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public class Play {
    private Picture background1;
    private Picture background2;
    private Picture background3;
    private Picture arrow;
    private Picture ground;
    private Player player;
    private List<Enemy> enemies;
    private List<Entity> currentEntities;
    private Biom biom;
    private int numberOfCoins;
    private boolean coinAdded;
    private int timeInSeconds;
    private int miliSeconds;

    /**
     * Konštruktor triedy {@code Play}. Inicializuje herné prostredie, načítava pozadia,
     * vytvára hráča a načítava zoznam nepriateľov zo súboru.
     */
    public Play() throws FileNotFoundException {
        this.biom = Biom.FOREST;
        this.background1 = new Picture(0, 0, 2200, 700, String.format("res/back/%s/b1.png", this.biom.name()));
        this.background2 = new Picture(0, 0, 2200, 700, String.format("res/back/%s/b2.png", this.biom.name()));
        this.background3 = new Picture(0, 0, 2200, 700, String.format("res/back/%s/b3.png", this.biom.name()));
        this.arrow = new Picture(800, 535, 100, 155, "res/arrow.png");
        this.ground = new Picture(0, 650, 2200, 58, "res/back/ground.png");
        this.player = new Player(EntityType.KNIGHT, KnightType.RED);
        this.enemies = new ArrayList<>();
        this.currentEntities = new ArrayList<>();
        this.currentEntities.add(this.player);
        this.timeInSeconds = 0;
        this.miliSeconds = 0;
        File enemyFile = new File("res/data/enemies.txt");
        Scanner input = new Scanner(enemyFile);
        while (input.hasNextLine()) {
            this.enemies.add(new Enemy(EntityType.MONSTER.getByName(input.nextLine())));
        }
        input.close();
        this.currentEntities.add(this.enemies.getFirst());
        this.numberOfCoins = 0;
        this.coinAdded = true;
    }

    /**
     * Resetuje hernú úroveň do počiatočného stavu. Skryje šípku, obnoví čas,
     * vynuluje počet mincí a premiestni všetky entity na ich štartovacie pozície.
     */
    public void reset() {
        this.arrow.setVisible(false);
        this.timeInSeconds = 300;
        this.numberOfCoins = 0;
        this.player.getHpBar().resetHP();
        for (Entity entity : this.currentEntities) {
            entity.setStartPosition();
        }
        for (Enemy enemy : this.enemies) {
            enemy.setStartPosition();
        }
    }

    /**
     * Vykresľuje herné prostredie a všetky entity na zadaný grafický kontext.
     * @param g Grafický kontext, na ktorý sa majú herné prvky vykresliť.
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        this.background1.draw(g);
        this.background2.draw(g);
        this.background3.draw(g);
        this.arrow.draw(g);
        this.ground.draw(g);
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 40));
        g2.drawString("Time", 500, 70);
        g2.drawString(String.format("%02d:%02d", this.timeInSeconds / 60, this.timeInSeconds % 60), 500, 110);
        for (Entity entity : this.currentEntities) {
            entity.draw(g);
        }
    }

    /**
     * Vráti inštanciu hráča.
     * @return Inštancia triedy {@code Player}.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Posúva pozadie pri prechode medzi levelmi a hráča počas hry doprava.
     * Rýchlosť posúvania jednotlivých vrstiev pozadia sa líši pre vytvorenie paralaxného efektu.
     */
    public void moveRight() {
        if (this.currentEntities.getLast().isDead()) {
            if (this.background2.getX() < -1100) {
                this.background2.changeCords(0, this.background2.getY());
            }
            if (this.background1.getX() < -1100) {
                this.background1.changeCords(0, this.background1.getY());
            }
            if (this.ground.getX() < -1100) {
                this.ground.changeCords(0, this.ground.getY());
            }
            if (this.background3.getX() < -1100) {
                this.background3.changeCords(0, this.background3.getY());
            }
            this.background1.changeCords(this.background1.getX() - 1, this.background1.getY());
            this.background2.changeCords(this.background2.getX() - 2, this.background2.getY());
            this.background3.changeCords(this.background3.getX() - 3, this.background2.getY());
            this.ground.changeCords(this.ground.getX() - 4, this.ground.getY());
            this.arrow.changeCords(this.arrow.getX() - 4, this.arrow.getY());
            this.player.moveHorizontaly(Direction.RIGHT, true);
            if (this.player.getX() > 99) {
                this.player.moveWithoutAnimation();
            }
        } else {
            this.player.moveHorizontaly(Direction.RIGHT, false);
        }

    }

    /**
     * Posúva hráča doľava.
     */
    public void moveLeft() {
        this.player.moveHorizontaly(Direction.LEFT, this.currentEntities.getLast().isDead());
    }

    /**
     * Nájde a nastaví ďalšieho živého nepriateľa ako aktuálnu bojovú entitu.
     * Odstráni predchádzajúceho nepriateľa zo zoznamu aktuálnych entít a pridá nového, ak existuje.
     */
    public void findAliveEnemy() {
        this.currentEntities.removeLast();
        for (Enemy enemy : this.enemies) {
            if (!enemy.isDead()) {
                enemy.setStartPosition();
                this.currentEntities.add(enemy);
                break;
            }
        }
    }

    /**
     * Kontroluje, či existuje aspoň jeden živý nepriateľ v zozname.
     * @return {@code true}, ak existuje aspoň jeden živý nepriateľ, inak {@code false}.
     */
    public boolean isAnyAliveEnemy() {
        for (Enemy enemy : this.enemies) {
            if (!enemy.isDead()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Kontroluje, či vypršal časový limit pre aktuálnu úroveň.
     * @return {@code true}, ak je časovač na nule, inak {@code false}.
     */
    public boolean outOfTime() {
        return this.timeInSeconds == 0;
    }

    /**
     * Vráti aktuálny počet získaných mincí.
     * @return Celkový počet mincí.
     */
    public int getNumberOfCoins() {
        return this.numberOfCoins;
    }

    /**
     * Pokúsi sa pridať mincu k celkovému počtu, ak bol aktuálny nepriateľ porazený
     * a minca ešte nebola pridaná za tohto nepriateľa.
     */
    private void tryToAddCoin() {
        if (!this.coinAdded && this.currentEntities.getLast().isDead()) {
            this.numberOfCoins++;
            this.coinAdded = true;
        }
    }

    /**
     * Riadi časovač hry, znižuje počet sekúnd každých 60 milisekúnd.
     */
    private void outOfTimeControl() {
        this.miliSeconds++;
        if (this.miliSeconds == 60 && !this.outOfTime()) {
            this.miliSeconds = 0;
            this.timeInSeconds--;
        }
    }

    /**
     * Spustí prechod na ďalšiu úroveň. Skryje porazeného nepriateľa a zobrazí šípku
     * indikujúcu smer ďalšieho postupu. Resetuje príznak pridania mince.
     */
    private void levelTransition() {
        this.currentEntities.getLast().setVisible(false);
        this.arrow.setVisible(true);
        this.coinAdded = false;
    }

    /**
     * Kontroluje, či je hráč pripravený na prechod na ďalšiu úroveň.
     * Ak je hráč na začiatku obrazovky, aktuálny nepriateľ je porazený a šípka mimo obrazovky,
     * nájde ďalšieho živého nepriateľa a resetuje pozíciu šípky.
     */
    private void readyForNextLevelCheck() {
        if (this.player.getX() < 100 && this.currentEntities.getLast().isDead() && this.arrow.getX() < -100) {
            if (this.isAnyAliveEnemy()) {
                this.findAliveEnemy();
                this.arrow.setVisible(false);
                this.arrow.changeCords(800, this.arrow.getY());
            }
        }
    }

    /**
     * Kontroluje, či sa entity nachádzajú v oblasti útoku a ak áno, registruje zásah
     * a aplikuje poškodenie na základe typu útočiacej entity.
     */
    private void tryFight() {
        if (this.inAttackArea()) {
            for (Entity entity : this.currentEntities) {
                if (entity.isAttacking() && !entity.isHitRegistered() && entity.getActAnimNumber() == 5) {
                    if (entity instanceof  Player && this.currentEntities.getLast().isVisible()) {
                        this.currentEntities.getLast().hit((int)Math.ceil(this.player.getKnightType().getAttack() * 0.08));

                    } else {
                        this.player.hit(this.currentEntities.getLast().getEntityType().getAttack());
                    }
                    entity.setHitRegistered(true);
                }
            }
        }
    }

    /**
     * Kontroluje, či sa hráč nachádza v oblasti útoku aktuálneho nepriateľa.
     * @return {@code true}, ak je hráč v oblasti útoku, inak {@code false}.
     */
    private boolean inAttackArea() {
        return this.player.getX() + 80 > this.currentEntities.getLast().getX() && this.player.getX() - 150 < this.currentEntities.getLast().getX();
    }

    /**
     * Aktualizuje stav hry, animácie entít, kontroluje prechod na ďalšiu úroveň,
     * riadi časovač, pokúša sa pridať mincu a spúšťa umelú inteligenciu nepriateľa.
     */
    public void update() {

        for (Entity entity : this.currentEntities) {
            entity.update();
            if (!entity.isAttacking()) {
                entity.setHitRegistered(false);
            }
        }
        if (this.currentEntities.getLast().isDead() && this.currentEntities.getLast().isVisible()) {
            this.levelTransition();
        }
        this.outOfTimeControl();
        this.tryToAddCoin();
        this.readyForNextLevelCheck();
        this.tryFight();
        ((Enemy)this.currentEntities.getLast()).enemyAI(this.player);
    }

    /**
     * Nastaví aktuálny biom (prostredie) hry a zmení pozadia na zodpovedajúce.
     * @param biom Enum {@code Biom} reprezentujúci nové prostredie.
     */
    public void setBiom(Biom biom) {
        this.biom = biom;
        this.background1.changeImage(String.format("res/back/%s/b1.png", this.biom.name()));
        this.background2.changeImage(String.format("res/back/%s/b2.png", this.biom.name()));
        this.background3.changeImage(String.format("res/back/%s/b3.png", this.biom.name()));

    }
}