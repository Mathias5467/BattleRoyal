package gui;

import entity.Player;
import entity.Enemy;
import entity.Entity;
import entity.EntityType;
import entity.KnightType;
import main.Picture;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Map {
    private Picture background1;
    private Picture background2;
    private Picture background3;
    private Picture ground;
    private Player player;
    private List<Entity> entities;
    private Enemy currentEnemy;
    private Enemy soldier;
    private Enemy viking;
    private Enemy skeleton;
    private boolean addCoin;
    public Map() {
        this.background1 = new Picture(0, 0, 1100, 700, "res/background/background1.png");
        this.background2 = new Picture(0, 0, 1100, 700, "res/background/background2.png");
        this.background3 = new Picture(0, 0, 2200, 700, "res/background/background3.png");
        this.ground = new Picture(0, 590, 2200, 114, "res/background/ground.png");
        this.player = new Player(EntityType.KNIGHT, KnightType.RED);
        this.viking = new Enemy(EntityType.VIKING);
        this.soldier = new Enemy(EntityType.SOLDIER);
        this.skeleton = new Enemy(EntityType.SKELETON);
        this.entities = new ArrayList<Entity>();
        this.entities.add(this.soldier);
        this.entities.add(this.player);
        this.entities.add(this.viking);
        this.entities.add(this.skeleton);
        this.currentEnemy = this.skeleton;
        this.addCoin = false;
    }

    public void reset() {
        this.player.setStartPosition();
        this.player.getHpBar().resetHP();
        this.currentEnemy.setStartPosition();
        this.currentEnemy.getHpBar().resetHP();
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
            this.ground.changeCords(this.ground.getX() - 2, this.ground.getY());
            this.player.onlyAnimate();
            this.currentEnemy.moveWithoutAnimation();
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
            if (this.background3.getX() > 0) {
                this.background3.changeCords(-1100, this.background3.getY());
            }
            if (this.ground.getX() > 0) {
                this.ground.changeCords( -1100, this.ground.getY());
            }
            this.background3.changeCords(this.background3.getX() + 1, this.background3.getY());
            this.ground.changeCords(this.ground.getX() + 2, this.ground.getY());
        }

        this.player.moveLeft();
        this.player.setDefending(false);
    }

    public void stop() {
        this.player.setDefending(false);
        this.player.stop();
    }


    public boolean isAddCoin() {
        return this.addCoin;
    }

    public void update() {
        this.addCoin = false;
        this.player.update();
        this.currentEnemy.update();


        if (this.currentEnemy.getX() < -150) {
            this.addCoin = true;
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
                        this.currentEnemy.hit((int)Math.ceil(this.player.getKnightType().getAttack() * 0.08));
                    } else {
                        this.player.hit(5);
                    }
                    entity.setHitRegistered(true);
                }
            }
        }
        this.currentEnemy.enemyAI(this.player);

    }
}