package gui;


import backend.SelectOption;
import main.GameState;
import main.Picture;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;


public class Menu extends SelectOption {
    private Picture background;
    private GameState[] options;
    private int[] rectangleChosenCords;
    private int chosenOptionNumber;
    public Menu() {
        this.background = new Picture(0, 0, 1245, 700, "res/back/menu.png");
        this.options = new GameState[] {GameState.PLAY, GameState.KNIGHTS, GameState.MAPS, GameState.EXIT};
        this.rectangleChosenCords = new int[] {478, 295};
        this.chosenOptionNumber = 0;
    }

    @Override
    public void selectOption(int direction) {
        this.chosenOptionNumber += direction;
        this.chosenOptionNumber = this.mod(this.chosenOptionNumber, 4);
        this.rectangleChosenCords[1] = 295 + this.chosenOptionNumber * 70;
    }


    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        this.background.draw(g);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRoundRect(400, 250, 300, 350, 30, 30);
        g2.setColor(new Color(255, 255, 255));
        g2.drawRoundRect(400, 250, 300, 350, 30, 30);
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 80));
        g2.drawString("Battle Royal", 315, 150);
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.drawString(GameState.PLAY.getName(), 515, 330);
        g2.drawString(GameState.KNIGHTS.getName(), 490, 400);
        g2.drawString(GameState.MAPS.getName(), 523, 470);
        g2.drawString(GameState.EXIT.getName(), 515, 540);
        g2.drawRoundRect(this.rectangleChosenCords[0], this.rectangleChosenCords[1], 140, 50, 15, 15);
    }

    public GameState getChosenGameState() {
        return this.options[this.chosenOptionNumber];
    }


}
