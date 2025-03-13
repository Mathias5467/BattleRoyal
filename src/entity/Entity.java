package entity;
import gui.Dialog;
import main.Picture;

public class Entity {
    private int x;
    private int y;
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
    private boolean isAttacking;
    private boolean isDying;
    private boolean isDead;
    private boolean hitRegistered;
    private int continualAnimationCounter;
    private boolean isVisible;
    public Entity(int x, int y, EntityType entityType, String name, Picture picture, String pictureName, Direction direction, HPBar hpBar, int speed) {
        this.x = x;
        this.y = y;
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
        this.isAttacking = false;
        this.continualAnimationCounter = 0;
        this.isDying = false;
        this.isDead = false;
        this.hitRegistered = false;
        this.isVisible = true;
    }

    public String getPictureName() {
        return null;
    }

    public void moveWithoutAnimation() {
        this.x -= 2;
        this.picture.changeCords(this.x, this.y);
    }

    /**
     * Maybe make this inside walkRight with boolean parameter
     * @param direction
     */
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

    public boolean isAttacking() {
        return this.isAttacking;
    }

    public boolean isDead() {
        return this.isDead;
    }

    public boolean isDying() {
        return this.isDying;
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


    public void moveRight() {
        if (!this.isDead && !this.isDying) {
            this.movementType = Movement.WALK;
            this.direction = Direction.RIGHT;
            if (this.x + this.movementSpeed < 980) {
                this.x += this.movementSpeed;
                this.picture.changeCords(this.x, this.y);
                this.actualAnimationNumber++;
                if (this.actualAnimationNumber >= this.maxAnimationNumber) {
                    this.actualAnimationNumber = 0;
                    this.numberOfAnimation = this.numberOfAnimation.isEmpty() ? "1" : this.numberOfAnimation;
                    this.animation();
                }
            }
        }
    }

    public void moveLeft() {
        if (!this.isDead && !this.isDying) {
            this.movementType = Movement.WALK;
            this.direction = Direction.LEFT;
            if (this.x > -30) {
                this.x -= this.movementSpeed;
                this.picture.changeCords(this.x, this.y);
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
        if (!this.isAttacking && !this.isDead && !this.isDying) {
            this.movementType = movementType;
            this.actualAnimationNumber = 0;
            this.numberOfAnimation = "0";
            this.isAttacking = true;
            this.continualAnimationCounter = 0;
        }
    }

    public void death() {
        if (!this.isDying) {
            this.movementType = Movement.DEATH;
            this.isAttacking = false;
            this.actualAnimationNumber = 0;
            this.numberOfAnimation = "0";
            this.isDying = true;
            this.continualAnimationCounter = 0;
        }
    }

    // New method to continue the attack animation
    public void update() {
        if ((this.isAttacking || this.isDying) && !this.isDead) {
            this.continualAnimationCounter++;

            // Change frame every few game ticks (adjust timing as needed)
            if (this.continualAnimationCounter >= 8) { // Change 5 to adjust animation speed
                this.continualAnimationCounter = 0;

                this.actualAnimationNumber++;

                if (this.actualAnimationNumber >= this.maxAnimationNumber - 1) {
                    // End of animation
                    this.actualAnimationNumber = 0;
                    if (this.isDying) {
                        this.isDying = false;
                        this.isDead = true;
                        this.picture.setVisible(false);
                    } else {
                        this.isAttacking = false;
                        this.movementType = Movement.STAY;
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

    public int getMaxAnimationNumber() {
        return this.maxAnimationNumber;
    }

    public int getActualAnimationNumber() {
        return this.actualAnimationNumber;
    }

    public void setActualAnimationNumber(int actualAnimationNumber) {
        this.actualAnimationNumber = actualAnimationNumber;
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

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public boolean isHitRegistered() {
        return this.hitRegistered;
    }

    public void setHitRegistered(boolean hitRegistered) {
        this.hitRegistered = hitRegistered;
    }

    public void setDead(boolean b) {
        this.isDead = b;
    }

    public boolean isVisible() {
        return this.isVisible;
    }

    public void setVisible(boolean visible) {
        this.isVisible = visible;
        this.picture.setVisible(this.isVisible);
    }
}