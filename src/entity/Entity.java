package entity;
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
    }

    public String getPictureName() {
        return null;
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

    public void moveLeft() {
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

    public void death() {

    }

    public void hit(int damage) {
        if (this.hpBar.getActualHP() == 0) {
            this.death();
        }
        this.hpBar.reduceHP(damage);
    }

    public void attack(Movement movementType) {
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
}
