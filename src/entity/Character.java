package entity;
import main.Picture;

public abstract class Character {

    private int x;
    private int y;
    private Picture picture;
    private HPBar hpBar;
    private Direction direction;
    private Movement movement;
    public Character(int x, int y, Picture picture, Direction direction, HPBar hpBar) {
        this.x = x;
        this.y = y;
        this.picture = picture;
        this.direction = direction;
        this.movement = Movement.STAY;
        this.hpBar = hpBar;
    }


}
