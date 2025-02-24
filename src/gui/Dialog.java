package gui;

import main.GameState;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;

public class Dialog {
    private boolean visible;
    private GameState gameState;
    private ConfirmDialog[] options;
    private ConfirmDialog chosenOption;
    private boolean confirmed;
    public Dialog(GameState gameState) {
        this.gameState = gameState;
        this.visible = false;
        this.options = new ConfirmDialog[] {ConfirmDialog.YES, ConfirmDialog.NO};
        this.chosenOption = ConfirmDialog.YES;
        this.confirmed = false;
    }

    public void changeOption(int direction) {
        this.chosenOption = this.options[direction];
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(255, 255, 255));
        g2.drawRoundRect(350, 250, 300, 200, 15, 15);
        g2.setColor(new Color(255, 255, 255, 140));
        g2.fillRoundRect(350, 250, 300, 200, 15, 15);
        if (this.chosenOption == ConfirmDialog.YES) {
            g2.setColor(new Color(1, 11, 64));
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(400, 360, 80, 50, 15, 15);
            g2.setStroke(new BasicStroke(2));
            g2.setColor(new Color(255, 255, 255));
            g2.drawRoundRect(520, 360, 80, 50, 15, 15);
        } else {
            g2.setStroke(new BasicStroke(2));
            g2.setColor(new Color(255, 255, 255));
            g2.drawRoundRect(400, 360, 80, 50, 15, 15);
            g2.setStroke(new BasicStroke(5));
            g2.setColor(new Color(1, 11, 64));
            g2.drawRoundRect(520, 360, 80, 50, 15, 15);
        }
        g2.setColor(new Color(1, 11, 64));
        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawString("Do you want to EXIT?", 390, 300);
        g2.drawString(ConfirmDialog.YES.toString(), 423, 390);
        g2.drawString(ConfirmDialog.NO.toString(), 548, 390);

    }

    public boolean isVisible() {
        return this.visible;
    }

    public void hide() {
        this.visible = false;
    }

    public void setVisible() {
        this.visible = true;
    }

    public boolean isConfirmed() {
        return this.confirmed;
    }

    public ConfirmDialog getChosenOption() {
        return this.chosenOption;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
