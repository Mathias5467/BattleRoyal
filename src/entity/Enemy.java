package entity;

import entity.components.HPBar;
import entity.utilities.Direction;
import entity.utilities.EntityType;
import entity.utilities.Movement;
import components.Picture;

/**
 * Trieda {@code Enemy} reprezentuje nepriateľské entity v hre.
 * Dedií od triedy {@code Entity} a pridáva špecifické správanie pre nepriateľov,
 * ako je jednoduchá umelá inteligencia (AI) pre pohyb a útok na hráča.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public class Enemy extends Entity {
    private int attackCounter;

    /**
     * Konštruktor triedy {@code Enemy}. Inicializuje nepriateľa so zadaným typom entity.
     * Nastavuje počiatočné pozície, obrázok, smer, HP bar a rýchlosť pohybu.
     * @param entityType Typ entity nepriateľa (napr. SKELETON, VIKING).
     */
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

    /**
     * Vráti cestu k súboru s obrázkom nepriateľa na základe jeho typu entity,
     * typu pohybu, smeru a čísla aktuálnej animácie.
     * @return Cesta k súboru s obrázkom nepriateľa.
     */
    @Override
    public String getPictureName() {
        return String.format("res/%s/%s%s%s.png",
                this.getEntityType().toString(),
                this.getMovementType().getSymbol(),
                this.getDirection(),
                this.getNumberOfAnimation());
    }

    /**
     * Aplikuje poškodenie na nepriateľa a znižuje jeho životy.
     * Ak životy klesnú na 0, spustí animáciu smrti.
     * @param damage Hodnota poškodenia, ktoré nepriateľ utrpel.
     */
    @Override
    public void hit(int damage) {
        this.getHpBar().reduceHP(damage);
        if (this.getHpBar().getActualHP() == 0) {
            this.death();
        }
    }

    /**
     * Implementuje jednoduchú umelú inteligenciu pre pohyb a útok nepriateľa.
     * Nepriateľ sa pokúša priblížiť k hráčovi a zaútočiť, ak je v dosahu.
     * Ak je hráč mŕtvy, nepriateľ zostane stáť.
     * @param player Inštancia triedy {@code Player} reprezentujúca hráča.
     */
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