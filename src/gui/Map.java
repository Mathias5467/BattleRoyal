package gui;

import entity.*;
import main.Picture;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;

public class Map {
    private Picture background1;
    private Picture background2;
    private Picture background3;
    private Picture ground;
    private Player player;
    private List<Enemy> enemies;
    private Enemy skeleton;
    private boolean hit1Registered; // Track if hit has been registered for current attack
    private boolean hit2Registered; // Track if hit has been registered for current attack

    public Map() {
        this.background1 = new Picture(0, 0, 1100, 700, "res/background/background1.png");
        this.background2 = new Picture(0, 0, 1100, 700, "res/background/background2.png");
        this.background3 = new Picture(0, 0, 2200, 700, "res/background/background3.png");
        this.ground = new Picture(0, 590, 2200, 114, "res/background/ground.png");
        this.player = new Player(EntityType.KNIGHT, KnightType.RED);
        this.skeleton = new Enemy(EntityType.SKELETON);
        this.hit1Registered = false;
        this.hit2Registered = false;
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

        // If player is not attacking, reset the hit registration
        if (!this.player.isAttacking()) {
            this.hit1Registered = false;
            if (!this.skeleton.isAttacking()) {
                this.hit2Registered = false;
                return;
            }
            return;
        }




        // Check if we're close enough to the skeleton and on the correct frame
        if (this.player.getX() + 150 > this.skeleton.getX()) {
            if (this.player.isAttacking() && !this.hit1Registered) {
                // Register hit and apply damage only once
                this.skeleton.hit((int)Math.ceil(this.player.getKnightType().getAttack() * 0.08));
                this.hit1Registered = true;
            }
            if (this.skeleton.isAttacking() && !this.hit2Registered) {
                // Register hit and apply damage only once
                this.player.hit(6);
                this.hit2Registered = true;
            }
        }

    }
}