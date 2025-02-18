package entity;

public enum Movement {
    STAY("stay"),
    JUMP("jump"),
    WALK("walk"),
    FALL("fall"),
    ATTACK1("attack1"),
    ATTACK2("attack2"),
    ATTACK3("attack3"),
    DEFEND("defend"),
    HIT("hit"),
    DEATH("death");


    private String symbol;

    Movement(String rep) {
        this.symbol = rep;
    }

    public String getSymbol() {
        return this.symbol;
    }
}
