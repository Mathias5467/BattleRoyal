package gui;

import backend.Biom;
import entity.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Trieda {@code Play} riadi hlavnú hernú obrazovku. Zabezpečuje načítanie úrovne,
 * správu entít, aktualizáciu hernej logiky a vykresľovanie počas hry.
 */
public class Play {
    private LevelManager levelManager;
    private Player player;
    private List<Enemy> enemies;
    private List<Entity> currentEntities;
    private Biom biom;
    private int numberOfCoins;
    private boolean coinAdded;
    private int timeInSeconds;
    private int miliSeconds;
    private static final int PRAH_ZASTAVENIA_HRACA = 99;
    private static final int PRAH_DALSIEJ_UROVNE_HRACA = 100;
    private static final double NASOBIC_POKODENIA_UTOKU = 0.08;
    private static final String SUBOR_S_DATAMI_NEPRIATELOV = "res/data/enemies.txt";
    private static final int CASOVY_LIMIT_SEKUND = 300;
    private static final int MILISEKUND_NA_SEKUNDU = 60;
    private static final int DOSAH_UTOKU_HRACA_VPRAVO = 80;
    private static final int DOSAH_UTOKU_HRACA_VLAVO = 150;

    /**
     * Vytvorí novú inštanciu triedy {@code Play}, inicializuje herné prostredie,
     * hráča, nepriateľov a načíta počiatočné herné dáta.
     * @throws FileNotFoundException Ak sa nenájde súbor s dátami nepriateľov.
     */
    public Play() throws FileNotFoundException {
        this.biom = Biom.FOREST;
        this.levelManager = new LevelManager(this.biom.name());
        this.player = new Player(EntityType.KNIGHT, KnightType.RED);
        this.enemies = new ArrayList<>();
        this.currentEntities = new ArrayList<>();
        this.currentEntities.add(this.player);
        this.timeInSeconds = CASOVY_LIMIT_SEKUND;
        this.miliSeconds = 0;
        this.nacitajNepriatelov();
        this.currentEntities.add(this.enemies.getFirst());
        this.numberOfCoins = 0;
        this.coinAdded = true;
    }

    /**
     * Načíta dáta o nepriateľoch zo špecifikovaného súboru a vytvorí inštancie
     * triedy {@code Enemy}.
     * @throws FileNotFoundException Ak sa nenájde súbor s dátami nepriateľov.
     */
    private void nacitajNepriatelov() throws FileNotFoundException {
        File enemyFile = new File(SUBOR_S_DATAMI_NEPRIATELOV);
        try (Scanner input = new Scanner(enemyFile)) {
            while (input.hasNextLine()) {
                EntityType enemyType = EntityType.MONSTER.getByName(input.nextLine());
                if (enemyType != null) {
                    this.enemies.add(new Enemy(enemyType));
                }
            }
        }
    }

