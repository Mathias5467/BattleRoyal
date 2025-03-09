package entity;

import main.Picture;

public class Enemy extends Entity {
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
                1
        );
    }

    @Override
    public String getPictureName() {
        return String.format("res/%s/%s%s%s.png",
                super.getEntity().toString(),
                super.getMovementType().getSymbol(),
                super.getDirection(),
                super.getNumberOfAnimation());
    }


    public void enemyAI(Player player) {
        if (!player.isDead()) {
            if (player.getX() + 80 > super.getX() && player.getX() < super.getX() + 80) {
                super.attack(Movement.ATTACK1);
            } else if (Math.abs(player.getX() - super.getX()) > 0 && player.getX() > super.getX()) {
                super.moveRight();
            } else if (Math.abs(player.getX() - super.getX()) > 0 && player.getX() < super.getX()) {
                super.moveLeft();
            }
        } else {
            super.setMovementType(Movement.STAY);
            super.setNumberOfAnimation("");
            super.changePicture();
        }
    }
}