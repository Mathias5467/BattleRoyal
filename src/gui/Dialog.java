package gui;

import backend.SelectOption;
import main.PlayState;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;

public class Dialog extends SelectOption {
    private boolean visible;
    private ConfirmDialog[] options;
    private PlayState playState;
    private ConfirmDialog chosenOption;
    private int counter;

    public Dialog() {
        this.visible = false;
        this.counter = 0;
        this.chosenOption = ConfirmDialog.YES;
        this.options = new ConfirmDialog[] {ConfirmDialog.YES, ConfirmDialog.NO};
        this.playState = PlayState.TIE;
    }

    @Override
    public void selectOption(int direction) {
        this.counter += direction;
        this.counter = this.mod(this.counter, 2);
        this.chosenOption = this.options[this.counter];
    }

    public void setPlayState(PlayState playState) {
        this.playState = playState;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(8));
        g2.setColor(new Color(43, 43, 43));
        g2.fillRoundRect(375, 240, 350, 220, 10, 10);
        g2.setColor(new Color(142, 37, 29));
        g2.drawRoundRect(375, 240, 350, 220, 10, 10);

        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.setStroke(new BasicStroke(5));
// Draw YES button
        if (this.chosenOption == ConfirmDialog.YES) {
            g2.setColor(new Color(142, 37, 29));
            g2.fillRoundRect(450, 360, 80, 50, 15, 15);
            g2.setColor(new Color(43, 43, 43));
            g2.drawString(ConfirmDialog.YES.toString(), 473, 390);
        } else {
            g2.drawRoundRect(450, 360, 80, 50, 15, 15);
            g2.drawString(ConfirmDialog.YES.toString(), 473, 390);
        }

// Draw NO button
        if (this.chosenOption == ConfirmDialog.NO) {
            g2.setColor(new Color(142, 37, 29));
            g2.fillRoundRect(570, 360, 80, 50, 15, 15);
            g2.setColor(new Color(43, 43, 43));
            g2.drawString(ConfirmDialog.NO.toString(), 598, 390);
        } else {
            g2.setColor(new Color(142, 37, 29));
            g2.drawRoundRect(570, 360, 80, 50, 15, 15);
            g2.drawString(ConfirmDialog.NO.toString(), 598, 390);
        }
        g2.setColor(new Color(142, 37, 29));
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        switch (this.playState) {
            case WIN -> g2.drawString("You Won!", 480, 290);
            case LOST -> g2.drawString("You Lost!", 480, 290);
            case TIME_OUT -> g2.drawString("Time is out!", 450, 290);
        }

        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawString("Do you want to exit?", 440, 320);

    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public ConfirmDialog getChosenOption() {
        return this.chosenOption;
    }


}
