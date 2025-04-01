package entity;

import main.Picture;

public class Player extends Entity {
    private KnightType knightType;

    public Player(EntityType entityType, KnightType knightType) {
        super(
                100,
                495,
                entityType,
                new Picture(100, 495, 150, 170, "res/Knight/red/stayR.png"),
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


    public void setKnight(KnightType knightType) {
        this.knightType = knightType;
        this.getHpBar().setName(this.knightType.getName());
        this.getHpBar().setHP(this.knightType.getHp());
    }

    @Override
    public String getPictureName() {
        return String.format("res/%s/%s/%s%s%s.png",
                this.getEntityType().toString(),
                this.knightType.getColor(),
                this.getMovementType().getSymbol(),
                this.getDirection(),
                this.getNumberOfAnimation());
    }


    public boolean mayStop() {
        return !this.isDead() && !this.isAttacking() && !this.isDying();
    }


    public void defend() {
        if (!this.isAttacking() && !this.isDead() && !this.isDying()) {
            this.setMovementType(Movement.DEFEND);
            this.resetNumberOfAnimation();
            this.changePicture();
        }
    }

    @Override
    public void hit(int damage) {
        if (this.getMovementType() == Movement.DEFEND) {
            this.getHpBar().reduceHP((int)Math.ceil(((double)(100 - this.knightType.getDefence()) / 100) * damage));
        } else {
            this.getHpBar().reduceHP(damage);
        }
        if (this.getHpBar().getActualHP() == 0) {
            this.death();
        }
    }


    public KnightType getKnightType() {
        return this.knightType;
    }
}