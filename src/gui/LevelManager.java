package gui;

import main.Picture;

/**
 * Trieda {@code LevelManager} spravuje vizuálne prvky hernej úrovne,
 * ako sú posúvajúce sa pozadia, zem a prechodová šípka.
 */
public class LevelManager {
    private Picture background1;
    private Picture background2;
    private Picture background3;
    private Picture ground;
    private Picture arrow;
    private int background1ScrollSpeed = 1;
    private int background2ScrollSpeed = 2;
    private int background3ScrollSpeed = 3;
    private int groundScrollSpeed = 4;
    private int arrowScrollSpeed = 4;
    private static final int BACKGROUND_WIDTH = 2200;
    private static final int BACKGROUND_HEIGHT = 700;
    private final int groundYCoordinate = 650;
    private final int groundHeight = 58;
    private final int initialArrowXCoordinate = 800;
    private final int initialArrowYCoordinate = 535;
    private final int arrowWidth = 100;
    private final int arrowHeight = 155;
    private static final String GROUND_IMAGE_PATH = "res/back/ground.png";
    private static final String ARROW_IMAGE_PATH = "res/arrow.png";
    private static final String BACKGROUND1_IMAGE_FORMAT = "res/back/%s/b1.png";
    private static final String BACKGROUND2_IMAGE_FORMAT = "res/back/%s/b2.png";
    private static final String BACKGROUND3_IMAGE_FORMAT = "res/back/%s/b3.png";
    private static final int BACKGROUND_SCROLL_THRESHOLD = -LevelManager.BACKGROUND_WIDTH / 2;

    /**
     * Vytvorí inštanciu {@code LevelManager} pre daný biom hry, načíta
     * príslušné obrázky pozadia a inicializuje ostatné prvky úrovne.
     * @param biomName Názov aktuálneho biomu hry.
     */
    public LevelManager(String biomName) {
        this.background1 = new Picture(0, 0, LevelManager.BACKGROUND_WIDTH, LevelManager.BACKGROUND_HEIGHT, String.format(BACKGROUND1_IMAGE_FORMAT, biomName));
        this.background2 = new Picture(0, 0, LevelManager.BACKGROUND_WIDTH, LevelManager.BACKGROUND_HEIGHT, String.format(BACKGROUND2_IMAGE_FORMAT, biomName));
        this.background3 = new Picture(0, 0, LevelManager.BACKGROUND_WIDTH, LevelManager.BACKGROUND_HEIGHT, String.format(BACKGROUND3_IMAGE_FORMAT, biomName));
        this.ground = new Picture(0, this.groundYCoordinate, LevelManager.BACKGROUND_WIDTH, this.groundHeight, GROUND_IMAGE_PATH);
        this.arrow = new Picture(this.initialArrowXCoordinate, this.initialArrowYCoordinate, this.arrowWidth, this.arrowHeight, ARROW_IMAGE_PATH);
        this.arrow.setVisible(false);
    }

    /**
     * Resetuje viditeľnosť a pozíciu prechodovej šípky úrovne.
     */
    public void resetArrow() {
        this.arrow.setVisible(false);
        this.arrow.changeCords(this.initialArrowXCoordinate, this.initialArrowYCoordinate);
    }

    /**
     * Zobrazí prechodovú šípku úrovne.
     */
    public void showArrow() {
        this.arrow.setVisible(true);
    }

    /**
     * Vráti prvý obrázok pozadia.
     * @return Prvý obrázok pozadia typu {@code Picture}.
     */
    public Picture getBackground1() {
        return this.background1;
    }

    /**
     * Vráti druhý obrázok pozadia.
     * @return Druhý obrázok pozadia typu {@code Picture}.
     */
    public Picture getBackground2() {
        return this.background2;
    }

    /**
     * Vráti tretí obrázok pozadia.
     * @return Tretí obrázok pozadia typu {@code Picture}.
     */
    public Picture getBackground3() {
        return this.background3;
    }

    /**
     * Vráti obrázok zeme.
     * @return Obrázok zeme typu {@code Picture}.
     */
    public Picture getGround() {
        return this.ground;
    }

    /**
     * Vráti obrázok prechodovej šípky.
     * @return Obrázok šípky typu {@code Picture}.
     */
    public Picture getArrow() {
        return this.arrow;
    }

    /**
     * Posúva prvky pozadia a zeme doprava, čím vytvára efekt skrolovania.
     * Skrolovanie nastane iba vtedy, ak je príznak {@code canScroll} pravdivý
     * (napr. keď hráč porazí aktuálneho nepriateľa).
     * @param canScroll Indikátor, či sa má úroveň posúvať.
     */
    public void scrollRight(boolean canScroll) {
        if (canScroll) {
            this.scrollElement(this.background2, LevelManager.BACKGROUND_WIDTH, this.background2.getY(), this.background2ScrollSpeed);
            this.scrollElement(this.background1, LevelManager.BACKGROUND_WIDTH, this.background1.getY(), this.background1ScrollSpeed);
            this.scrollElement(this.ground, LevelManager.BACKGROUND_WIDTH, this.ground.getY(), this.groundScrollSpeed);
            this.scrollElement(this.background3, LevelManager.BACKGROUND_WIDTH, this.background2.getY(), this.background3ScrollSpeed);
            this.arrow.changeCords(this.arrow.getX() - this.arrowScrollSpeed, this.arrow.getY());
        }
    }

    /**
     * Pomocná metóda na horizontálne posunutie prvku typu {@code Picture}.
     * Ak sa prvok posunie za určitú hranicu, resetuje sa na začiatok,
     * čím sa vytvorí efekt plynulého skrolovania.
     * @param picture Prvok typu {@code Picture}, ktorý sa má posunúť.
     * @param width Šírka posúvanej oblasti.
     * @param y Y-ová súradnica obrázka.
     * @param speed Rýchlosť posunu.
     */
    private void scrollElement(Picture picture, int width, int y, int speed) {
        if (picture.getX() < BACKGROUND_SCROLL_THRESHOLD) {
            picture.changeCords(0, y);
        }
        picture.changeCords(picture.getX() - speed, y);
    }

    /**
     * Nastaví obrázky pozadia pre nový biom hry.
     * @param biomName Názov nového biomu hry.
     */
    public void setBiom(String biomName) {
        this.background1.changeImage(String.format(BACKGROUND1_IMAGE_FORMAT, biomName));
        this.background2.changeImage(String.format(BACKGROUND2_IMAGE_FORMAT, biomName));
        this.background3.changeImage(String.format(BACKGROUND3_IMAGE_FORMAT, biomName));
    }
}