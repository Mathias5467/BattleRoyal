package gui;


import entity.KnightColor;
import main.Picture;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.util.HashMap;


public class Options {

    private Picture knightPicture;
    private HashMap<KnightColor, int[]> knightStatistic;
    private KnightColor knightColor;
    private final int tile = 3;
    private KnightColor[] colors;
    private String[] knightNames;
    public Options() {
        this.knightColor = KnightColor.BLUE;
        this.knightPicture = new Picture(600, 200, 300, 320, this.getPathToImage());
        this.knightStatistic = new HashMap<KnightColor, int[]>() {{
                put(KnightColor.RED, new int[] {50, 90, 70});
                put(KnightColor.GREEN, new int[] {95, 50, 40});
                put(KnightColor.BLUE, new int[] {45, 50, 85});
                }};
        this.knightNames = new String[] {"Thorne", "Alaric", "Rhogar"};
        this.colors = new KnightColor[] {KnightColor.RED, KnightColor.GREEN, KnightColor.BLUE};
    }

    public String getPathToImage() {
        return String.format("res/knight/%s/stayL.png", this.knightColor.getColor());
    }

    public KnightColor getKnightColor() {
        return this.knightColor;
    }

    public void changeColor(int direction) {
        switch (this.knightColor) {
            case RED -> {
                if (direction < 0) {
                    this.knightColor = this.colors[2];
                } else {
                    this.knightColor = this.colors[direction];
                }
            }
            case GREEN -> {
                this.knightColor = this.colors[1 + direction];
            }
            case BLUE -> {
                if (2 + direction > 2) {
                    this.knightColor = this.colors[0];
                } else {
                    this.knightColor = this.colors[2 + direction];
                }
            }
        }
        this.knightPicture.changeImage(this.getPathToImage());
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(new Color(255, 255, 255, 80));
        g2.fillRoundRect(550, 100, 400, 500, 30, 30);
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
        g2.drawString("& get back to menu", 160, 560);
        g2.setColor(new Color(17, 72, 7));
        g2.fillRect(100, 190, this.knightStatistic.get(this.knightColor)[0] * this.tile, 30);
        g2.setColor(new Color(142, 37, 29));
        g2.fillRect(100, 310, this.knightStatistic.get(this.knightColor)[1] * this.tile, 30);
        g2.setColor(new Color(1, 48, 94));
        g2.fillRect(100, 430, this.knightStatistic.get(this.knightColor)[2] * this.tile, 30);
//        g2.setColor(new Color(255, 255, 255));
        g2.setColor(new Color(255, 255, 255, 80));
        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawRect(100, 190, 300, 30);
        g2.drawString(String.format("%d/%d", this.knightStatistic.get(this.knightColor)[0], 100), 420, 210);
        g2.drawRect(100, 310, 300, 30);
        g2.drawString(String.format("%d/%d", this.knightStatistic.get(this.knightColor)[1], 100), 420, 330);
        g2.drawRect(100, 430, 300, 30);
        g2.drawString(String.format("%d/%d", this.knightStatistic.get(this.knightColor)[2], 100), 420, 450);
        g2.setColor(new Color(43, 43, 43));
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.drawString(this.knightNames[this.knightColor.ordinal()], 700, 570);
        g2.setFont(new Font("Segoe Print", Font.BOLD, 90));
        g2.drawString("<", 560, 380);
        g2.drawString(">", 890, 380);

    }




}
