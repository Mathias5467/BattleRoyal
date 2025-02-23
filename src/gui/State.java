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
    private GameState chosenGameState;
    public State() {
        this.background = new Picture(0, 0, 1192, 670, "res/state.png");
        this.chosenGameState = GameState.PLAY;
        this.options = new HashMap<GameState, int[]>() {{
                put(GameState.PLAY, new int[] {448, 340}); // - 17
                put(GameState.OPTIONS, new int[] {440, 410});
                put(GameState.EXIT, new int[] {465, 480});
            }};

    }

    public void goUp() {
        switch (this.chosenGameState) {
            case GameState.PLAY -> {
                this.chosenGameState = GameState.EXIT;
            }
            case GameState.OPTIONS -> {
                this.chosenGameState = GameState.PLAY;
            }
            case GameState.EXIT -> {
                this.chosenGameState = GameState.OPTIONS;
            }
        }
        this.choseText();
    }


    //TODO: Refactor this methods changing text in game state
    public void goDown() {
        switch (this.chosenGameState) {
            case GameState.PLAY -> {
                this.chosenGameState = GameState.OPTIONS;
            }
            case GameState.OPTIONS -> {
                this.chosenGameState = GameState.EXIT;
            }
            case GameState.EXIT -> {
                this.chosenGameState = GameState.PLAY;
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
        g2.setColor(new Color(0, 0, 0, 140));
        g2.fillRoundRect(350, 250, 300, 300, 30, 30);
        g2.setFont(new Font("Consolas", Font.BOLD, 30));
        g2.setColor(new Color(184, 134, 11));
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
