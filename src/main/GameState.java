package main;

public enum GameState {
    MENU("Menu"),
    PLAY("Play"),
    EXIT("Exit"),
    KNIGHTS("Knights"),
    MAPS("Map");

    private String name;

    GameState(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
