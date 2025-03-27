package gui;


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
        this.background = new Picture(0, 0, 1245, 700, "res/background/menu.png");
        this.options = new GameState[] {GameState.PLAY, GameState.OPTIONS, GameState.EXIT};
        this.rectangleChosenCords = new int[] {478, 305}; //   305  375  445
        this.chosenOptionNumber = 0;
    }

    @Override
    public void selectOption(int direction) {
        this.chosenOptionNumber += direction;
        this.chosenOptionNumber = this.mod(this.chosenOptionNumber, 3);
        this.rectangleChosenCords[1] = 305 + this.chosenOptionNumber * 70;
    }


    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        this.background.draw(g);
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(255, 255, 255, 140));
        g2.fillRoundRect(400, 250, 300, 300, 30, 30);
        g2.setColor(new Color(1, 11, 64));
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 80));
        g2.drawString("Battle Royal", 315, 150);
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.drawString(GameState.PLAY.getName(), 515, 340);
        g2.drawString(GameState.OPTIONS.getName(), 490, 410);
        g2.drawString(GameState.EXIT.getName(), 515, 480);
        g2.drawRoundRect(this.rectangleChosenCords[0], this.rectangleChosenCords[1], 140, 50, 15, 15);
    }

    public GameState getChosenGameState() {
        return this.options[this.chosenOptionNumber];
    }


}
