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
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(255, 255, 255));
        g2.drawRoundRect(400, 250, 300, 200, 15, 15);
        g2.setColor(new Color(255, 255, 255, 140));
        g2.fillRoundRect(400, 250, 300, 200, 15, 15);
        if (this.chosenOption == ConfirmDialog.YES) {
            g2.setColor(new Color(1, 11, 64));
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(450, 360, 80, 50, 15, 15);
            g2.setStroke(new BasicStroke(2));
            g2.setColor(new Color(255, 255, 255));
            g2.drawRoundRect(570, 360, 80, 50, 15, 15);
        } else {
            g2.setStroke(new BasicStroke(2));
            g2.setColor(new Color(255, 255, 255));
            g2.drawRoundRect(450, 360, 80, 50, 15, 15);
            g2.setStroke(new BasicStroke(5));
            g2.setColor(new Color(1, 11, 64));
            g2.drawRoundRect(570, 360, 80, 50, 15, 15);
        }
        g2.setColor(new Color(1, 11, 64));

        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        if (this.playState == PlayState.WIN) {
            g2.drawString("You Won!", 480, 290);
        } else if (this.playState == PlayState.LOST) {
            g2.drawString("You Lost!", 480, 290);
        } else if (this.playState == PlayState.TIME_OUT) {
            g2.drawString("Time is out!", 450, 290);
        }
        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawString("Do you want to exit?", 440, 320);
        g2.drawString(ConfirmDialog.YES.toString(), 473, 390);
        g2.drawString(ConfirmDialog.NO.toString(), 598, 390);

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
