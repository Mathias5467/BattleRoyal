package entity;

import main.Picture;

public class Enemy extends Entity {
    private int attackCounter;
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
        this.attackCounter = 0;
    }

    @Override
    public String getPictureName() {
        return String.format("res/%s/%s%s%s.png",
                this.getEntity().toString(),
                this.getMovementType().getSymbol(),
                this.getDirection(),
                this.getNumberOfAnimation());
    }


    public void enemyAI(Player player) {
        this.attackCounter++;
        if (!player.isDead()) {
            if (this.attackCounter == 60) {
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
            this.setNumberOfAnimation("");
            this.changePicture();
        }
    }
}