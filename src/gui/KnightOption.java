package gui;


import backend.SelectOption;
import entity.KnightType;
import main.Picture;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.BasicStroke;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;


public class KnightOption extends SelectOption {
    private final Picture knightPicture;
    private final Picture coinPicture;
    private KnightType knightType;
    private static final int TILE = 3;
    private int counter;
    private int numberOfCoins;
    private final HashMap<KnightType, Boolean> knightsBought;
    public KnightOption() throws FileNotFoundException {
        this.knightType = KnightType.RED;
        this.knightPicture = new Picture(665, 220, 270, 290, String.format("res/knight/%s/stayL.png", this.knightType.getColor()));
        this.knightsBought = new HashMap<>();
        File file = new File("res/data/knightsBought.txt");
        Scanner input = new Scanner(file);
        while (input.hasNextLine()) {
            String[] data = input.nextLine().split(" ");
            if (Integer.parseInt(data[1]) == 1) {
                this.knightsBought.put(this.knightType.getBasedColor(data[0]), true);
            } else {
                this.knightsBought.put(this.knightType.getBasedColor(data[0]), false);
            }

        }
        input.close();
        this.counter = 0;
        this.numberOfCoins = 0;
        this.coinPicture = new Picture(95, 120, 40, 40, "res/coin.png");
    }



    public void writeIntoFile() throws FileNotFoundException {
        File file = new File("res/data/knightsBought.txt");
        PrintWriter input = new PrintWriter(file);
        for (KnightType type : this.knightsBought.keySet()) {
            if (this.knightsBought.get(type)) {
                input.println(String.format("%s 1", type.getColor()));
            } else {
                input.println(String.format("%s 0", type.getColor()));
            }

        }
        input.close();
    }

    public int getNumberOfCoins() {
        return this.numberOfCoins;
    }

    public boolean tryBuy() {
        if (this.knightType.getPrice() <= this.numberOfCoins) {
            this.knightsBought.put(this.knightType, true);
            this.numberOfCoins -= this.knightType.getPrice();
            this.knightPicture.changeImage(String.format("res/knight/%s/stayL.png", this.knightType.getColor()));
            return true;
        }
        return false;
    }

    public boolean tryChoose() {
        return this.knightsBought.get(this.knightType);
    }


    public void setNumberOfCoins(int numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }


    public KnightType getKnightType() {
        return this.knightType;
    }

    @Override
    public void selectOption(int direction) {
        this.counter += direction;
        this.counter = this.mod(this.counter, 3);
        KnightType[] values = KnightType.values();
        this.knightType = values[this.counter % 3];
        if (!this.knightsBought.get(this.knightType)) {
            this.knightPicture.changeImage("res/lock.png");
        } else {
            this.knightPicture.changeImage(String.format("res/knight/%s/stayL.png", this.knightType.getColor()));
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
        g2.drawString("x " + this.numberOfCoins, 150, 145);
        g2.drawString("HP", 100, 200);
        g2.drawString("Attack", 100, 300);
        g2.drawString("Defence", 100, 400);
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        g2.drawString("Press ENTER to save changes", 125, 510);
        g2.drawString("Press ESC to get back to menu", 110, 550);
        g2.setColor(new Color(17, 72, 7));
        g2.fillRect(100, 210, this.knightType.getHp() * KnightOption.TILE, 20);
        g2.setColor(new Color(142, 37, 29));
        g2.fillRect(100, 310, this.knightType.getAttack() * KnightOption.TILE, 20);
        g2.setColor(new Color(1, 48, 94));
        g2.fillRect(100, 410, this.knightType.getDefence() * KnightOption.TILE, 20);
        g2.setColor(new Color(255, 255, 255, 90));
        g2.drawRect(100, 210, 300, 20);
        g2.drawRect(100, 310, 300, 20);
        g2.drawRect(100, 410, 300, 20);
        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawString(String.format("%3d/100", this.knightType.getHp()), 420, 225);
        g2.drawString(String.format("%3d/100", this.knightType.getAttack()), 420, 325);
        g2.drawString(String.format("%3d/100", this.knightType.getDefence()), 420, 425);
        g2.setFont(new Font("Segoe Print", Font.BOLD, 90));
        g2.setColor(new Color(43, 43, 43));
        g2.drawString("<", 615, 420);
        g2.drawString(">", 935, 420);
        g2.setFont(new Font("Consolas", Font.BOLD, 50));
        g2.drawString(this.knightType.getName(), 715, 180);
        if (!this.knightsBought.get(this.knightType)) {
            g2.setFont(new Font("Courier New", Font.BOLD, 40));
            g2.setColor(new Color(255, 255, 255, 150));
            g2.drawString(String.format("%d$", this.knightType.getPrice()), 765, 435);
        }
    }




}
