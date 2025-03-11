package entity;

import main.Picture;

public class Enemy extends Entity {
    public Enemy(EntityType entityType) {

        super(
                800,
                460,
                entityType,
                entityType.toString(),
                new Picture(820, 460, 200, 220, String.format("res/%s/stayL.png", entityType.toString())),
                String.format("res/%s/stayL.png", entityType.toString()),
                Direction.LEFT,
                new HPBar(750, 80, 100, 925, 70, entityType.toString()),
                1
        );
    }

    @Override
    public String getPictureName() {
        return String.format("res/%s/%s%s%s.png",
                this.getEntity().toString(),
                this.getMovementType().getSymbol(),
                this.getDirection(),
                this.getNumberOfAnimation());
    }

    public void setStartPosition() {
        this.setX(800);
        this.setY(460);
        this.getHpBar().resetHP();
        this.getHpBar().resetWidth();
        this.setDirection(Direction.RIGHT);
        this.getPicture().changeCords(this.getX(), this.getY());
    }

    public void enemyAI(Player player) {
        if (!player.isDead()) {
            if (player.getX() + 80 > this.getX() && player.getX() < this.getX() + 80) {
                this.attack(Movement.ATTACK1);
            } else if (Math.abs(player.getX() - this.getX()) > 0 && player.getX() > this.getX()) {
                this.moveRight();
            } else if (Math.abs(player.getX() - this.getX()) > 0 && player.getX() < this.getX()) {
                this.moveLeft();
            }
        } else {
            this.setMovementType(Movement.STAY);
            this.setNumberOfAnimation("");
            this.changePicture();
        }
    }
}