    /**
     * Resetuje herný stav, vrátane úrovne, pozície hráča, pozícií nepriateľov,
     * časovača a zozbieraných mincí.
     */
    public void reset() {
        this.levelManager.resetSipku();
        this.timeInSeconds = CASOVY_LIMIT_SEKUND;
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
     * Vykreslí aktuálny stav hry na poskytnutý grafický kontext {@code Graphics}.
     * Zahŕňa pozadie, zem, šípku, časovač a všetky aktívne entity.
     * @param g Grafický kontext, na ktorý sa má kresliť.
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.BLACK);
        this.levelManager.getPozadie1().draw(g);
        this.levelManager.getPozadie2().draw(g);
        this.levelManager.getPozadie3().draw(g);
        this.levelManager.getSipku().draw(g);
        this.levelManager.getZem().draw(g);
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 40));
        g2.drawString("Time", 500, 70);
        g2.drawString(String.format("%02d:%02d", this.timeInSeconds / 60, this.timeInSeconds % 60), 500, 110);
        for (Entity entity : this.currentEntities) {
            entity.draw(g);
        }
    }

    /**
     * Vráti entitu hráča.
     * @return Inštancia triedy {@code Player}.
     */
    public Player getPlayer() {
        return this.player;
    }

    /**
     * Spracováva pohyb hráča doprava, posúva pozadie, ak je to potrebné,
     * a pohybuje hráčom.
     */
    public void moveRight() {
        boolean mozeSkrolovat = this.currentEntities.getLast().isDead();
        this.levelManager.posunDoprava(mozeSkrolovat);
        this.player.moveHorizontaly(Direction.RIGHT, mozeSkrolovat);
        if (this.player.getX() > PRAH_ZASTAVENIA_HRACA && mozeSkrolovat) {
            this.player.moveWithoutAnimation();
        }
    }

    /**
     * Spracováva pohyb hráča doľava.
     */
    public void moveLeft() {
        this.player.moveHorizontaly(Direction.LEFT, this.currentEntities.getLast().isDead());
    }

    /**
     * Nájde ďalšieho živého nepriateľa v zozname a nastaví ho ako aktuálneho
     * nepriateľa v hre. Ak sa nenájde žiadny živý nepriateľ, nič sa nezmení.
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
     * Skontroluje, či je v hre nejaký živý nepriateľ.
     * @return {@code true}, ak je aspoň jeden nepriateľ nažive, inak {@code false}.
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
     * Skontroluje, či vypršal herný čas.
     * @return {@code true}, ak je čas v sekundách nula, inak {@code false}.
     */
    public boolean outOfTime() {
        return this.timeInSeconds == 0;
    }

    /**
     * Vráti aktuálny počet mincí zozbieraných hráčom.
     * @return Počet mincí.
     */
    public int getNumberOfCoins() {
        return this.numberOfCoins;
    }

    /**
     * Pokúsi sa pridať mincu do hráčovej zbierky, ak nebola nedávno pridaná
     * a aktuálny nepriateľ je mŕtvy.
     */
    private void tryToAddCoin() {
        if (!this.coinAdded && this.currentEntities.getLast().isDead()) {
            this.numberOfCoins++;
            this.coinAdded = true;
        }
    }

    /**
     * Aktualizuje herný časovač, dekrementuje čas v sekundách na základe
     * uplynulých milisekúnd.
     */
    private void outOfTimeControl() {
        this.miliSeconds++;
        if (this.miliSeconds == MILISEKUND_NA_SEKUNDU && !this.outOfTime()) {
            this.miliSeconds = 0;
            this.timeInSeconds--;
        }
    }

    /**
     * Iniciuje sekvenciu prechodu na ďalšiu úroveň, zneviditeľní aktuálneho
     * nepriateľa a zobrazí šípku prechodu na úroveň.
     */
    private void levelTransition() {
        this.currentEntities.getLast().setVisible(false);
        this.levelManager.zobrazSipku();
        this.coinAdded = false;
    }

    /**
     * Skontroluje, či sú splnené podmienky pre prechod na ďalšiu úroveň
     * (hráč blízko ľavého okraja, aktuálny nepriateľ mŕtvy a šípka mimo obrazovky).
     * Ak áno, pokúsi sa nájsť ďalšieho živého nepriateľa.
     */
    private void readyForNextLevelCheck() {
        if (this.player.getX() < PRAH_DALSIEJ_UROVNE_HRACA && this.currentEntities.getLast().isDead() && this.levelManager.getSipku().getX() < -100) {
            if (this.isAnyAliveEnemy()) {
                this.findAliveEnemy();
                this.levelManager.resetSipku();
            }
        }
    }

    /**
     * Skontroluje, či je hráč v dosahu útoku aktuálneho nepriateľa.
     * @return {@code true}, ak je hráč v oblasti útoku, inak {@code false}.
     */
    private boolean inAttackArea() {
        int enemyX = this.currentEntities.getLast().getX();
        int playerX = this.player.getX();
        return playerX + DOSAH_UTOKU_HRACA_VPRAVO > enemyX && playerX - DOSAH_UTOKU_HRACA_VLAVO < enemyX;
    }

    /**
     * Spracováva bojové interakcie medzi hráčom a aktuálnym nepriateľom.
     * Kontroluje útočiace entity a aplikuje poškodenie, ak je zásah zaregistrovaný
     * počas príslušného snímku animácie.
     */
    private void tryFight() {
        if (this.inAttackArea()) {
            for (Entity entity : this.currentEntities) {
                if (entity.isAttacking() && !entity.isHitRegistered() && entity.getActAnimNumber() == 5) {
                    Entity target = (entity instanceof Player) ? this.currentEntities.getLast() : this.player;
                    if (target != null && target.isVisible()) {
                        int damage = (entity instanceof Player)
                                ? (int)Math.ceil(this.player.getKnightType().getAttack() * NASOBIC_POKODENIA_UTOKU)
                                : entity.getEntityType().getAttack();
                        target.hit(damage);
                        entity.setHitRegistered(true);
                    }
                }
            }
        }
    }

    /**
     * Aktualizuje herný stav, vrátane animácií entít, prechodov medzi úrovňami,
     * časovača, zbierania mincí a umelej inteligencie nepriateľov.
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
        if (this.currentEntities.getLast() instanceof Enemy) {
            ((Enemy)this.currentEntities.getLast()).enemyAI(this.player);
        }
    }

    /**
     * Nastaví aktuálny biom hry a aktualizuje správcu úrovní tak, aby odrážal
     * nový biom.
     * @param biom Nový biom hry typu {@code Biom}.
     */
    public void setBiom(Biom biom) {
        this.biom = biom;
        this.levelManager.nastavBiom(this.biom.name());
    }
}