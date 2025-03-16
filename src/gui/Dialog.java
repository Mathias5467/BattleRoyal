package gui;

import main.GameState;
import main.PlayState;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;

public class Dialog {
    private boolean visible;
    private ConfirmDialog[] options;
    private PlayState playState;
    private ConfirmDialog chosenOption;
    private boolean confirmed;
    public Dialog(GameState gameState) {
        this.visible = false;
        this.confirmed = false;
        this.chosenOption = ConfirmDialog.YES;
        this.options = new ConfirmDialog[] {ConfirmDialog.YES, ConfirmDialog.NO};
        this.playState = PlayState.TIE;
    }

    public void changeOption(int direction) {
        this.chosenOption = this.options[direction];
    }

    public void setPlayState(PlayState playState) {
        this.playState = playState;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        // Dialog background
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.WHITE);
        g2.drawRoundRect(400, 250, 300, 200, 15, 15);
        g2.setColor(new Color(255, 255, 255, 140));
        g2.fillRoundRect(400, 250, 300, 200, 15, 15);

        // Buttons - set appropriate styles based on selection
        boolean isYesSelected = this.chosenOption == ConfirmDialog.YES;
        g2.setStroke(new BasicStroke(isYesSelected ? 5 : 2));
        g2.setColor(isYesSelected ? new Color(1, 11, 64) : Color.WHITE);
        g2.drawRoundRect(450, 360, 80, 50, 15, 15);

        g2.setStroke(new BasicStroke(isYesSelected ? 2 : 5));
        g2.setColor(isYesSelected ? Color.WHITE : new Color(1, 11, 64));
        g2.drawRoundRect(570, 360, 80, 50, 15, 15);

        // Text content
        g2.setColor(new Color(1, 11, 64));
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        if (this.playState == PlayState.WIN) {
            g2.drawString("You Won!", 480, 290);
        } else if (this.playState == PlayState.LOST) {
            g2.drawString("You Lost!", 480, 290);
        }

        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawString("Do you want to exit?", 440, 320);
        g2.drawString(ConfirmDialog.YES.toString(), 473, 390);
        g2.drawString(ConfirmDialog.NO.toString(), 598, 390);
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

    public String getChosenOption() {
        return this.chosenOption.toString();
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

}
