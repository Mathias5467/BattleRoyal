package gui;

import main.GameState;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;

public class Dialog {
    private boolean visible;
    private MessageType type;
    private String[] options;
    private String chosenOption;
    private boolean confirmed;
    public Dialog(GameState gameState) {
        this.type = MessageType.EXIT;
        this.visible = false;
        this.options = new String[] {this.type.getOk(), this.type.getCancel()};
        this.chosenOption = this.type.getOk();
        this.confirmed = false;
    }

    public void changeOption(int direction) {
        this.chosenOption = this.options[direction];
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(255, 255, 255));
        g2.drawRoundRect(400, 250, 300, 200, 15, 15);
        g2.setColor(new Color(255, 255, 255, 140));
        g2.fillRoundRect(400, 250, 300, 200, 15, 15);
        if (this.chosenOption.equals(this.type.getOk())) {
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
        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawString(this.type.getMessage(), 440, 300);
        g2.drawString(this.type.getOk(), 473, 390);
        g2.drawString(this.type.getCancel(), 598, 390);

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

    public String getChosenOption() {
        return this.chosenOption;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
