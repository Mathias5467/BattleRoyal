package entity;

import main.Picture;

public class Enemy extends Entity {
    private int attackCounter;
    public Enemy(EntityType entityType) {

        super(
                800,
                465,
                entityType,
                new Picture(820, 465, 200, 220, String.format("res/%s/stayL.png", entityType)),
                String.format("res/%s/stayL.png", entityType),
                Direction.LEFT,
                new HPBar(750, 80, 100, 925, 70, entityType.toString()),
                1
        );
        this.attackCounter = 0;
    }

    @Override
    public String getPictureName() {
        return String.format("res/%s/%s%s%s.png",
                this.getEntityType().toString(),
                this.getMovementType().getSymbol(),
                this.getDirection(),
                this.getNumberOfAnimation());
    }

    @Override
    public void hit(int damage) {
        this.getHpBar().reduceHP(damage);
        if (this.getHpBar().getActualHP() == 0) {
            this.death();
        }
    }

    public void enemyAI(Player player) {
        this.attackCounter++;
        if (!player.isDead()) {
            if (this.attackCounter == 50) {
                if (player.getX() + 80 > this.getX() && player.getX() > this.getX() - 150) {
                    this.attack(Movement.ATTACK1);
                }
                this.attackCounter = 0;
            }
            if (Math.abs(player.getX() - this.getX()) > 120 && player.getX() > this.getX()) {
                this.moveHorizontaly(Direction.RIGHT, false);
            } else if (Math.abs(player.getX() - this.getX()) > 70 && player.getX() < this.getX()) {
                this.moveHorizontaly(Direction.LEFT, false);
            }
        } else {
            this.setMovementType(Movement.STAY);
            this.resetNumberOfAnimation();
            this.changePicture();
        }
    }
}