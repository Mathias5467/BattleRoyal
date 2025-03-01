package entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;


public class HPBar {
    private final int x;
    private final int y;
    private int actualHP;
    private final int maximalHP;
    private final int width;
    private final int height;
    private final int tileSize;
    private String name;
    private final int textX;
    private final int textY;
    public HPBar(int x, int y, int maximalHP, int textX, int textY, String name) {
        this.maximalHP = maximalHP;
        this.actualHP = this.maximalHP;
        this.tileSize = 3;
        this.name = name;
        this.x = x;
        this.y = y;
        this.textX = textX;
        this.textY = textY;
        this.width = this.actualHP * this.tileSize;
        this.height = 30;
    }

    public int getActualHP() {
        return this.actualHP;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.RED);
        g2.fillRect(this.x, this.y, this.width, this.height);
        g2.setColor(Color.WHITE);
        g2.drawRect(this.x, this.y, this.width, this.height);
        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(Color.WHITE);
        g2.drawString(this.name, this.textX, this.textY);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void reduceHP(int downHP) {
        if (this.actualHP - downHP > 0) {
            this.actualHP -= downHP;
        } else {
            this.actualHP = 0;
        }
    }

    public void resetHP() {
        this.actualHP = this.maximalHP;
    }

}


