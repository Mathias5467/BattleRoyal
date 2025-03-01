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
    private KnightColor color;
    private String name;
    public Entity(int x, int y, EntityType entityType, KnightColor color, String name, Picture picture, String pictureName, Direction direction, HPBar hpBar) {
        this.x = x;
        this.y = y;
        this.picture = picture;
        this.pictureName = pictureName;
        this.direction = direction;
        this.movementType = Movement.STAY;
        this.hpBar = hpBar;
        this.numberOfAnimation = "";
        this.actualAnimationNumber = 0;
        this.movementSpeed = 4;
        this.maxAnimationNumber = 10;
        this.entityType = entityType;
        this.color = color;
        this.name = name;
    }

    public String getPictureName() {
        if (this.color != null) {
            return String.format("res/%s/%s/%s%s%s.png",
                    this.entityType.toString(),
                    this.color,
                    this.movementType.getSymbol(),
                    this.direction,
                    this.numberOfAnimation);
        }
        return String.format("res/%s/%s%s%s.png",
                this.entityType.toString(),
                this.movementType.getSymbol(),
                this.direction,
                this.numberOfAnimation);
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
//        if (Integer.parseInt(this.numberOfAnimation) == 6) {
//            this.stop();
//        }
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

    public void defend() {
        this.movementType = Movement.DEFEND;
        this.numberOfAnimation = "";
        this.changePicture();
    }

    public void attack() {
    }

    public Picture getPicture() {
        return this.picture;
    }

    public void stop() {
        this.movementType = Movement.STAY;
        this.numberOfAnimation = "";
        this.changePicture();
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
    public Direction getDirection() {
        return this.direction;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setColor(KnightColor color, String name) {
        this.color = color;
        this.name = name;
        this.hpBar.setName(this.name);
        this.changePicture();
    }

}
