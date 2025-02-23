package input;

public enum KeyType {
    UP("up"),
    DOWN("down"),
    LEFT("left"),
    RIGHT("right"),
    M("m"),
    ESC("esc"),
    ENTER("enter");

    private final String shorthand;
    KeyType(String shorthand) {
        this.shorthand = shorthand;
    }


    public String getShorthand() {
        return this.shorthand;
    }
}
