package entity;
import main.Picture;

import java.awt.*;

public abstract class Entity {
    private int x;
    private final int startX;
    private int y;
    private final int startY;
    private final Picture picture;
    private String pictureName;
    private final HPBar hpBar;
    private Direction direction;
    private final EntityType entityType;
    private Movement movementType;
    private String numberOfAnimation;
    private final int maxAnimationNumber;
    private int actualAnimationNumber;
    private final int movementSpeed;
    private boolean hitRegistered;
    private int continualAnimationCounter;
    public Entity(int x, int y, EntityType entityType, String name, Picture picture, String pictureName, Direction direction, HPBar hpBar, int speed) {
        this.x = x;
        this.y = y;
        this.startY = y;
        this.startX = x;
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
    }

    public abstract String getPictureName();


    public void draw(Graphics g) {
        this.picture.draw(g);
        this.hpBar.draw(g);
    }

    public void setStartPosition() {
        this.x = this.startX;
        this.y = this.startY;
        this.movementType = Movement.STAY;
        this.numberOfAnimation = "";
        this.picture.changeCords(this.x, this.y);
        if (this.entityType != EntityType.KNIGHT) {
            this.hpBar.resetHP();
            this.direction = Direction.LEFT;
        } else {
            this.direction = Direction.RIGHT;
        }
        this.changePicture();
    }


    public boolean isAttacking() {
        return (this.movementType == Movement.ATTACK1 || this.movementType == Movement.ATTACK2 || this.movementType == Movement.ATTACK3);
    }

    public boolean isDead() {
        return this.movementType == Movement.DEATH;
    }

    protected boolean isDying() {
        return this.movementType == Movement.DYING;
    }

    protected void changePicture() {
        this.pictureName = this.getPictureName();
        this.picture.changeImage(this.pictureName);
    }

    private void animation() {
        int currentAnimationNum = this.numberOfAnimation.isEmpty() ? 1 : Integer.parseInt(this.numberOfAnimation);
        currentAnimationNum = (currentAnimationNum % 6) + 1;
        this.numberOfAnimation = String.valueOf(currentAnimationNum);
        this.changePicture();
    }

    public void moveHorizontaly(Direction direction, boolean animationOnly) {
        if (!this.isDead() && !this.isDying()) {
            this.movementType = Movement.WALK;
            this.direction = direction;
            int movementNumber;
            switch (direction) {
                case RIGHT -> movementNumber = this.movementSpeed;
                case LEFT -> movementNumber = -this.movementSpeed;
                default -> movementNumber = 0;
            }
            if (this.x + movementNumber < 980 && this.x + movementNumber > 30) {
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


    public abstract void hit(int damage);

    // Modified attack method to start the animation sequence

    public void attack(Movement movementType) {
        if (!this.isAttacking() && !this.isDead() && !this.isDying()) {
            this.movementType = movementType;
            this.actualAnimationNumber = 0;
            this.numberOfAnimation = "0";
            this.continualAnimationCounter = 0;
        }
    }

    protected void death() {
        if (this.movementType != Movement.DYING && this.movementType != Movement.DEATH) {
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
            if (this.continualAnimationCounter >= 8) {
                this.continualAnimationCounter = 0;

                this.actualAnimationNumber++;

                if (this.actualAnimationNumber >= this.maxAnimationNumber - 1) {
                    this.actualAnimationNumber = 0;
                    if (this.isDying()) {
                        this.movementType = Movement.DEATH;
                    } else {
                        this.movementType = Movement.STAY;
                        this.stop();
                    }
                } else {
                    this.animation();
                }
            }
        }
    }

    protected Picture getPicture() {
        return this.picture;
    }

    public void stop() {
        this.movementType = Movement.STAY;
        this.numberOfAnimation = "";
        this.changePicture();
    }

    protected void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return this.x;
    }


    protected String getNumberOfAnimation() {
        return this.numberOfAnimation;
    }

    protected void setNumberOfAnimation(String numberOfAnimation) {
        this.numberOfAnimation = numberOfAnimation;
    }

    public int getActualAnimationNumber() {
        return this.actualAnimationNumber;
    }

    public HPBar getHpBar() {
        return this.hpBar;
    }

    protected void setMovementType(Movement type) {
        this.movementType = type;
    }

    public EntityType getEntityType() {
        return this.entityType;
    }

    public Movement getMovementType() {
        return this.movementType;
    }

    protected Direction getDirection() {
        return this.direction;
    }

    public boolean isHitRegistered() {
        return this.hitRegistered;
    }

    public void setHitRegistered(boolean hit) {
        this.hitRegistered = hit;
    }

    public void setDead(boolean dead) {
        if (dead) {
            this.movementType = Movement.DEATH;
        } else {
            this.movementType = Movement.STAY;
        }

    }

    public boolean isVisible() {
        return this.picture.isVisible();
    }

    public void setVisible(boolean visible) {
        this.picture.setVisible(visible);
    }

    protected int getY() {
        return this.y;
    }
}