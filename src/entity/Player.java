package entity;

import entity.components.HPBar;
import entity.utilities.Direction;
import entity.utilities.EntityType;
import entity.utilities.KnightType;
import entity.utilities.Movement;
import components.Picture;

/**
 * Trieda {@code Player} reprezentuje hrateľnú postavu rytiera v hre.
 * Dedií od triedy {@code Entity} a pridáva špecifické vlastnosti a správanie pre hráča,
 * ako je typ rytiera, pohyb bez animácie pozadia a obrana.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public class Player extends Entity {
    private KnightType knightType;

    /**
     * Konštruktor triedy {@code Player}. Inicializuje hráča so zadaným typom entity a typom rytiera.
     * Nastavuje počiatočné pozície, obrázok, smer, HP bar a rýchlosť animácie.
     * @param entityType Typ entity (vždy KNIGHT pre hráča).
     * @param knightType Typ rytiera, ktorý určuje jeho vzhľad a štatistiky.
     */
    public Player(EntityType entityType, KnightType knightType) {
        super(
                100,
                490,
                entityType,
                new Picture(100, 490, 150, 170, "res/Knight/red/stayR.png"),
                "Knight/red/stayL.png",
                Direction.RIGHT,
                new HPBar(50, 80, knightType.getHp(), 50, 70, knightType.getName()),
                4
        );
        this.knightType = knightType;
    }

    /**
     * Pohybuje hráčom doľava bez spustenia animácie pohybu, používa sa pri posúvaní pozadia.
     */
    public void moveWithoutAnimation() {
        this.setX(this.getX() - 2);
        this.getPicture().changeCords(this.getX(), this.getY());
    }

    /**
     * Nastaví typ rytiera hráča a nastaví mu meno.
     * @param knightType Nový typ rytiera pre hráča.
     */
    public void setKnight(KnightType knightType) {
        this.knightType = knightType;
        this.getHpBar().setName(this.knightType.getName());
    }

    /**
     * Vráti cestu k súboru s obrázkom hráča na základe jeho typu entity, farby rytiera,
     * typu pohybu, smeru a čísla aktuálnej animácie.
     * @return Cesta k súboru s obrázkom hráča.
     */
    @Override
    public String getPictureName() {
        return String.format("res/%s/%s/%s%s%s.png",
                this.getEntityType().toString(),
                this.knightType.getColor(),
                this.getMovementType().getSymbol(),
                this.getDirection(),
                this.getNumberOfAnimation());
    }

    /**
     * Kontroluje, či môže hráč zastaviť svoju aktuálnu akciu.
     * Hráč môže zastaviť, ak nie je mŕtvy, neútočí a neumiera.
     * @return {@code true}, ak môže hráč zastaviť, inak {@code false}.
     */
    public boolean mayStop() {
        return !this.isDead() && !this.isAttacking() && this.getMovementType() != Movement.DYING;
    }

    /**
     * Spustí animáciu obrany hráča, ak hráč práve neútočí, nie je mŕtvy a neumiera.
     * Nastaví typ pohybu na DEFEND, resetuje číslo animácie a zmení obrázok.
     */
    public void defend() {
        if (!this.isAttacking() && !this.isDead() && this.getMovementType() != Movement.DYING) {
            this.setMovementType(Movement.DEFEND);
            this.resetNumberOfAnimation();
            this.changePicture();
        }
    }

    /**
     * Aplikuje poškodenie na hráča. Ak hráč bráni, zníži HP o čiastočné poškodenie
     * zohľadňujúce obranu rytiera. Inak zníži HP o plnú hodnotu poškodenia.
     * Ak HP klesne na 0, spustí animáciu smrti.
     * @param damage Hodnota poškodenia, ktoré má hráč utrpieť.
     */
    @Override
    public void hit(int damage) {
        if (this.getMovementType() == Movement.DEFEND) {
            this.getHpBar().reduceHP((int)Math.ceil(((double)(100 - this.knightType.getDefence()) / 100) * damage));
        } else {
            this.getHpBar().reduceHP(damage);
        }
        if (this.getHpBar().getActualHP() == 0) {
            this.death();
        }
    }

    /**
     * Vráti typ rytiera hráča.
     * @return Typ rytiera hráča (inštancia enumu {@code KnightType}).
     */
    public KnightType getKnightType() {
        return this.knightType;
    }
}