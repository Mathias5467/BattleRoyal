package entity;

import main.Picture;

public class Player extends Entity {
    private KnightType knightType;

    public Player(EntityType entityType, KnightType knightType) {
        super(
                80,
                480,
                entityType,
                knightType.getName(),
                new Picture(80, 480, 150, 170, "res/knight/red/stayR.png"),
                "Knight/red/stayL.png",
                Direction.RIGHT,
                new HPBar(50, 80, knightType.getHp(), 50, 70, knightType.getName()),
                4
        );
        this.knightType = knightType;
    }

    public void moveWithoutAnimation() {
        this.setX(this.getX() - 2);
        this.getPicture().changeCords(this.getX(), this.getY());
    }

    public KnightType getKnightType() {
        return this.knightType;
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

    public void setDefending(boolean defending) {
        if (!this.isAttacking() && defending) {
            this.setMovementType(Movement.DEFEND);
        }
    }

    public void defend() {
        if (this.mayDoAction()) {
            this.setMovementType(Movement.DEFEND);
            this.setNumberOfAnimation("");
            this.changePicture();
        }
    }

    @Override
    public void hit(int damage) {
        if (this.getMovementType() == Movement.DEFEND) {
            this.getHpBar().reduceHP((int)Math.ceil(((double)(100 - this.knightType.getDefend()) / 100) * damage));
        } else {
            this.getHpBar().reduceHP(damage);
        }
        if (this.getHpBar().getActualHP() == 0) {
            this.death();
        }
    }


}