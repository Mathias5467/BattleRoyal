package gui;

import entity.*;
import main.Picture;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Play {
    private Picture background1;
    private Picture background2;
    private Picture background3;
    private Picture arrow;
    private Picture ground;
    private Player player;
    private List<Enemy> enemies;
    private List<Entity> currentEntities;
    private int numberOfCoins;
    private boolean coinAdded;
    private int timeInSeconds;
    private int fpsCounter;
    public Play() throws FileNotFoundException {
        this.background1 = new Picture(0, 0, 1100, 700, "res/background/background1.png");
        this.background2 = new Picture(0, 0, 1100, 700, "res/background/background2.png");
        this.background3 = new Picture(0, 0, 2200, 700, "res/background/background3.png");
        this.arrow = new Picture(800, 490, 100, 155, "res/arrow.png");
        this.ground = new Picture(0, 590, 2200, 114, "res/background/ground.png");
        this.player = new Player(EntityType.KNIGHT, KnightType.RED);
        this.enemies = new ArrayList<>();
        this.currentEntities = new ArrayList<>();
        this.currentEntities.add(this.player);
        this.timeInSeconds = 0;
        this.fpsCounter = 0;
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

    public void reset() {
        this.arrow.setVisible(false);
        this.timeInSeconds = 300;
        this.numberOfCoins = 0;
        for (Entity entity : this.currentEntities) {
            entity.setStartPosition();
        }
        for (Enemy enemy : this.enemies) {
            enemy.setStartPosition();
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setColor(Color.WHITE);
        this.background1.draw(g);
        this.background2.draw(g);
        this.background3.draw(g);
        this.arrow.draw(g);
        this.ground.draw(g);
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 40));
        g2.drawString(String.format("%02d:%02d", this.timeInSeconds / 60, this.timeInSeconds % 60), 500, 110);
        for (Entity entity : this.currentEntities) {
            entity.draw(g);
        }
    }

    public Player getPlayer() {
        return this.player;
    }


    public void moveRight() {
        if (this.currentEntities.getLast().isDead()) {
            if (this.background3.getX() < -1100) {
                this.background3.changeCords(0, this.background3.getY());

            }
            if (this.ground.getX() < -1100) {
                this.ground.changeCords(0, this.ground.getY());
            }
            this.background3.changeCords(this.background3.getX() - 1, this.background3.getY());
            this.ground.changeCords(this.ground.getX() - 3, this.ground.getY());
            this.arrow.changeCords(this.arrow.getX() - 3, this.arrow.getY());

            this.player.moveHorizontaly(Direction.RIGHT, true);
            if (this.player.getX() > 99) {
                this.player.moveWithoutAnimation();
            }
        } else {
            this.player.moveHorizontaly(Direction.RIGHT, false);
        }

    }

    public void moveLeft() {
        this.player.moveHorizontaly(Direction.LEFT, this.currentEntities.getLast().isDead());
    }

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

    public boolean isAnyAliveEnemy() {
        for (Entity entity : this.enemies) {
            if (!entity.isDead() && entity instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    public boolean outOfTime() {
        return this.timeInSeconds == 0;
    }

    public int getNumberOfCoins() {
        return this.numberOfCoins;
    }

    private void tryToAddCoin() {
        if (!this.coinAdded && this.currentEntities.getLast().isDead()) {
            this.numberOfCoins++;
            this.coinAdded = true;
        }
    }

    private void outOfTimeControl() {
        this.fpsCounter++;
        if (this.fpsCounter == 60 && !this.outOfTime()) {
            this.fpsCounter = 0;
            this.timeInSeconds--;
        }
    }

    private void levelTransition() {
        this.currentEntities.getLast().setVisible(false);
        this.arrow.setVisible(true);
        this.coinAdded = false;
    }

    private void readyForNextLevel() {
        if (this.player.getX() < 100 && this.currentEntities.getLast().isDead() && this.arrow.getX() < -100) {
            if (this.isAnyAliveEnemy()) {
                this.findAliveEnemy();
                this.arrow.setVisible(false);
                this.arrow.changeCords(800, this.arrow.getY());
            }
        }
    }

    private void tryFight() {
        if (this.inAttackArea()) {
            for (Entity entity : this.currentEntities) {
                if (entity.isAttacking() && !entity.isHitRegistered() && entity.getActAnimNumber() == 5) {
                    if (entity instanceof  Player && this.currentEntities.getLast().isVisible()) {
//                        this.currentEnemy.hit((int)Math.ceil(this.player.getKnightType().getAttack() * 0.08));
                        this.currentEntities.getLast().hit(100);
                    } else {
                        this.player.hit(this.currentEntities.getLast().getEntityType().getAttack());
                    }
                    entity.setHitRegistered(true);
                }
            }
        }
    }

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
        this.readyForNextLevel();
        this.tryFight();
        ((Enemy)this.currentEntities.getLast()).enemyAI(this.player);
    }

    private boolean inAttackArea() {
        return this.player.getX() + 80 > this.currentEntities.getLast().getX() && this.player.getX() - 150 < this.currentEntities.getLast().getX();
    }

}