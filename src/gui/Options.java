package gui;


import entity.KnightType;
import main.Picture;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;


public class Options {

    private Picture knightPicture;
    private KnightType knightType;
    private final int tile = 3;
    private KnightType[] colors;
    private int counter;
    public Options() {
        this.knightType = KnightType.RED;
        this.knightPicture = new Picture(650, 200, 300, 320, this.getPathToImage());
        this.colors = new KnightType[] {KnightType.RED, KnightType.GREEN, KnightType.BLUE};
        this.counter = 0;
    }

    public String getPathToImage() {
        return String.format("res/knight/%s/stayL.png", this.knightType.getColor());
    }

    public KnightType getKnightType() {
        return this.knightType;
    }

    public void changeColor(int direction) {
        this.counter += direction;
        this.counter = this.mod(this.counter, 3);
        KnightType[] values = KnightType.values();
        this.knightType = values[this.counter % 3];
        this.knightPicture.changeImage(this.getPathToImage());
    }

    public int mod(int a, int b) {
        return (a % b < 0) ? (a % b) + Math.abs(b) : (a % b);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(new Color(255, 255, 255, 80));
        g2.fillRoundRect(600, 100, 400, 500, 30, 30);
        this.knightPicture.draw(g);

//        g2.setFont(new Font("Consolas", Font.BOLD, 50));
//        g2.drawString("Statistics", 100, 80);
        g2.setStroke(new BasicStroke(5));
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.drawString("HP", 100, 170);
        g2.drawString("Attack", 100, 290);
        g2.drawString("Defend", 100, 410);
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        g2.drawString("Press ENTER to save changes", 110, 520);
        g2.drawString("Press ESC to get back to menu", 95, 560);
        g2.setColor(new Color(17, 72, 7));
        g2.fillRect(100, 190, this.knightType.getHp() * this.tile, 30);
        g2.setColor(new Color(142, 37, 29));
        g2.fillRect(100, 310, this.knightType.getAttack() * this.tile, 30);
        g2.setColor(new Color(1, 48, 94));
        g2.fillRect(100, 430, this.knightType.getDefend() * this.tile, 30);
//        g2.setColor(new Color(255, 255, 255));
        g2.setColor(new Color(255, 255, 255, 80));
        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawRect(100, 190, 300, 30);
        g2.drawString(String.format("%d/%d", this.knightType.getHp(), 100), 420, 210);
        g2.drawRect(100, 310, 300, 30);
        g2.drawString(String.format("%d/%d", this.knightType.getAttack(), 100), 420, 330);
        g2.drawRect(100, 430, 300, 30);
        g2.drawString(String.format("%d/%d", this.knightType.getDefend(), 100), 420, 450);
        g2.setColor(new Color(43, 43, 43));
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.drawString(this.knightType.getName(), 750, 570);
        g2.setFont(new Font("Segoe Print", Font.BOLD, 90));
        g2.drawString("<", 610, 380);
        g2.drawString(">", 940, 380);

    }




}
