package gui;

import main.GameState;
import main.Picture;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.util.HashMap;


public class State {
    private Picture background;
    private HashMap<GameState, int[]> options;
    public State() {
        this.background = new Picture(0, 0, 1192, 670, "res/state.png");

        this.options = new HashMap<GameState, int[]>() {{
                put(GameState.PLAY, new int[] {465, 340});
                put(GameState.OPTIONS, new int[] {440, 410});
                put(GameState.EXIT, new int[] {465, 480});
            }};

    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        this.background.draw(g);
        g2.setColor(new Color(255, 255, 255, 140));
        g2.fillRoundRect(350, 250, 300, 300, 30, 30);
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.setColor(Color.BLACK);
        for (GameState key : this.options.keySet()) {
            g2.drawString(key.getName(), this.options.get(key)[0], this.options.get(key)[1]);
        }
    }


}
