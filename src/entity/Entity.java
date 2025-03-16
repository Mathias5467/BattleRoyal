package entity;
import gui.Dialog;
import main.Picture;

public class Entity {
    private int x;
    private int startX;
    private int y;
    private int startY;
    private Picture picture;
    private String pictureName;
    private HPBar hpBar;
    private Direction direction;
    private EntityType entityType;
    private Movement movementType;
    private String numberOfAnimation;
    private final int maxAnimationNumber;
    private int actualAnimationNumber;
    private final int movementSpeed;
    private boolean hitRegistered;
    private int continualAnimationCounter;
    private boolean isVisible;
    public Entity(int x, int y, EntityType entityType, String name, Picture picture, String pictureName, Direction direction, HPBar hpBar, int speed) {
        this.x = x;
        this.y = y;
        this.startX = x;
        this.startY = y;
        this.picture = picture;
        this.pictureName = pictureName;
        this.direction = direction;
        this.movementType = Movement.STAY;
        this.hpBar = hpBar;
        this.numberOfAnimation = "";
        this.actualAnimationNumber = 0;
        this.movementSpeed = speed;
        this.maxAnimationNumber = 8;
        this.entityType = entityType;
        this.continualAnimationCounter = 0;
        this.hitRegistered = false;
        this.isVisible = true;
    }

    public String getPictureName() {
        return null;
    }


    public void setStartPosition() {
        if (this.entityType == EntityType.KNIGHT) {
            this.direction = Direction.RIGHT;
        } else {
            this.direction = Direction.LEFT;
            this.hpBar.resetHP();
            this.hpBar.resetWidth();
        }
        this.setX(this.getStartX());
        this.setY(this.getStartY());
        this.getPicture().changeCords(this.getX(), this.getY());
    }

    public int getStartX() {
        return this.startX;
    }

    public int getStartY() {
        return this.startY;
    }

    public boolean isAttacking() {
        return (this.movementType == Movement.ATTACK1 || this.movementType == Movement.ATTACK2 || this.movementType == Movement.ATTACK3);
    }

    public boolean isDead() {
        return this.movementType == Movement.DEATH;
    }

    public boolean isDying() {
        return this.movementType == Movement.DYING;
    }

    public void changePicture() {
        this.pictureName = this.getPictureName();
        this.picture.changeImage(this.pictureName);
    }

    public void animation() {
        int currentAnimationNum = this.numberOfAnimation.isEmpty() ? 1 : Integer.parseInt(this.numberOfAnimation);
        currentAnimationNum = (currentAnimationNum % 6) + 1;
        this.numberOfAnimation = String.valueOf(currentAnimationNum);
        this.changePicture();
    }

    public boolean mayDoAction() {
        return !this.isDead() && !this.isAttacking() && !this.isDying();
    }

    public void onlyAnimate(Direction direction) {
        this.movementType = Movement.WALK;
        this.direction = direction;
        this.actualAnimationNumber++;
        if (this.actualAnimationNumber >= this.maxAnimationNumber) {
            this.actualAnimationNumber = 0;
            this.numberOfAnimation = this.numberOfAnimation.isEmpty() ? "1" : this.numberOfAnimation;
            this.animation();
        }

    }

    public void moveHorizontaly(Direction direction, boolean animationOnly) {
        if (this.mayDoAction()) {
            this.movementType = Movement.WALK;
            this.direction = direction;
            int movementNumber;
            switch (direction) {
                case RIGHT -> {
                    movementNumber = this.movementSpeed;
                }
                case LEFT -> {
                    movementNumber = -this.movementSpeed;
                }
                default -> {
                    movementNumber = 0;
                }
            }
            if (this.x + movementNumber < 980 && this.x + movementNumber > 0) {
                if (!animationOnly) {
                    this.x += movementNumber;
                    this.picture.changeCords(this.x, this.y);
                }
                this.actualAnimationNumber++;
                if (this.actualAnimationNumber >= this.maxAnimationNumber) {
                    this.actualAnimationNumber = 0;
                    this.numberOfAnimation = this.numberOfAnimation.isEmpty() ? "1" : this.numberOfAnimation;
                    this.animation();
                }
            }


        }
    }


    public void hit(int damage) {
        if (this.hpBar.getActualHP() == 0) {
            this.death();
        }
        this.hpBar.reduceHP(damage);
    }

    // Modified attack method to start the animation sequence

    public void attack(Movement movementType) {
        if (this.mayDoAction()) {
            this.movementType = movementType;
            this.actualAnimationNumber = 0;
            this.numberOfAnimation = "0";
            this.continualAnimationCounter = 0;
        }
    }

    public void death() {
        if (!this.isDead() && !this.isDying()) {
            this.movementType = Movement.DYING;
            this.actualAnimationNumber = 0;
            this.numberOfAnimation = "0";
            this.continualAnimationCounter = 0;
        }
    }

    // New method to continue the attack animation
    public void update() {
        if ((this.isAttacking() || this.isDying()) && !this.isDead()) {
            this.continualAnimationCounter++;

            // Change frame every few game ticks (adjust timing as needed)
            if (this.continualAnimationCounter >= 8) { // Change 5 to adjust animation speed
                this.continualAnimationCounter = 0;

                this.actualAnimationNumber++;

                if (this.actualAnimationNumber >= this.maxAnimationNumber - 1) {
                    // End of animation
                    this.actualAnimationNumber = 0;
                    if (this.isDying()) {
                        this.movementType = Movement.DEATH;
                        if (this.entityType != EntityType.KNIGHT) {
                            this.setVisible(false);
                        }
                    } else {
                        this.movementType = Movement.STAY;
                        this.numberOfAnimation = "";
                        this.changePicture();
                    }
                    this.numberOfAnimation = "";
                } else {
                    this.animation();
                }
            }
        }
    }

    public Picture getPicture() {
        return this.picture;
    }

    public void stop() {
        this.movementType = Movement.STAY;
        this.numberOfAnimation = "";
        this.changePicture();
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public String getNumberOfAnimation() {
        return this.numberOfAnimation;
    }

    public void setNumberOfAnimation(String numberOfAnimation) {
        this.numberOfAnimation = numberOfAnimation;
    }

    public int getActualAnimationNumber() {
        return this.actualAnimationNumber;
    }


    public HPBar getHpBar() {
        return this.hpBar;
    }

    public void setMovementType(Movement type) {
        this.movementType = type;
    }

    public EntityType getEntity() {
        return this.entityType;
    }

    public Movement getMovementType() {
        return this.movementType;
    }

    public Direction getDirection() {
        return this.direction;
    }

    public boolean isHitRegistered() {
        return this.hitRegistered;
    }

    public void setHitRegistered(boolean hitRegistered) {
        this.hitRegistered = hitRegistered;
    }

    public void setDead(boolean dead) {
        if (dead) {
            this.movementType = Movement.DEATH;
        } else {
            this.movementType = Movement.STAY;
        }

    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        this.picture.setVisible(this.isVisible);
    }
}