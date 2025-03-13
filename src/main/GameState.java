package main;

public enum GameState {
    MENU("Menu"),
    PLAY("Play"),
    EXIT("Exit"),
    OPTIONS("Options");

    private String name;

    GameState(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
