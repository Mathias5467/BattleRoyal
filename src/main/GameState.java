package main;

public enum GameState {
    MENU("Menu"),
    STATE("Status"),
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

    public String getChosenName() {
        return String.format(">%s<", this.name);
    }
}
