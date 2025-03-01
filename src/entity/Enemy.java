
package entity;

import main.Picture;

public class Enemy extends Entity {
    private String pictureName;
    public Enemy() {

        super(
                50,
                480,
                EntityType.KNIGHT,
                KnightColor.RED,
                "Thorne",
                new Picture(50, 480, 150, 170, "res/knight/red/stayR.png"),
                "knight/red/stayL.png",
                Direction.RIGHT,
                new HPBar(50, 80, 100, 50, 70, "Thorne"));
        this.pictureName = super.getPictureName();
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

    public Picture getPicture() {
        return super.getPicture();
    }

//    public HPBar getHpBar() {
//        return super.getHpBar();
//    }

}
