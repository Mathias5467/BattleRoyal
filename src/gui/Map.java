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
    public Map() throws FileNotFoundException {
        this.background1 = new Picture(0, 0, 1100, 700, "res/background/background1.png");
        this.background2 = new Picture(0, 0, 1100, 700, "res/background/background2.png");
        this.background3 = new Picture(0, 0, 2200, 700, "res/background/background3.png");
        this.ground = new Picture(0, 590, 2200, 114, "res/background/ground.png");
        this.player = new Player(EntityType.KNIGHT, KnightType.RED);
        this.enemies = new ArrayList<Enemy>();
        this.currentEntities = new ArrayList<Entity>();
        this.currentEntities.add(this.player);

        File enemyFile = new File("res/data/enemies.txt");
        Scanner input = new Scanner(enemyFile);
        while (input.hasNextLine()) {
            this.enemies.add(new Enemy(EntityType.MONSTER.getEntityByName(input.nextLine())));
        }
        this.currentEntities.add(this.enemies.getFirst());
    }

    public void reset() {
        for (Entity entity : this.currentEntities) {
            entity.setStartPosition();
            entity.setDead(false);
            entity.setVisible(true);
        }
        for (Enemy enemy : this.enemies) {
            enemy.setDead(false);
            enemy.setVisible(true);
        }
        this.player.getHpBar().resetHP();
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
            entity.getHpBar().draw(g);
            entity.getPicture().draw(g);
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

            this.player.moveHorizontaly(Direction.RIGHT, true);
            if (this.player.getX() > 99) {
                this.player.moveWithoutAnimation();
            }
        } else {
            this.player.moveHorizontaly(Direction.RIGHT, false);
            this.player.setDefending(false);
        }

    }

    public void defend() {
        this.player.setDefending(true);
        this.player.defend();
    }

    public void moveLeft() {
        if (this.currentEntities.getLast().isDead()) {
            this.player.moveHorizontaly(Direction.LEFT, true);
        } else {
            this.player.moveHorizontaly(Direction.LEFT, false);
            this.player.setDefending(false);
        }
    }

    public void stop() {
        this.player.setDefending(false);
        this.player.stop();
    }

    public boolean findAliveEnemy() {
        for (Entity entity : this.enemies) {
            if (!entity.isDead() && entity instanceof Enemy) {
                return true;
            }
        }
        return false;
    }

    public void update() {
        for (Entity entity : this.currentEntities) {
            entity.update();
        }

        if (this.player.getX() < 100 && this.currentEntities.getLast().isDead()) {
            this.currentEntities.removeLast();
            for (Enemy enemy : this.enemies) {
                if (!enemy.isDead()) {
                    this.currentEntities.add(enemy);
                    this.currentEntities.getLast().setStartPosition();
                    this.player.setStartPosition();
                    break;
                }
            }
        }
        for (Entity entity : this.currentEntities) {
            if (!entity.isAttacking()) {
                entity.setHitRegistered(false);
            }
        }

        if (this.player.getX() + 80 > this.currentEntities.getLast().getX()) {
            for (Entity entity : this.currentEntities) {
                if (entity.isAttacking() && !entity.isHitRegistered() && entity.getActualAnimationNumber() == 5) {
                    if (entity instanceof  Player && this.currentEntities.getLast().isVisible()) {
//                        this.currentEnemy.hit((int)Math.ceil(this.player.getKnightType().getAttack() * 0.08));
                        this.currentEntities.getLast().hit(100);
                    } else {
                        this.player.hit(this.currentEntities.getLast().getEntity().getAttack());
                    }
                    entity.setHitRegistered(true);
                }
            }
        }
        ((Enemy)this.currentEntities.getLast()).enemyAI(this.player);

    }

    public Enemy getCurrentEnemy() {
        return ((Enemy)this.currentEntities.getLast());
    }
}