package gui;

import backend.Biom;
import backend.SelectOption;
import entity.KnightType;
import main.Picture;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class MapOption extends SelectOption {
    private Picture biom1;
    private Picture biom2;
    private Picture biom3;
    private Picture[] bioms;
    private int counter;
    private final HashMap<Biom, Boolean> biomsBought;
    private Biom biom;
    public MapOption() throws FileNotFoundException {
        this.biom = Biom.FOREST;
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

    @Override
    public void selectOption(int direction) {
        this.counter += direction;
        this.counter = this.mod(this.counter, 3);
        Biom[] values = Biom.values();
        this.biom = values[this.counter % 3];
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
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
            }
            if (currentBiom != null) {
                if (currentBiom == this.biom) {
                    g2.setStroke(new BasicStroke(8));
                } else {
                    g2.setStroke(new BasicStroke(5));
                }
                g2.setColor(Color.WHITE);
                g2.drawRoundRect(picture.getX(), picture.getY(), picture.getWidth(), picture.getHeight(), 20, 20);
            }


        }

        g2.setColor(new Color(255, 255, 255, 90));
        g2.setFont(new Font("Courier New", Font.BOLD, 20));
        g2.drawString("Press ENTER to save changes", 385, 550);
        g2.drawString("Press ESC to get back to menu", 373, 590);
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 50));
        g2.drawString("Choose Biom", 405, 130);
    }
}
