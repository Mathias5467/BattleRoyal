package gui;


import gui.utilities.SelectOption;
import state.GameState;
import components.Picture;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;

/**
 * Trieda {@code Menu} reprezentuje hlavné menu hry. Umožňuje výber rôznych herných stavov
 * pomocou vizuálneho zvýraznenia aktuálne vybranej možnosti.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public class Menu extends SelectOption {
    private final Picture background;
    private final GameState[] options;
    private final int[] rectangleChosenCords;
    private int chosenOptionNumber;

    /**
     * Konštruktor triedy {@code Menu}. Inicializuje pozadie menu, dostupné herné stavy,
     * počiatočné súradnice zvýrazňovacieho obdĺžnika a index aktuálne vybranej možnosti.
     */
    public Menu() {
        this.background = new Picture(0, 0, 1245, 700, "res/back/menu.png");
        this.options = new GameState[] {GameState.PLAY, GameState.KNIGHTS, GameState.MAPS, GameState.EXIT};
        this.rectangleChosenCords = new int[] {478, 295};
        this.chosenOptionNumber = 0;
    }

    /**
     * Posunie výber v menu daným smerom (nahor alebo nadol). Aktualizuje index vybranej možnosti
     * a súradnice zvýrazňovacieho obdĺžnika.
     * @param direction Smer posunu (-1 pre nahor, 1 pre nadol).
     */
    @Override
    public void selectOption(int direction) {
        this.chosenOptionNumber += direction;
        this.chosenOptionNumber = this.mod(this.chosenOptionNumber, 4);
        this.rectangleChosenCords[1] = 295 + this.chosenOptionNumber * 70;
    }

    /**
     * Vykresľuje pozadie menu, rám, názov hry a dostupné možnosti s vizuálnym zvýraznením
     * aktuálne vybranej možnosti.
     * @param g Grafický kontext, na ktorý sa má menu vykresliť.
     */
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        this.background.draw(g);
        g2.setStroke(new BasicStroke(8));
        g2.setColor(new Color(43, 43, 43));
        g2.fillRoundRect(400, 250, 300, 350, 30, 30);
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 80));
        g2.drawString("Battle Royal", 315, 150);
//        g2.setColor(new Color(3, 156, 140));
        g2.setColor(new Color(219, 144, 24));
        g2.drawRoundRect(400, 250, 300, 350, 30, 30);
        g2.setStroke(new BasicStroke(5));
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.drawString(GameState.PLAY.getName(), 515, 330);
        g2.drawString(GameState.KNIGHTS.getName(), 490, 400);
        g2.drawString(GameState.MAPS.getName(), 523, 470);
        g2.drawString(GameState.EXIT.getName(), 515, 540);
        g2.drawRoundRect(this.rectangleChosenCords[0], this.rectangleChosenCords[1], 140, 50, 15, 15);
    }

    /**
     * Vráti herný stav zodpovedajúci aktuálne vybranej možnosti v menu.
     * @return Enum {@code GameState} reprezentujúci zvolený herný stav.
     */
    public GameState getChosenGameState() {
        return this.options[this.chosenOptionNumber];
    }
}