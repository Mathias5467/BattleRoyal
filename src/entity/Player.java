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
        this.setX(50);
        this.setY(480);
        this.setDirection(Direction.RIGHT);
        this.getPicture().changeCords(this.getX(), this.getY());
    }

    public void setKnight(KnightType knightType) {
        this.knightType = knightType;
        this.getHpBar().setName(this.knightType.getName());
        this.getHpBar().setHP(this.knightType.getHp());
        this.getHpBar().setMaxHP(this.knightType.getHp());
    }

    @Override
    public String getPictureName() {
        return String.format("res/%s/%s/%s%s%s.png",
                this.getEntity().toString(),
                this.knightType.getColor(),
                this.getMovementType().getSymbol(),
                this.getDirection(),
                this.getNumberOfAnimation());
    }


    public KnightType getKnightType() {
        return this.knightType;
    }


    public void setDefending(boolean defending) {
        if (!this.isAttacking()) {
            this.isDefending = defending;
        }
    }

    public void defend() {
        if (!this.isAttacking() && !this.isDead() && !this.isDying()) {
            this.setMovementType(Movement.DEFEND);
            this.setNumberOfAnimation("");
            this.changePicture();
        }
    }

    @Override
    public void hit(int damage) {
        if (this.isDefending) {
            this.getHpBar().reduceHP((int)(damage / 3));
        } else {
            this.getHpBar().reduceHP(damage);
        }
        if (this.getHpBar().getActualHP() == 0) {
            this.death();
        }
    }


}