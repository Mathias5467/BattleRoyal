package entity;

import main.Picture;

public class Player extends Entity {
    private boolean isDefending;
    private KnightType knightType;
    private boolean isAttacking;
    private Movement currentAttack;
    private int attackAnimationCounter;

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
        this.isAttacking = false;
        this.attackAnimationCounter = 0;
    }

    // Existing methods...
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

    public boolean isAttacking() {
        return this.isAttacking;
    }

    public KnightType getKnightType() {
        return this.knightType;
    }

    // Modified attack method to start the animation sequence
    @Override
    public void attack(Movement movementType) {
        if (!this.isAttacking) {
            this.currentAttack = movementType;
            super.setMovementType(movementType);
            super.setActualAnimationNumber(0);
            super.setNumberOfAnimation("0");
            this.isAttacking = true;
            this.attackAnimationCounter = 0;
        }
    }

    // New method to continue the attack animation
    public void updateAttackAnimation() {
        if (this.isAttacking) {
            this.attackAnimationCounter++;

            // Change frame every few game ticks (adjust timing as needed)
            if (this.attackAnimationCounter >= 8) { // Change 5 to adjust animation speed
                this.attackAnimationCounter = 0;

                int nextFrame = super.getActualAnimationNumber() + 1;
                super.setActualAnimationNumber(nextFrame);

                if (nextFrame >= super.getMaxAnimationNumber()) {
                    // End of animation
                    super.setActualAnimationNumber(0);
                    this.isAttacking = false;
                    // Return to idle state
                    super.setMovementType(Movement.STAY);
                    super.setNumberOfAnimation("");
                } else {
                    // Continue to next frame
                    super.animation();
                }
            }
        }
    }



    public void setDefending(boolean defending) {
        if (!this.isAttacking) {
            this.isDefending = defending;
        }
    }

    public void defend() {
        if (!this.isAttacking) {
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
    }

    // Modified movement methods to prevent movement during attack
    @Override
    public void moveRight() {
        if (!this.isAttacking) {
            super.moveRight();
        }
    }

    @Override
    public void moveLeft() {
        if (!this.isAttacking) {
            super.moveLeft();
        }
    }

    @Override
    public void stop() {
        if (!this.isAttacking) {
            super.stop();
        }
    }
}