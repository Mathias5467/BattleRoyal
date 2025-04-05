package gui;

import backend.Biom;
import backend.SelectOption;
import main.Picture;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public class MapOption extends SelectOption {
    private Picture biom1;
    private Picture biom2;
    private Picture biom3;
    private int numberOfCoins;
    private Picture coinPicture;
    private Picture[] bioms;
    private int counter;
    private final HashMap<Biom, Boolean> biomsBought;
    private Biom biom;
    public MapOption() throws FileNotFoundException {
        this.numberOfCoins = 0;
        this.biom = Biom.FOREST;
        this.coinPicture = new Picture(100, 540, 40, 40, "res/coin.png");
        this.biom1 = new Picture(80, 250, 300, 200, "res/back/show/Forest.png");
        this.biom2 = new Picture(400, 250, 300, 200, "res/back/show/Dune.png");
        this.biom3 = new Picture(720, 250, 300, 200, "res/back/show/Mountain.png");
        this.bioms = new Picture[] {this.biom1, this.biom2, this.biom3};
        this.biomsBought = new HashMap<>();
        this.counter = 0;
        File file = new File("res/data/biomsBought.txt");
        Scanner input = new Scanner(file);
        while (input.hasNextLine()) {
            String[] data = input.nextLine().split(" ");
            if (Integer.parseInt(data[1]) == 1) {
                this.biomsBought.put(this.biom.getByName(data[0]), true);
            } else {
                this.biomsBought.put(this.biom.getByName(data[0]), false);
            }

        }
    }

    public void writeIntoFile() throws FileNotFoundException {
        File file = new File("res/data/biomsBought.txt");
        PrintWriter input = new PrintWriter(file);
        for (Biom biomType : this.biomsBought.keySet()) {
            if (this.biomsBought.get(biomType)) {
                input.println(String.format("%s 1", biomType.getName()));
            } else {
                input.println(String.format("%s 0", biomType.getName()));
            }

        }
        input.close();
    }

    public int getNumberOfCoins() {
        return this.numberOfCoins;
    }

    public boolean tryBuy() {
        if (this.biom.getPrice() <= this.numberOfCoins) {
            this.biomsBought.put(this.biom, true);
            this.numberOfCoins -= this.biom.getPrice();
            return true;
        }
        return false;
    }

    public boolean tryChoose() {
        return this.biomsBought.get(this.biom);
    }


    public void setNumberOfCoins(int numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }


    @Override
    public void selectOption(int direction) {
        this.counter += direction;
        this.counter = this.mod(this.counter, 3);
        Biom[] values = Biom.values();
        this.biom = values[this.counter % 3];
    }

    public Biom getBiom() {
        return this.biom;
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        this.coinPicture.draw(g2);
        for (int i = 0; i < this.bioms.length; i++) {
            Picture picture = this.bioms[i];
            picture.draw(g);
            Biom currentBiom = null;
            switch (i) {
                case 0 -> currentBiom = Biom.FOREST;
                case 1 -> currentBiom = Biom.DUNE;
                case 2 -> currentBiom = Biom.MOUNTAIN;
            }
            if (currentBiom != null && !this.biomsBought.get(currentBiom)) {
                g2.setColor(new Color(0, 0, 0, 150)); // Black with 150/255 opacity
                g2.fillRoundRect(picture.getX(), picture.getY(), picture.getWidth(), picture.getHeight(), 20, 20);

                String priceText = currentBiom.getPrice() + "$";
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 30));
                FontMetrics metrics = g2.getFontMetrics();
                int textX = picture.getX() + (picture.getWidth() - metrics.stringWidth(priceText)) / 2;
                int textY = picture.getY() + (picture.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();

                // Draw the price text
                g2.drawString(priceText, textX, textY);
            }
            if (currentBiom != null) {
                if (currentBiom == this.biom) {
                    g2.setStroke(new BasicStroke(8));
                    g2.setColor(new Color(219, 144, 24));
                } else {
                    g2.setStroke(new BasicStroke(5));
                    g2.setColor(Color.WHITE);
                }

                g2.drawRoundRect(picture.getX(), picture.getY(), picture.getWidth(), picture.getHeight(), 20, 20);
            }


        }

        g2.setColor(new Color(255, 255, 255, 90));
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        g2.drawString("Press ENTER to save changes", 385, 550);
        g2.drawString("Press ESC to get back to menu", 373, 590);
        g2.drawString(String.format("x %d", this.numberOfCoins), 150, 565);
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 50));
        g2.drawString("Choose Biom", 405, 130);

    }
}
