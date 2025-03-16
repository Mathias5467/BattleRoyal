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
        if (!player.isDead()) {
            if (player.getX() + 80 > this.getX() && player.getX()  < this.getX() + 130 && this.attackCounter % 50 == 0) {
                this.attack(Movement.ATTACK1);
            } else if (player.getX() + 70 < this.getX()) {
                this.moveHorizontaly(Direction.LEFT, false);
            } else if (player.getX() > this.getX() + 120) {
                this.moveHorizontaly(Direction.RIGHT, false);
            }
        } else {
            this.setMovementType(Movement.STAY);
            this.setNumberOfAnimation("");
            this.changePicture();
        }
        this.attackCounter++;
    }
}