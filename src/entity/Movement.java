package entity;

public enum Movement {
    STAY("stay"),
    WALK("walk"),
    ATTACK1("attack1"),
    ATTACK2("attack2"),
    ATTACK3("attack3"),
    DEFEND("defend"),
    DEATH("death");


    private String symbol;

    Movement(String rep) {
        this.symbol = rep;
    }

    public String getSymbol() {
        return this.symbol;
    }
}
