package entity;

import main.Picture;

public class Player extends Entity {
    private boolean isDefending;
    private KnightType knightType;

    public Player(EntityType entityType, KnightType knightType) {
        super(
                50,
                480,
                entityType,
                knightType.getName(),
                new Picture(50, 480, 150, 170, "res/knight/red/stayR.png"),
                "Knight/red/stayL.png",
                Direction.RIGHT,
                new HPBar(50, 80, knightType.getHp(), 50, 70, knightType.getName()),
                4
        );
        this.knightType = knightType;
        this.isDefending = false;
    }


    public void setStartPosition() {
        super.setX(50);
        super.setY(480);
        super.setDirection(Direction.RIGHT);
        super.getPicture().changeCords(super.getX(), super.getY());
    }

    public void setKnight(KnightType knightType) {
        this.knightType = knightType;
        super.getHpBar().setName(this.knightType.getName());
        super.getHpBar().setHP(this.knightType.getHp());
        super.getHpBar().setMaxHP(this.knightType.getHp());
    }

    @Override
    public String getPictureName() {
        return String.format("res/%s/%s/%s%s%s.png",
                super.getEntity().toString(),
                this.knightType.getColor(),
                super.getMovementType().getSymbol(),
                super.getDirection(),
                super.getNumberOfAnimation());
    }


    public KnightType getKnightType() {
        return this.knightType;
    }


    public void setDefending(boolean defending) {
        if (!super.isAttacking()) {
            this.isDefending = defending;
        }
    }

    public void defend() {
        if (!super.isAttacking() && !super.isDead() && !super.isDying()) {
            super.setMovementType(Movement.DEFEND);
            super.setNumberOfAnimation("");
            super.changePicture();
        }
    }

    @Override
    public void hit(int damage) {
        if (this.isDefending) {
            super.getHpBar().reduceHP((int)(damage / 3));
        } else {
            super.getHpBar().reduceHP(damage);
        }
        if (super.getHpBar().getActualHP() == 0) {
            super.death();
        }
    }


}