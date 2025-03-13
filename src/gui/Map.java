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
    private List<Entity> entities;
    private Enemy currentEnemy;
    public Map() throws FileNotFoundException {
        this.background1 = new Picture(0, 0, 1100, 700, "res/background/background1.png");
        this.background2 = new Picture(0, 0, 1100, 700, "res/background/background2.png");
        this.background3 = new Picture(0, 0, 2200, 700, "res/background/background3.png");
        this.ground = new Picture(0, 590, 2200, 114, "res/background/ground.png");
        this.player = new Player(EntityType.KNIGHT, KnightType.RED);
        this.entities = new ArrayList<Entity>();
        this.entities.add(this.player);
        File enemyFile = new File("res/data/enemies.txt");
        Scanner input = new Scanner(enemyFile);
        while (input.hasNextLine()) {
            this.entities.add(new Enemy(EntityType.MONSTER.getEntityByName(input.nextLine())));
        }
        this.currentEnemy = (Enemy)this.entities.get(1);
    }

    public void reset() {
        this.player.setStartPosition();
        this.player.getHpBar().resetHP();
        this.currentEnemy.setStartPosition();
        this.currentEnemy.getHpBar().resetHP();
        for (Entity entity : this.entities) {
            entity.setDead(false);
            entity.setVisible(true);
        }
        this.anotherEnemy();
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
        this.player.getPicture().draw(g);
        this.player.getHpBar().draw(g);
        this.currentEnemy.getPicture().draw(g);
        this.currentEnemy.getHpBar().draw(g);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void moveRight() {

        if (this.currentEnemy.isDead()) {
            if (this.background3.getX() < -1100) {
                this.background3.changeCords(0, this.background3.getY());
            }
            if (this.ground.getX() < -1100) {
                this.ground.changeCords(0, this.ground.getY());
            }
            this.background3.changeCords(this.background3.getX() - 1, this.background3.getY());
            this.ground.changeCords(this.ground.getX() - 3, this.ground.getY());
            this.player.onlyAnimate(Direction.RIGHT);
            this.currentEnemy.moveWithoutAnimation();
            if (this.player.getX() > 80) {
                this.player.moveWithoutAnimation();
            }
        } else {
            this.player.moveRight();
            this.player.setDefending(false);
        }

    }

    public void defend() {
        this.player.setDefending(true);
        this.player.defend();
    }

    public void moveLeft() {
        if (this.currentEnemy.isDead()) {
            this.player.onlyAnimate(Direction.LEFT);
        } else {
            this.player.moveLeft();
            this.player.setDefending(false);
        }
    }

    public void stop() {
        this.player.setDefending(false);
        this.player.stop();
    }



    public boolean anotherEnemy() {
        for (Entity entity : this.entities) {
            if (!entity.isDead() && entity instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        this.player.update();
        this.currentEnemy.update();


        if (this.currentEnemy.getX() < 0) {
            this.currentEnemy.setStartPosition();
            for (Entity entity : this.entities) {
                if (!entity.isDead() && entity instanceof Enemy) {
                    this.currentEnemy = (Enemy)entity;
                    this.currentEnemy.setStartPosition();
                    this.player.setStartPosition();
                    break;
                }
            }
        }
        for (Entity entity : this.entities) {
            if (!entity.isAttacking()) {
                entity.setHitRegistered(false);
            }
        }

        // Check if we're close enough to the skeleton and on the correct frame
        if (this.player.getX() + 80 > this.currentEnemy.getX()) {
            for (Entity entity : this.entities) {
                if (entity.isAttacking() && !entity.isHitRegistered() && entity.getActualAnimationNumber() == 5) {
                    if (entity instanceof  Player) {
//                        this.currentEnemy.hit((int)Math.ceil(this.player.getKnightType().getAttack() * 0.08));
                        if (this.currentEnemy.isVisible()) {
                            this.currentEnemy.hit(100);
                        }
                    } else {
                        this.player.hit(5);
                    }
                    entity.setHitRegistered(true);
                }
            }
        }
        this.currentEnemy.enemyAI(this.player);

    }

    public Enemy getCurrentEnemy() {
        return this.currentEnemy;
    }
}