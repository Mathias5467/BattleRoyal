package gui;

import gui.utilities.Biom;
import gui.utilities.Options;
import components.Picture;

import java.io.FileNotFoundException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Color;
import java.awt.BasicStroke;

/**
 * Trieda {@code MapOptions} rozširuje {@code Options} a poskytuje obrazovku pre výber biomu (mapy) v hre.
 * Zobrazuje dostupné biomy, ich náhľady a cenu (ak ešte neboli zakúpené).
 * Umožňuje hráčovi vybrať si biom alebo si ho zakúpiť, ak má dostatok mincí.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public class MapOptions extends Options<Biom> {
    private final Picture coinPicture;
    private final Picture[] bioms;

    /**
     * Konštruktor triedy {@code MapOptions}. Inicializuje obrazovku s možnosťami výberu biomu,
     * načítava obrázky biomov a ikonu mince. Volá konštruktor rodičovskej triedy {@code Options}
     * s informáciami o zakúpených biomoch.
     * @param numberOfCoins Aktuálny počet mincí hráča.
     */
    public MapOptions(int numberOfCoins) throws FileNotFoundException {
        super(numberOfCoins, "biomsBought", Biom.FOREST);
        this.coinPicture = new Picture(100, 540, 40, 40, "res/coin.png");
        Picture biom1 = new Picture(80, 250, 300, 200, "res/back/show/Forest.png");
        Picture biom2 = new Picture(400, 250, 300, 200, "res/back/show/Dune.png");
        Picture biom3 = new Picture(720, 250, 300, 200, "res/back/show/Mountain.png");
        this.bioms = new Picture[] {biom1, biom2, biom3};
    }

    /**
     * Vykresľuje obrazovku s možnosťami výberu biomu. Zobrazuje náhľady biomov,
     * cenu nezakúpených biomov, ikonu mince a informácie o ovládaní. Aktuálne vybratý biom je orámovaný.
     * @param g Grafický kontext, na ktorý sa má obrazovka vykresliť.
     */
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
            if (currentBiom != null && !this.getOptionsBought().get(currentBiom)) {
                g2.setColor(new Color(0, 0, 0, 150));
                g2.fillRoundRect(picture.getX(), picture.getY(), picture.getWidth(), picture.getHeight(), 20, 20);
                String priceText = currentBiom.getPrice() + "$";
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 30));
                FontMetrics metrics = g2.getFontMetrics();
                int textX = picture.getX() + (picture.getWidth() - metrics.stringWidth(priceText)) / 2;
                int textY = picture.getY() + (picture.getHeight() - metrics.getHeight()) / 2 + metrics.getAscent();
                g2.drawString(priceText, textX, textY);
            }
            if (currentBiom != null) {
                if (currentBiom == this.getOption()) {
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
        g2.drawString(String.format("x %d", this.getNumberOfCoins()), 150, 565);
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 50));
        g2.drawString("Choose Biom", 405, 130);
    }
}