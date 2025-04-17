package gui;

import gui.utilities.Biom;
import entity.utilities.Direction;
import entity.Enemy;
import entity.Entity;
import entity.utilities.EntityType;
import entity.utilities.KnightType;
import entity.Player;
import gui.utilities.LevelManager;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import java.awt.Color;
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
    private final LevelManager levelManager;
    private final Player player;
    private final List<Enemy> enemies;
    private final List<Entity> currentEntities;
    private Biom biom;
    private int numberOfCoins;
    private boolean coinAdded;
    private int timeInSeconds;
    private int miliSeconds;
    private static final int PLAYER_STOP_THRESHOLD = 99;
    private static final int PLAYER_NEXT_LEVEL_THRESHOLD = 100;
    private static final double ATTACK_DAMAGE_MULTIPLIER = 0.08;
    private static final String ENEMIES_DATA_FILE = "res/data/enemies.txt";
    private static final int TIME_LIMIT_SECONDS = 300;
    private static final int MILLISECONDS_PER_SECOND = 60;
    private static final int PLAYER_ATTACK_RANGE_RIGHT = 80;
    private static final int PLAYER_ATTACK_RANGE_LEFT = 150;

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
        this.timeInSeconds = TIME_LIMIT_SECONDS;
        this.miliSeconds = 0;
        this.loadEnemies();
        this.currentEntities.add(this.enemies.getFirst());
        this.numberOfCoins = 0;
        this.coinAdded = true;
    }

    /**
     * Načíta dáta o nepriateľoch zo špecifikovaného súboru a vytvorí inštancie
     * triedy {@code Enemy}.
     * @throws FileNotFoundException Ak sa nenájde súbor s dátami nepriateľov.
     */
    private void loadEnemies() throws FileNotFoundException {
        File enemyFile = new File(ENEMIES_DATA_FILE);
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
        this.levelManager.resetArrow();
        this.timeInSeconds = TIME_LIMIT_SECONDS;
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
        this.levelManager.getBackground1().draw(g);
        this.levelManager.getBackground2().draw(g);
        this.levelManager.getBackground3().draw(g);
        this.levelManager.getArrow().draw(g);
        this.levelManager.getGround().draw(g);
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
        boolean canScroll = this.currentEntities.getLast().isDead();
        this.levelManager.scrollRight(canScroll);
        this.player.moveHorizontaly(Direction.RIGHT, canScroll);
        if (this.player.getX() > PLAYER_STOP_THRESHOLD && canScroll) {
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
    private void tryAddCoin() {
        if (!this.coinAdded && this.currentEntities.getLast().isDead()) {
            this.numberOfCoins++;
            this.coinAdded = true;
        }
    }

    /**
     * Aktualizuje herný časovač, dekrementuje čas v sekundách na základe
     * uplynulých milisekúnd.
     */
    private void updateTime() {
        this.miliSeconds++;
        if (this.miliSeconds == MILLISECONDS_PER_SECOND && !this.outOfTime()) {
            this.miliSeconds = 0;
            this.timeInSeconds--;
        }
    }

    /**
     * Iniciuje sekvenciu prechodu na ďalšiu úroveň, zneviditeľní aktuálneho
     * nepriateľa a zobrazí šípku prechodu na úroveň.
     */
    private void initiateLevelTransition() {
        this.currentEntities.getLast().setVisible(false);
        this.levelManager.showArrow();
        this.coinAdded = false;
    }

    /**
     * Skontroluje, či sú splnené podmienky pre prechod na ďalšiu úroveň
     * (hráč blízko ľavého okraja, aktuálny nepriateľ mŕtvy a šípka mimo obrazovky).
     * Ak áno, pokúsi sa nájsť ďalšieho živého nepriateľa.
     */
    private void checkReadyForNextLevel() {
        if (this.player.getX() < PLAYER_NEXT_LEVEL_THRESHOLD && this.currentEntities.getLast().isDead() && this.levelManager.getArrow().getX() < -100) {
            if (this.isAnyAliveEnemy()) {
                this.findAliveEnemy();
                this.levelManager.resetArrow();
            }
        }
    }

    /**
     * Skontroluje, či je hráč v dosahu útoku aktuálneho nepriateľa.
     * @return {@code true}, ak je hráč v oblasti útoku, inak {@code false}.
     */
    private boolean isInAttackArea() {
        int enemyX = this.currentEntities.getLast().getX();
        int playerX = this.player.getX();
        return playerX + PLAYER_ATTACK_RANGE_RIGHT > enemyX && playerX - PLAYER_ATTACK_RANGE_LEFT < enemyX;
    }

    /**
     * Spracováva bojové interakcie medzi hráčom a aktuálnym nepriateľom.
     * Kontroluje útočiace entity a aplikuje poškodenie, ak je zásah zaregistrovaný
     * počas príslušného snímku animácie.
     */
    private void handleCombat() {
        if (this.isInAttackArea()) {
            for (Entity entity : this.currentEntities) {
                if (entity.isAttacking() && !entity.isHitRegistered() && entity.getActAnimNumber() == 5) {
                    Entity target = (entity instanceof Player) ? this.currentEntities.getLast() : this.player;
                    if (target != null && target.isVisible()) {
                        int damage = (entity instanceof Player)
                                ? (int)Math.ceil(this.player.getKnightType().getAttack() * ATTACK_DAMAGE_MULTIPLIER)
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
            this.initiateLevelTransition();
        }
        this.updateTime();
        this.tryAddCoin();
        this.checkReadyForNextLevel();
        this.handleCombat();
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
        this.levelManager.setBiom(this.biom.name());
    }
}