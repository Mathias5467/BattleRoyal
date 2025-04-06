package gui;

import entity.KnightType;
import main.Picture;

import java.awt.*;
import java.io.FileNotFoundException;

public class KnightOptions extends Options<KnightType> {
    private final Picture knightPicture;
    private final Picture coinPicture;
    private static final int TILE = 3;

    public KnightOptions(int numberOfCoins) throws FileNotFoundException {
        super(numberOfCoins, "knightsBought", KnightType.RED);
        this.knightPicture = new Picture(665, 220, 270, 290, String.format("res/knight/%s/stayL.png", this.getOption().getColor()));
        this.coinPicture = new Picture(95, 120, 40, 40, "res/coin.png");
    }

    @Override
    public void selectOption(int direction) {
        super.selectOption(direction);

        // Update the knight picture based on selection
        if (!this.getOptionsBought().get(this.getOption())) {
            this.knightPicture.changeImage("res/lock.png");
        } else {
            this.knightPicture.changeImage(String.format("res/knight/%s/stayL.png", this.getOption().getColor()));
        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(new Color(255, 255, 255, 90));
        g2.fillRoundRect(600, 100, 400, 500, 30, 30);
        this.knightPicture.draw(g);
        this.coinPicture.draw(g);
        g2.setFont(new Font("Consolas", Font.BOLD, 25));
        g2.drawString("x " + this.getNumberOfCoins(), 150, 145);
        g2.drawString("HP", 100, 200);
        g2.drawString("Attack", 100, 300);
        g2.drawString("Defence", 100, 400);
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        g2.drawString("Press ENTER to save changes", 125, 510);
        g2.drawString("Press ESC to get back to menu", 113, 550);
        g2.setColor(new Color(17, 72, 7));
        g2.fillRect(100, 210, this.getOption().getHp() * KnightOptions.TILE, 20);
        g2.setColor(new Color(142, 37, 29));
        g2.fillRect(100, 310, this.getOption().getAttack() * KnightOptions.TILE, 20);
        g2.setColor(new Color(1, 48, 94));
        g2.fillRect(100, 410, this.getOption().getDefence() * KnightOptions.TILE, 20);
        g2.setColor(new Color(255, 255, 255, 90));
        g2.drawRect(100, 210, 300, 20);
        g2.drawRect(100, 310, 300, 20);
        g2.drawRect(100, 410, 300, 20);
        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawString(String.format("%3d/100", this.getOption().getHp()), 420, 225);
        g2.drawString(String.format("%3d/100", this.getOption().getAttack()), 420, 325);
        g2.drawString(String.format("%3d/100", this.getOption().getDefence()), 420, 425);
        g2.setFont(new Font("Segoe Print", Font.BOLD, 90));
        g2.setColor(new Color(43, 43, 43));
        g2.drawString("<", 615, 420);
        g2.drawString(">", 935, 420);
        g2.setFont(new Font("Consolas", Font.BOLD, 50));
        g2.drawString(this.getOption().getName(), 715, 180);
        if (!this.getOptionsBought().get(this.getOption())) {
            g2.setFont(new Font("Courier New", Font.BOLD, 40));
            g2.setColor(new Color(255, 255, 255, 150));
            g2.drawString(String.format("%d$", this.getOption().getPrice()), 765, 435);
        }
    }

}