package gui;

import entity.*;
import main.Picture;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {
    private Picture background1;
    private Picture background2;
    private Picture background3;
    private Picture ground;
    private Player player;
    private List<Enemy> enemies;
    private List<Entity> currentEntities;
    private boolean coinAdded;
    private int numberOfCoins;
    public Map() throws FileNotFoundException {
        this.background1 = new Picture(0, 0, 1100, 700, "res/background/background1.png");
        this.background2 = new Picture(0, 0, 1100, 700, "res/background/background2.png");
        this.background3 = new Picture(0, 0, 2200, 700, "res/background/background3.png");
        this.ground = new Picture(0, 590, 2200, 114, "res/background/ground.png");
        this.player = new Player(EntityType.KNIGHT, KnightType.RED);
        this.enemies = new ArrayList<>();
        this.currentEntities = new ArrayList<>();
        this.currentEntities.add(this.player);
        File enemyFile = new File("res/data/enemies.txt");
        Scanner input = new Scanner(enemyFile);
        while (input.hasNextLine()) {
            this.enemies.add(new Enemy(EntityType.MONSTER.getEntityByName(input.nextLine())));
        }
        this.numberOfCoins = 0;
        this.currentEntities.add(this.enemies.get(1));
        this.coinAdded = false;
    }

    public void reset() {
        this.numberOfCoins = 0;
        for (Entity entity : this.currentEntities) {
            entity.setDead(false);
            entity.setVisible(true);
            entity.getHpBar().resetHP();
            entity.setStartPosition();
        }
    }

    public void changeKnight(KnightType knightType) {
        this.player.setKnight(knightType);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        this.background1.draw(g);
        this.background2.draw(g);
        this.background3.draw(g);
        this.ground.draw(g);
        for (Entity entity : this.currentEntities) {
            entity.getPicture().draw(g);
            entity.getHpBar().draw(g);
        }
    }

    public Player getPlayer() {
        return this.player;
    }


    public void moveRight() {
        if (this.currentEntities.get(1).isDead()) {
            if (this.background3.getX() < -1100) {
                this.background3.changeCords(0, this.background3.getY());

            }
            if (this.ground.getX() < -1100) {
                this.ground.changeCords(0, this.ground.getY());
            }
            this.background3.changeCords(this.background3.getX() - 1, this.background3.getY());
            this.ground.changeCords(this.ground.getX() - 3, this.ground.getY());

            this.player.moveHorizontaly(Direction.RIGHT, true);
            if (this.player.getX() > 79) {
                this.player.moveWithoutAnimation();
            }
        } else {
            this.player.moveHorizontaly(Direction.RIGHT, false);
        }

    }

    public void defend() {
        this.player.defend();
    }

    public void moveLeft() {
        if (this.currentEntities.get(1).isDead()) {
            this.player.moveHorizontaly(Direction.LEFT, true);
        } else {
            this.player.moveHorizontaly(Direction.LEFT, false);
        }
    }

    public void stop() {
        this.player.stop();
    }

    public boolean isAnotherEnemy() {
        for (Enemy enemy : this.enemies) {
            if (!enemy.isDead()) {
                return true;
            }
        }
        return false;
    }

    public void findAliveEnemy() {
        for (Enemy enemy : this.enemies) {
            if (!enemy.isDead()) {
                this.currentEntities.set(1, enemy);
            }
        }
    }

    public boolean hittingDistance() {
        return (this.player.getX() + 80 > this.currentEntities.get(1).getX() && this.player.getX()  < this.currentEntities.get(1).getX() + 130);
    }

    public void update() {
        //updatujem playera a currentEnemy
        for (Entity entity : this.currentEntities) {
            entity.update();
        }

        //vyber dalsieho ziveho moba a nastavenie zaciatocnej hodnoty
        if (this.player.getX() < 80 && this.currentEntities.get(1).isDead()) {
            this.findAliveEnemy();
            this.coinAdded = false;
            this.currentEntities.getLast().setStartPosition();
        }


        for (Entity entity : this.currentEntities) {
            if (!entity.isAttacking()) {
                entity.setHitRegistered(false);
            }
        }

        if (this.currentEntities.getLast().isDead() && !this.coinAdded) {
            this.coinAdded = true;
            this.numberOfCoins++;
        }



        if (this.hittingDistance()) {
            for (Entity entity : this.currentEntities) {
                if (entity.isAttacking() && !entity.isHitRegistered() && entity.getActualAnimationNumber() == 5) {
                    if (entity instanceof  Player && this.currentEntities.get(1).isVisible()) {
//                      this.currentEnemy.hit((int)Math.ceil(this.player.getKnightType().getAttack() * 0.08));
                        this.currentEntities.get(1).hit(100);
                    } else {
                        this.player.hit(this.currentEntities.get(1).getEntity().getAttack());
                    }
                    entity.setHitRegistered(true);
                }
            }
        }
        ((Enemy)this.currentEntities.get(1)).enemyAI(this.player);

    }



    public int getNumberOfCoins() {
        return this.numberOfCoins;
    }
}