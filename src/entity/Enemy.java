package entity;

import main.Picture;

public class Enemy extends Entity {
    private String pictureName;
    private boolean isDying;
    private int deathAnimationCounter;
    private boolean isAttacking;
    private int attackAnimationCounter;
    public Enemy(EntityType entityType) {

        super(
                800,
                480,
                entityType,
                entityType.toString(),
                new Picture(820, 450, 200, 220, String.format("res/%s/stayL.png", entityType.toString())),
                String.format("res/%s/stayL.png", entityType.toString()),
                Direction.LEFT,
                new HPBar(750, 80, 100, 925, 70, entityType.toString()),
                2
        );
        this.pictureName = super.getPictureName();
        this.isDying = false;
        this.deathAnimationCounter = 0;

        this.isAttacking = false;
        this.attackAnimationCounter = 0;
    }
    //TODO: Do hp bar

    @Override
    public String getPictureName() {
        return String.format("res/%s/%s%s%s.png",
                super.getEntity().toString(),
                super.getMovementType().getSymbol(),
                super.getDirection(),
                super.getNumberOfAnimation());
    }


    @Override
    public void death() {
        if (!this.isDying) {
            super.setMovementType(Movement.DEATH);
            super.setActualAnimationNumber(0);
            super.setNumberOfAnimation("0");
            this.isDying = true;
            this.deathAnimationCounter = 0;
        }
    }

    public boolean isDying() {
        return this.isDying;
    }

    // Modified attack method to start the animation sequence
    @Override
    public void attack(Movement movementType) {
        if (!this.isAttacking && super.getMovementType() != Movement.DEATH) {
            super.setMovementType(movementType);
            super.setActualAnimationNumber(0);
            super.setNumberOfAnimation("0");
            this.isAttacking = true;
            this.attackAnimationCounter = 0;
        }
    }

    public boolean isAttacking() {
        return this.isAttacking;
    }

    public void update() {
        if (this.isDying) {
            this.deathAnimationCounter++;
            // Change frame every few game ticks
            if (this.deathAnimationCounter >= 8) {
                this.deathAnimationCounter = 0;

                int nextFrame = super.getActualAnimationNumber() + 1;
                super.setActualAnimationNumber(nextFrame);
                super.animation(); // Update the animation frame

                // Check if we've reached the last frame
                if (nextFrame >= super.getMaxAnimationNumber() - 2) {
                    // End of animation
                    this.isDying = false;
                    // Return to idle state or implement other logic
                }
            }
        }
        if (this.isAttacking) {
            this.attackAnimationCounter++;
            // Change frame every few game ticks (adjust timing as needed)
            if (this.attackAnimationCounter >= 10) { // Change 5 to adjust animation speed
                this.attackAnimationCounter = 0;

                int nextFrame = super.getActualAnimationNumber() + 1;
                super.setActualAnimationNumber(nextFrame);
                super.animation(); // Update the animation frame

                // Check if we've reached the last frame (maxAnimationNumber frames completed)
                if (nextFrame >= super.getMaxAnimationNumber() - 2) {
                    // End of animation
                    this.isAttacking = false;
                    // Return to idle state
                    super.setMovementType(Movement.STAY);
                    super.setNumberOfAnimation("");
                    super.setActualAnimationNumber(0);
                    super.changePicture();
                }
            }
        }
    }
}