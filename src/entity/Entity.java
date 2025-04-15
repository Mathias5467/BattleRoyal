package entity;
import main.Picture;

import java.awt.*;

/**
 * Abstraktná trieda {@code Entity} je základom pre všetky hrateľné a nehrateľné postavy v hre.
 * Poskytuje základné atribúty ako pozícia, obrázok, životy, smer, typ pohybu a animácie.
 * Definuje abstraktné metódy pre získanie názvu obrázka a pre zásah entitou.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
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
    private int actAnimNumber;
    private final int movementSpeed;
    private boolean hitRegistered;
    private int continualAnimationCounter;

    /**
     * Konštruktor triedy {@code Entity}. Inicializuje základné atribúty entity.
     * @param x Počiatočná x-ová súradnica entity.
     * @param y Počiatočná y-ová súradnica entity.
     * @param entityType Typ entity (napr. rytier, monštrum).
     * @param picture Objekt {@code Picture} reprezentujúci aktuálny obrázok entity.
     * @param pictureName Základný názov obrázka entity (bez čísla animácie).
     * @param direction Počiatočný smer entity.
     * @param hpBar Objekt {@code HPBar} reprezentujúci ukazovateľ životov entity.
     * @param speed Rýchlosť pohybu entity.
     */
    public Entity(int x, int y, EntityType entityType, Picture picture, String pictureName, Direction direction, HPBar hpBar, int speed) {
        this.x = x;
        this.y = y;
        this.startY = y;
        this.startX = x;
        this.picture = picture;
        this.pictureName = pictureName;
        this.direction = direction;
        this.movementType = Movement.STAY;
        this.hpBar = hpBar;
        this.resetNumberOfAnimation();
        this.actAnimNumber = 0;
        this.movementSpeed = speed;
        this.maxAnimationNumber = 8;
        this.entityType = entityType;
        this.continualAnimationCounter = 0;
        this.hitRegistered = false;
    }

    /**
     * Abstraktná metóda pre získanie názvu súboru s obrázkom entity.
     * Implementácia sa líši pre rôzne typy entít a ich stavy.
     * @return Názov súboru s obrázkom entity.
     */
    public abstract String getPictureName();

    /**
     * Vykresľuje entitu a jej HP bar na zadaný grafický kontext.
     * @param g Grafický kontext, na ktorý sa má entita vykresliť.
     */
    public void draw(Graphics g) {
        this.picture.draw(g);
        this.hpBar.draw(g);
    }

    /**
     * Nastaví entitu na jej počiatočnú pozíciu, resetuje typ pohybu, animáciu, smer (pre nepriateľov doľava, pre hráča doprava),
     * obnoví HP (pre nepriateľov) a zabezpečí, že entita je viditeľná. Následne zmení obrázok entity.
     */
    public void setStartPosition() {
        this.x = this.startX;
        this.y = this.startY;
        this.movementType = Movement.STAY;
        this.resetNumberOfAnimation();
        this.picture.changeCords(this.x, this.y);
        if (this.entityType != EntityType.KNIGHT) {
            this.hpBar.resetHP();
            this.direction = Direction.LEFT;
        } else {
            this.direction = Direction.RIGHT;
        }
        if (!this.isVisible()) {
            this.setVisible(true);
        }
        this.changePicture();
    }

    /**
     * Kontroluje, či entita práve útočí.
     * @return {@code true}, ak je typ pohybu entity jeden z útočných typov, inak {@code false}.
     */
    public boolean isAttacking() {
        return (this.movementType == Movement.ATTACK1 || this.movementType == Movement.ATTACK2 || this.movementType == Movement.ATTACK3);
    }

    /**
     * Kontroluje, či je entita mŕtva.
     * @return {@code true}, ak je typ pohybu entity DEATH, inak {@code false}.
     */
    public boolean isDead() {
        return this.movementType == Movement.DEATH;
    }

    /**
     * Kontroluje, či entita práve umiera.
     * @return {@code true}, ak je typ pohybu entity DYING, inak {@code false}.
     */
    protected boolean isDying() {
        return this.movementType == Movement.DYING;
    }

    /**
     * Zmení aktuálny obrázok entity na základe aktuálneho typu pohybu, smeru a čísla animácie.
     */
    protected void changePicture() {
        this.pictureName = this.getPictureName();
        this.picture.changeImage(this.pictureName);
    }

    /**
     * Aktualizuje číslo animácie pre pohyb. Cyklicky prechádza číslami od 1 do 6.
     * Následne zmení obrázok entity.
     */
    private void animation() {
        int currentAnimationNum = this.numberOfAnimation.isEmpty() ? 1 : Integer.parseInt(this.numberOfAnimation);
        currentAnimationNum = (currentAnimationNum % 6) + 1;
        this.numberOfAnimation = String.valueOf(currentAnimationNum);
        this.changePicture();
    }

    /**
     * Pohybuje entitou horizontálne daným smerom. Ak {@code animationOnly} je {@code false},
     * mení aj x-ovú súradnicu entity (pokiaľ sa nachádza v rámci definovaných hraníc).
     * Spúšťa animáciu pohybu po dosiahnutí určitého počtu snímkov.
     * @param direction Smer pohybu (LEFT alebo RIGHT).
     * @param animationOnly Ak {@code true}, vykoná sa len animácia pohybu bez zmeny pozície.
     */
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
            if (this.x + movementNumber < 970 && this.x + movementNumber > -20) {
                if (!animationOnly) {
                    this.x += movementNumber;
                    this.picture.changeCords(this.x, this.y);
                }
                this.actAnimNumber++;
                if (this.actAnimNumber >= this.maxAnimationNumber) {
                    this.actAnimNumber = 0;
                    this.numberOfAnimation = this.numberOfAnimation.isEmpty() ? "1" : this.numberOfAnimation;
                    this.animation();
                }
            }
        }
    }

    /**
     * Abstraktná metóda pre spracovanie zásahu entitou a zníženie jej životov.
     * Implementácia sa líši pre rôzne typy entít (napr. hráč brániaci sa).
     * @param damage Hodnota poškodenia, ktoré entita utrpela.
     */
    public abstract void hit(int damage);

    /**
     * Spustí animáciu útoku entity daným typom útoku, ak entita práve neútočí, nie je mŕtva a neumiera.
     * Resetuje číslo animácie a počítadlo kontinuálnej animácie.
     * @param movementType Typ útoku (ATTACK1, ATTACK2 alebo ATTACK3).
     */
    public void attack(Movement movementType) {
        if (!this.isAttacking() && !this.isDead() && !this.isDying()) {
            this.movementType = movementType;
            this.actAnimNumber = 0;
            this.numberOfAnimation = "0";
            this.continualAnimationCounter = 0;
        }
    }

    /**
     * Spustí animáciu smrti entity, ak entita práve neumiera a nie je už mŕtva.
     * Nastaví typ pohybu na DYING, resetuje číslo animácie a počítadlo kontinuálnej animácie.
     */
    protected void death() {
        if (this.movementType != Movement.DYING && this.movementType != Movement.DEATH) {
            this.movementType = Movement.DYING;
            this.actAnimNumber = 0;
            this.numberOfAnimation = "0";
            this.continualAnimationCounter = 0;
        }
    }

    /**
     * Aktualizuje animáciu útoku alebo umierania entity. Po určitom počte snímkov
     * posunie číslo animácie. Ak sa dosiahne koniec animácie, prejde do stavu DEATH (pri umieraní)
     * alebo STAY (po útoku).
     */
    public void update() {
        if ((this.isAttacking() || this.isDying()) && !this.isDead()) {
            this.continualAnimationCounter++;

            if (this.continualAnimationCounter >= 7) {
                this.continualAnimationCounter = 0;
                this.actAnimNumber++;

                if (this.actAnimNumber >= this.maxAnimationNumber - 1) {
                    this.actAnimNumber = 0;
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

    /**
     * Vráti objekt {@code Picture} reprezentujúci aktuálny obrázok entity.
     * @return Objekt {@code Picture} entity.
     */
    protected Picture getPicture() {
        return this.picture;
    }

    /**
     * Nastaví typ pohybu entity na STAY a resetuje animáciu.
     */
    public void stop() {
        this.movementType = Movement.STAY;
        this.resetNumberOfAnimation();
        this.changePicture();
    }

    /**
     * Nastaví x-ovú súradnicu entity.
     * @param x Nová x-ová súradnica.
     */
    protected void setX(int x) {
        this.x = x;
    }

    /**
     * Vráti aktuálnu x-ovú súradnicu entity.
     * @return Aktuálna x-ová súradnica.
     */
    public int getX() {
        return this.x;
    }

    /**
     * Vráti aktuálnu y-ovú súradnicu entity.
     * @return Aktuálna y-ová súradnica.
     */
    protected int getY() {
        return this.y;
    }

    /**
     * Vráti reťazec reprezentujúci aktuálne číslo animácie.
     * @return Reťazec s číslom animácie.
     */
    protected String getNumberOfAnimation() {
        return this.numberOfAnimation;
    }

    /**
     * Resetuje reťazec s číslom animácie na prázdny.
     */
    protected void resetNumberOfAnimation() {
        this.numberOfAnimation = "";
    }

    /**
     * Vráti aktuálne číslo snímky animácie.
     * @return Aktuálne číslo snímky animácie.
     */
    public int getActAnimNumber() {
        return this.actAnimNumber;
    }

    /**
     * Vráti objekt {@code HPBar} entity.
     * @return Objekt {@code HPBar} entity.
     */
    public HPBar getHpBar() {
        return this.hpBar;
    }

    /**
     * Vráti typ entity.
     * @return Typ entity (inštancia enumu {@code EntityType}).
     */
    public EntityType getEntityType() {
        return this.entityType;
    }

    /**
     * Vráti aktuálny typ pohybu entity.
     * @return Aktuálny typ pohybu entity (inštancia enumu {@code Movement}).
     */
    protected Movement getMovementType() {
        return this.movementType;
    }

    /**
     * Nastaví typ pohybu entity.
     * @param type Nový typ pohybu (inštancia enumu {@code Movement}).
     */
    public void setMovementType(Movement type) {
        this.movementType = type;
    }

    /**
     * Vráti aktuálny smer entity.
     * @return Aktuálny smer entity (inštancia enumu {@code Direction}).
     */
    protected Direction getDirection() {
        return this.direction;
    }

    /**
     * Kontroluje, či bol už zaregistrovaný zásah počas aktuálnej animácie útoku.
     * @return {@code true}, ak bol zásah zaregistrovaný, inak {@code false}.
     */
    public boolean isHitRegistered() {
        return this.hitRegistered;
    }

    /**
     * Nastaví príznak, či bol už zaregistrovaný zásah počas aktuálnej animácie útoku.
     * @param hit Nová hodnota príznaku.
     */
    public void setHitRegistered(boolean hit) {
        this.hitRegistered = hit;
    }

    /**
     * Kontroluje, či je entita viditeľná.
     * @return {@code true}, ak je entita viditeľná, inak {@code false}.
     */
    public boolean isVisible() {
        return this.picture.isVisible();
    }

    /**
     * Nastaví viditeľnosť entity.
     * @param visible Nová hodnota viditeľnosti.
     */
    public void setVisible(boolean visible) {
        this.picture.setVisible(visible);
    }
}