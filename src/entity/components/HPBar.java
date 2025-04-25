package entity.components;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;

/**
 * Trieda {@code HPBar} reprezentuje grafický ukazovateľ životov (HP) entity v hre.
 * Zobrazuje aktuálne a maximálne HP formou farebného obdĺžnika a zobrazuje meno entity.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public class HPBar {
    private final int x;
    private final int y;
    private int actualHP;
    private final int maximalHP;
    private int width;
    private final int maxWidth;
    private final int height;
    private final int tileSize;
    private String name;
    private final int textX;
    private final int textY;

    /**
     * Konštruktor triedy {@code HPBar}. Inicializuje pozíciu, maximálne HP, pozíciu textu s menom a meno entity.
     * Nastavuje počiatočné aktuálne HP na maximálnu hodnotu a vypočítava počiatočnú šírku ukazovateľa.
     * @param x X-ová súradnica ľavého horného rohu ukazovateľa.
     * @param y Y-ová súradnica ľavého horného rohu ukazovateľa.
     * @param maximalHP Maximálna hodnota životov entity.
     * @param textX X-ová súradnica pre zobrazenie mena entity.
     * @param textY Y-ová súradnica pre zobrazenie mena entity.
     * @param name Meno entity, ktorej HP bar patrí.
     */
    public HPBar(int x, int y, int maximalHP, int textX, int textY, String name) {
        this.maximalHP = maximalHP;
        this.actualHP = this.maximalHP;
        this.tileSize = 3;
        this.name = name;
        this.x = x;
        this.y = y;
        this.textX = textX;
        this.textY = textY;
        this.resetWidth();
        this.maxWidth = this.maximalHP * this.tileSize;
        this.height = 30;
    }

    /**
     * Vráti aktuálnu hodnotu životov.
     * @return Aktuálna hodnota životov.
     */
    public int getActualHP() {
        return this.actualHP;
    }

    /**
     * Vykresľuje HP bar na zadaný grafický kontext. Zobrazuje farebný obdĺžnik reprezentujúci aktuálne HP,
     * čierny rám pre maximálne HP a meno entity.
     * @param g Grafický kontext, na ktorý sa má HP bar vykresliť.
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.RED);
        g2.fillRoundRect(this.x, this.y, this.width, this.height, 10, 10);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(this.x, this.y, this.maxWidth, this.height, 10, 10);
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 30));
        g2.drawString(this.name, this.textX, this.textY);
    }

    /**
     * Nastaví meno entity zobrazené pri HP bare.
     * @param name Nové meno entity.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Zníži aktuálnu hodnotu životov o zadanú hodnotu. Ak by výsledné HP bolo záporné, nastaví sa na 0.
     * Následne prepočíta šírku HP baru.
     * @param downHP Hodnota, o ktorú sa majú životy znížiť.
     */
    public void reduceHP(int downHP) {
        if (this.actualHP - downHP > 0) {
            this.actualHP -= downHP;
        } else {
            this.actualHP = 0;
        }
        this.resetWidth();
    }

    /**
     * Prepočíta aktuálnu šírku farebného ukazovateľa na základe aktuálnej hodnoty HP.
     */
    private void resetWidth() {
        this.width = this.actualHP * this.tileSize;
    }

    /**
     * Obnoví aktuálnu hodnotu životov na maximálnu hodnotu a prepočíta šírku HP baru.
     */
    public void resetHP() {
        this.actualHP = this.maximalHP;
        this.resetWidth();
    }
}