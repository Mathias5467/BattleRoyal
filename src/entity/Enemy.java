
package entity;

import main.Picture;

public class Enemy extends Entity {
    private String pictureName;
    public Enemy(EntityType entityType) {

        super(
                800,
                480,
                entityType,
                "Thorne",
                new Picture(820, 450, 200, 220, String.format("res/%s/stayL.png", entityType.toString())),
                String.format("res/%s/stayL.png", entityType.toString()),
                Direction.LEFT,
                new HPBar(730, 80, 100, 800, 70, entityType.toString()),
                2
        );
        this.pictureName = super.getPictureName();
    }
    //TODO: Do hp bar

    @Override
    public String getPictureName() {
        return String.format("res/%s/%s/%s%s%s.png",
                super.getEntity().toString(),
                super.getMovementType().getSymbol(),
                super.getDirection(),
                super.getNumberOfAnimation());
    }

    @Override
    public void attack(Movement movementType) {
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
