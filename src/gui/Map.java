package gui;

import entity.Player;
import entity.Enemy;
import entity.Entity;
import entity.EntityType;
import entity.KnightType;
import entity.Movement;
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
    private Enemy skeleton;
    private int skeletonCounter;

    public Map() {
        this.background1 = new Picture(0, 0, 1100, 700, "res/background/background1.png");
        this.background2 = new Picture(0, 0, 1100, 700, "res/background/background2.png");
        this.background3 = new Picture(0, 0, 2200, 700, "res/background/background3.png");
        this.ground = new Picture(0, 590, 2200, 114, "res/background/ground.png");
        this.player = new Player(EntityType.KNIGHT, KnightType.RED);
        this.skeleton = new Enemy(EntityType.SKELETON);
        this.entities = new ArrayList<Entity>();
        this.entities.add(this.player);
        this.entities.add(this.skeleton);
        this.skeletonCounter = 0;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        this.background1.draw(g);
        this.background2.draw(g);
        this.background3.draw(g);
        this.ground.draw(g);
        this.player.getPicture().draw(g);
        this.player.getHpBar().draw(g);
        this.skeleton.getPicture().draw(g);
        this.skeleton.getHpBar().draw(g);
    }

    public Player getPlayer() {
        return this.player;
    }

    public void moveRight() {

//        if (this.background3.getX() < -1100) {
//            this.background3.changeCords(0, this.background3.getY());
//        }
//        if (this.ground.getX() < -1100) {
//            this.ground.changeCords(0, this.ground.getY());
//        }
//        this.background3.changeCords(this.background3.getX() - 1, this.background3.getY());
//        this.ground.changeCords(this.ground.getX() - 2, this.ground.getY());
        this.player.moveRight();
        this.player.setDefending(false);
    }

    public void defend() {
        this.player.setDefending(true);
        this.player.defend();
    }

    public void moveLeft() {
//        if (this.background3.getX() > 0) {
//            this.background3.changeCords(-1100, this.background3.getY());
//        }
//        if (this.ground.getX() > 0) {
//            this.ground.changeCords( -1100, this.ground.getY());
//        }
//        this.background3.changeCords(this.background3.getX() + 1, this.background3.getY());
//        this.ground.changeCords(this.ground.getX() + 2, this.ground.getY());
        this.player.moveLeft();
        this.player.setDefending(false);
    }

    public void stop() {
        this.player.setDefending(false);
        this.player.stop();
    }

    public Enemy getEnemy() {
        return this.skeleton;
    }

    public void update() {

        if (this.skeletonCounter == 150) {
            this.skeleton.attack(Movement.ATTACK1);
            this.skeletonCounter = 0;
        }
        this.skeletonCounter++;
        for (Entity entity : this.entities) {
            if (!entity.isAttacking()) {
                entity.setHitRegistered(false);
            }
        }

        // Check if we're close enough to the skeleton and on the correct frame
        if (this.player.getX() + 150 > this.skeleton.getX()) {
            for (Entity entity : this.entities) {
                if (entity.isAttacking() && !entity.isHitRegistered() && entity.getActualAnimationNumber() == 5) {
                    if (entity instanceof  Player) {
                        this.skeleton.hit((int)Math.ceil(this.player.getKnightType().getAttack() * 0.08));
                    } else {
                        this.player.hit(30);
                    }
                    entity.setHitRegistered(true);
                }
            }
        }

    }
}