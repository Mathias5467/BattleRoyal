package main;

import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Trieda {@code Picture} reprezentuje obrázok, ktorý môže byť zobrazený na plátne.
 * Umožňuje nastaviť pozíciu, rozmery, cestu k súboru obrázka a viditeľnosť.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public class Picture {
    private ImageIcon picture;
    private String picturePath;
    private int x;
    private int y;
    private int width;
    private int height;
    private boolean isVisible;

    /**
     * Konštruktor pre vytvorenie inštancie triedy {@code Picture}.
     * Načíta obrázok zo zadanej cesty a nastaví jeho počiatočné vlastnosti.
     * @param x Počiatočná x-ová súradnica obrázka.
     * @param y Počiatočná y-ová súradnica obrázka.
     * @param width Počiatočná šírka obrázka.
     * @param height Počiatočná výška obrázka.
     * @param picturePath Cesta k súboru obrázka.
     */
    public Picture(int  x, int y, int width, int height, String picturePath) {
        this.picturePath = picturePath;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.picture = new ImageIcon(this.picturePath);
        this.isVisible = true;
    }

    /**
     * Zmení súradnice obrázka na nové zadané hodnoty.
     * @param x Nová x-ová súradnica obrázka.
     * @param y Nová y-ová súradnica obrázka.
     */
    public void changeCords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Zmení aktuálne zobrazený obrázok na obrázok zo zadanej novej cesty.
     * @param newPath Nová cesta k súboru obrázka.
     */
    public void changeImage(String newPath) {
        this.picturePath = newPath;
        this.picture = new ImageIcon(this.picturePath);
    }

    /**
     * Vráti aktuálnu x-ovú súradnicu obrázka.
     * @return Aktuálna x-ová súradnica obrázka.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Vráti aktuálnu y-ovú súradnicu obrázka.
     * @return Aktuálna y-ová súradnica obrázka.
     */
    public int getY() {
        return this.y;
    }

    /**
     * Vykreslí obrázok na zadaný grafický kontext, ak je obrázok viditeľný.
     * @param g Grafický kontext, na ktorý sa má obrázok vykresliť.
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        if (this.isVisible) {
            g2.drawImage(this.picture.getImage(), this.x, this.y, this.width, this.height, null);
        }
    }

    /**
     * Zistí, či je obrázok aktuálne viditeľný.
     * @return {@code true}, ak je obrázok viditeľný, inak {@code false}.
     */
    public boolean isVisible() {
        return this.isVisible;
    }

    /**
     * Nastaví viditeľnosť obrázka.
     * @param visible {@code true} pre zobrazenie obrázka, {@code false} pre skrytie.
     */
    public void setVisible(boolean visible) {
        this.isVisible = visible;
    }

    /**
     * Vráti aktuálnu šírku obrázka.
     * @return Aktuálna šírka obrázka.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Vráti aktuálnu výšku obrázka.
     * @return Aktuálna výška obrázka.
     */
    public int getHeight() {
        return this.height;
    }
}