package gui;


import entity.KnightColor;
import main.Picture;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.util.HashMap;


public class Options {

    private Picture knightPicture;
    private HashMap<KnightColor, int[]> knightStatistic;
    public Options() {
        this.knightPicture = new Picture(600, 200, 300, 320, "res/knight/red/stayL.png");
        this.knightStatistic = new HashMap<KnightColor, int[]>() {{
                put(KnightColor.RED, new int[] {100, 50, 40});
                put(KnightColor.GREEN, new int[] {100, 50, 40});
                put(KnightColor.BLUE, new int[] {100, 50, 40});
                }};
    }


    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(new Color(255, 255, 255, 80));
        g2.fillRoundRect(550, 100, 400, 500, 30, 30);
        this.knightPicture.draw(g);
        g2.setColor(new Color(255, 255, 255));
//        g2.setFont(new Font("Consolas", Font.BOLD, 50));
//        g2.drawString("Statistics", 130, 100);
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.drawString("HP", 100, 180);
        g2.drawString("Attack", 100, 300);
        g2.drawString("Defend", 100, 420);
        g2.drawRect(100, 200, 300, 30);
        g2.drawRect(100, 320, 300, 30);
        g2.drawRect(100, 440, 300, 30);

    }




}
