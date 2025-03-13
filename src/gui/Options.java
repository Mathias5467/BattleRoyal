package gui;


import entity.KnightType;
import main.GameState;
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


public class Options {

    private Picture knightPicture;
    private Picture coinPicture;
    private KnightType knightType;
    private final int tile = 3;
    private int counter;
    private int numberOfCoins;
    private HashMap<KnightType, Boolean> knightsBought;
    public Options() throws FileNotFoundException {
        this.knightType = KnightType.RED;
        this.knightPicture = new Picture(650, 200, 300, 320, this.getPathToImage());
        this.knightsBought = new HashMap<KnightType, Boolean>();
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
        this.coinPicture = new Picture(95, 490, 40, 40, "res/coin.png");
    }

    public boolean tryBuy() {
        if (this.knightType.getPrice() <= this.numberOfCoins) {
            this.knightsBought.put(this.knightType, true);
            this.numberOfCoins -= this.knightType.getPrice();
            this.knightPicture.changeImage(this.getPathToImage());
            return true;
        }
        return false;
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

    public boolean tryChoose() {
        if (!this.knightsBought.get(this.knightType)) {
            return this.tryBuy();
        }
        return true;
    }


    public void setNumberOfCoins(int numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    public String getPathToImage() {
        return String.format("res/knight/%s/stayL.png", this.knightType.getColor());
    }

    public KnightType getKnightType() {
        return this.knightType;
    }

    public void changeColor(int direction) {
        this.counter += direction;
        this.counter = this.mod(this.counter, 3);
        KnightType[] values = KnightType.values();
        this.knightType = values[this.counter % 3];
        if (!this.knightsBought.get(this.knightType)) {
            this.knightPicture.changeImage("res/lock.png");
        } else {
            this.knightPicture.changeImage(this.getPathToImage());
        }
    }
    public int mod(int a, int b) {
        return (a % b < 0) ? (a % b) + Math.abs(b) : (a % b);
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;

        //card
        g2.setColor(new Color(255, 255, 255, 80));
        g2.fillRoundRect(600, 100, 400, 500, 30, 30);
        this.knightPicture.draw(g);
        this.coinPicture.draw(g);

        g2.setStroke(new BasicStroke(5));
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.drawString("HP", 100, 160);
        g2.drawString("Attack", 100, 280);
        g2.drawString("Defend", 100, 400);
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        g2.drawString("Press ENTER to save changes", 110, 570);
        g2.drawString("Press ESC to get back to menu", 95, 610);
        g2.setColor(new Color(17, 72, 7));
        g2.fillRect(100, 180, this.knightType.getHp() * this.tile, 30);
        g2.setColor(new Color(142, 37, 29));
        g2.fillRect(100, 300, this.knightType.getAttack() * this.tile, 30);
        g2.setColor(new Color(1, 48, 94));
        g2.fillRect(100, 420, this.knightType.getDefend() * this.tile, 30);

        g2.setColor(new Color(255, 255, 255, 80));
        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.drawString(String.format("x %d", this.numberOfCoins), 150, 515);
        g2.drawRect(100, 180, 300, 30);
        g2.drawString(String.format("%d/%d", this.knightType.getHp(), 100), 420, 200);
        g2.drawRect(100, 300, 300, 30);
        g2.drawString(String.format("%d/%d", this.knightType.getAttack(), 100), 420, 320);
        g2.drawRect(100, 420, 300, 30);
        g2.drawString(String.format("%d/%d", this.knightType.getDefend(), 100), 420, 440);
        g2.setColor(new Color(43, 43, 43));
        g2.setFont(new Font("Segoe Print", Font.BOLD, 90));
        g2.drawString("<", 610, 380);
        g2.drawString(">", 940, 380);
        g2.setFont(new Font("Consolas", Font.BOLD, 50));
        g2.drawString(this.knightType.getName(), 720, 170);
        g2.setColor(new Color(217, 174, 4));
        if (!this.knightsBought.get(this.knightType)) {
            g2.setFont(new Font("Courier New", Font.BOLD, 35));
            g2.drawString(String.format("%d$", this.knightType.getPrice()), 775, 570);
        }



    }




}
