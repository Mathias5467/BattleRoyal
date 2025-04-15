package gui;

import backend.SelectOption;
import main.PlayState;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;

/**
 * Trieda {@code Dialog} reprezentuje dialógové okno, ktoré sa zobrazuje v hre na potvrdenie akcie,
 * napríklad ukončenia hry. Môže tiež zobrazovať správy o výsledku hry.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public class Dialog extends SelectOption {
    private boolean visible;
    private ConfirmDialog[] options;
    private PlayState playState;
    private ConfirmDialog chosenOption;
    private int counter;

    /**
     * Konštruktor triedy {@code Dialog}. Inicializuje viditeľnosť dialógu na {@code false},
     * nastavuje počiatočný výber na "Áno", definuje dostupné možnosti (Áno/Nie)
     * a počiatočný stav hry na remízu.
     */
    public Dialog() {
        this.visible = false;
        this.counter = 0;
        this.chosenOption = ConfirmDialog.YES;
        this.options = new ConfirmDialog[] {ConfirmDialog.YES, ConfirmDialog.NO};
        this.playState = PlayState.TIE;
    }

    /**
     * Posunie výber v dialógovom okne medzi dostupnými možnosťami (Áno/Nie).
     * Aktualizuje index vybranej možnosti a samotnú vybranú možnosť.
     * @param direction Smer posunu (-1 pre doľava/predchádzajúca, 1 pre doprava/nasledujúca).
     */
    @Override
    public void selectOption(int direction) {
        this.counter += direction;
        this.counter = this.mod(this.counter, 2);
        this.chosenOption = this.options[this.counter];
    }

    /**
     * Nastaví stav hry, ktorý sa má zobraziť v dialógovom okne (napr. výhra, prehra, čas vypršal).
     * @param playState Enum {@code PlayState} reprezentujúci stav hry.
     */
    public void setPlayState(PlayState playState) {
        this.playState = playState;
    }

    /**
     * Vykresľuje dialógové okno s otázkou na ukončenie hry a možnosťami Áno/Nie.
     * Ak je nastavený stav hry (výhra, prehra, čas vypršal), zobrazí príslušnú správu.
     * Aktuálne vybraná možnosť je vizuálne zvýraznená.
     * @param g Grafický kontext, na ktorý sa má dialógové okno vykresliť.
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(8));
        g2.setColor(new Color(43, 43, 43));
        g2.fillRoundRect(375, 240, 350, 220, 10, 10);
        g2.setColor(new Color(3, 156, 140));
        g2.drawRoundRect(375, 240, 350, 220, 10, 10);

        g2.setFont(new Font("Consolas", Font.BOLD, 20));
        g2.setStroke(new BasicStroke(5));
        g2.drawString("Do you want to exit?", 440, 320);

        if (this.chosenOption == ConfirmDialog.YES) {
            g2.setColor(new Color(3, 156, 140));
            g2.fillRoundRect(450, 360, 80, 50, 15, 15);
            g2.drawRoundRect(570, 360, 80, 50, 15, 15);
            g2.drawString(ConfirmDialog.NO.toString(), 598, 390);
            g2.setColor(new Color(43, 43, 43));
            g2.drawString(ConfirmDialog.YES.toString(), 473, 390);
        } else {
            g2.setColor(new Color(1, 145, 130));
            g2.fillRoundRect(570, 360, 80, 50, 15, 15);
            g2.drawRoundRect(450, 360, 80, 50, 15, 15);
            g2.drawString(ConfirmDialog.YES.toString(), 473, 390);
            g2.setColor(new Color(43, 43, 43));
            g2.drawString(ConfirmDialog.NO.toString(), 598, 390);
        }

        g2.setColor(new Color(3, 156, 140));
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        switch (this.playState) {
            case WIN -> g2.drawString("You Won!", 480, 290);
            case LOST -> g2.drawString("You Lost!", 480, 290);
            case TIME_OUT -> g2.drawString("Time is out!", 450, 290);
        }

    }

    /**
     * Zisťuje, či je dialógové okno aktuálne viditeľné.
     * @return {@code true}, ak je dialóg viditeľný, inak {@code false}.
     */
    public boolean isVisible() {
        return this.visible;
    }

    /**
     * Nastavuje viditeľnosť dialógového okna.
     * @param visible {@code true} pre zobrazenie dialógu, {@code false} pre skrytie.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Vráti aktuálne vybratú možnosť v dialógovom okne (Áno alebo Nie).
     * @return Enum {@code ConfirmDialog} reprezentujúci zvolenú možnosť.
     */
    public ConfirmDialog getChosenOption() {
        return this.chosenOption;
    }
}