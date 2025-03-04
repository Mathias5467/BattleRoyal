package entity;

import main.Picture;

public class Player extends Entity {
    public boolean isDefending;
    public KnightColor color;
    public Player(EntityType entityType, int maxHP) {
        super(
                50,
                480,
                entityType,
                "Thorne",
                new Picture(50, 480, 150, 170, "res/knight/red/stayR.png"),
                "knight/red/stayL.png",
                Direction.RIGHT,
                new HPBar(50, 80, maxHP, 50, 70, "Thorne"),
                4
        );
        this.color = KnightColor.RED;
        this.isDefending = false;
    }


    @Override
    public String getPictureName() {
        return String.format("res/%s/%s/%s%s%s.png",
                super.getEntity().toString(),
                this.color,
                super.getMovementType().getSymbol(),
                super.getDirection(),
                super.getNumberOfAnimation());
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

    public void setDefending(boolean defending) {
        this.isDefending = defending;
    }

    public void defend() {
        super.setMovementType(Movement.DEFEND);
        super.setNumberOfAnimation("");
        super.changePicture();
    }

    @Override
    public void hit(int damage) {
        if (this.isDefending) {
            super.getHpBar().reduceHP((int)(damage / 3));
        }
    }

}
