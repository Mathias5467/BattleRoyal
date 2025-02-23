package gui;


import main.GameState;
import main.Picture;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;

import java.util.HashMap;


public class Menu {
    private Picture background;
    private HashMap<GameState, int[]> options;
    private GameState[] optionsLogic;
    private GameState chosenGameState;
    public Menu() {
        this.background = new Picture(0, 0, 1192, 670, "res/menu.png");
        this.chosenGameState = GameState.PLAY;
        this.options = new HashMap<GameState, int[]>() {{
                put(GameState.PLAY, new int[] {448, 340}); // - 17
                put(GameState.OPTIONS, new int[] {440, 410});
                put(GameState.EXIT, new int[] {465, 480});
            }};
        this.optionsLogic = new GameState[] {GameState.PLAY, GameState.OPTIONS, GameState.EXIT};
    }


    public void selectOption(int direction) {
        switch (this.chosenGameState) {
            case GameState.PLAY -> {
                if (direction < 0) {
                    this.chosenGameState = this.optionsLogic[2];
                } else {
                    this.chosenGameState = this.optionsLogic[direction];
                }
            }
            case GameState.OPTIONS -> {
                this.chosenGameState = this.optionsLogic[1 + direction];
            }
            case GameState.EXIT -> {
                if (2 + direction > 2) {
                    this.chosenGameState = this.optionsLogic[0];
                } else {
                    this.chosenGameState = this.optionsLogic[2 + direction];
                }
            }
        }
        this.choseText();
    }

    public void choseText() {
        switch (this.chosenGameState) {
            case GameState.PLAY -> {
                this.options.put(GameState.PLAY, new int[]{448, 340});
                this.options.put(GameState.OPTIONS, new int[]{440, 410});
                this.options.put(GameState.EXIT, new int[]{465, 480});
            }
            case GameState.OPTIONS -> {
                this.options.put(GameState.PLAY, new int[]{465, 340});
                this.options.put(GameState.OPTIONS, new int[]{423, 410});
                this.options.put(GameState.EXIT, new int[]{465, 480});
            }
            case GameState.EXIT -> {
                this.options.put(GameState.PLAY, new int[]{465, 340});
                this.options.put(GameState.OPTIONS, new int[]{440, 410});
                this.options.put(GameState.EXIT, new int[]{448, 480});
            }

        }
    }

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        this.background.draw(g);
        g2.setColor(new Color(255, 255, 255, 140));
        g2.fillRoundRect(350, 250, 300, 300, 30, 30);
        g2.setColor(new Color(1, 11, 64));
        g2.setFont(new Font("Old English Text MT", Font.BOLD, 80));
        g2.drawString("Battle Royal", 265, 150);
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        for (GameState key : this.options.keySet()) {
            if (key == this.chosenGameState) {
                g2.drawString(key.getChosenName(), this.options.get(key)[0], this.options.get(key)[1]);
            } else {
                g2.drawString(key.getName(), this.options.get(key)[0], this.options.get(key)[1]);
            }
        }
    }

    public GameState getChosenGameState() {
        return this.chosenGameState;
    }


}
