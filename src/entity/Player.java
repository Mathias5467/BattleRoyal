package entity;

import main.Picture;

public class Player extends Entity {
    public Player(EntityType entityType, int maxHP) {
        super(
                50,
                480,
                entityType,
                KnightColor.RED,
                "Thorne",
                new Picture(50, 480, 150, 170, "res/knight/red/stayR.png"),
                "knight/red/stayL.png",
                Direction.RIGHT,
                new HPBar(50, 80, maxHP, 50, 70, "Thorne"),
                4
        );
    }


    @Override
    public void attack() {
        int actualAnimationNumber = getActualAnimationNumber();
        setActualAnimationNumber(actualAnimationNumber++);
        if (actualAnimationNumber >= getMaxAnimationNumber()) {
            setActualAnimationNumber(0);
            if (getNumberOfAnimation().equals("")) {
                setNumberOfAnimation("0");
            }
            animation();
        }
    }

}
