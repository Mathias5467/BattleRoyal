package gui;

import main.Picture;

/**
 * Trieda {@code LevelManager} spravuje vizuálne prvky hernej úrovne,
 * ako sú posúvajúce sa pozadia, zem a prechodová šípka.
 */
public class LevelManager {
    private Picture pozadie1;
    private Picture pozadie2;
    private Picture pozadie3;
    private Picture zem;
    private Picture sipka;
    private int rychlostPosunuPozadia1 = 1;
    private int rychlostPosunuPozadia2 = 2;
    private int rychlostPosunuPozadia3 = 3;
    private int rychlostPosunuZeme = 4;
    private int rychlostPosunuSipky = 4;
    private static final int SIRKA_POZADIA = 2200;
    private static final int VYSKA_POZADIA = 700;
    private final int ySuradnicaZeme = 650;
    private final int vyskaZeme = 58;
    private final int pociatocnaXSuradnicaSipky = 800;
    private final int pociatocnaYSuradnicaSipky = 535;
    private final int sirkaSipky = 100;
    private final int vyskaSipky = 155;
    private static final String OBR_ZEME_CESTA = "res/back/ground.png";
    private static final String OBR_SIPKY_CESTA = "res/arrow.png";
    private static final String FORMAT_OBR_POZADIA1 = "res/back/%s/b1.png";
    private static final String FORMAT_OBR_POZADIA2 = "res/back/%s/b2.png";
    private static final String FORMAT_OBR_POZADIA3 = "res/back/%s/b3.png";
    private static final int PRAH_POSUNU_POZADIA = -LevelManager.SIRKA_POZADIA / 2;

    /**
     * Vytvorí inštanciu {@code LevelManager} pre daný biom hry, načíta
     * príslušné obrázky pozadia a inicializuje ostatné prvky úrovne.
     * @param nazovBiomu Názov aktuálneho biomu hry.
     */
    public LevelManager(String nazovBiomu) {
        this.pozadie1 = new Picture(0, 0, LevelManager.SIRKA_POZADIA, LevelManager.VYSKA_POZADIA, String.format(FORMAT_OBR_POZADIA1, nazovBiomu));
        this.pozadie2 = new Picture(0, 0, LevelManager.SIRKA_POZADIA, LevelManager.VYSKA_POZADIA, String.format(FORMAT_OBR_POZADIA2, nazovBiomu));
        this.pozadie3 = new Picture(0, 0, LevelManager.SIRKA_POZADIA, LevelManager.VYSKA_POZADIA, String.format(FORMAT_OBR_POZADIA3, nazovBiomu));
        this.zem = new Picture(0, this.ySuradnicaZeme, LevelManager.SIRKA_POZADIA, this.vyskaZeme, OBR_ZEME_CESTA);
        this.sipka = new Picture(this.pociatocnaXSuradnicaSipky, this.pociatocnaYSuradnicaSipky, this.sirkaSipky, this.vyskaSipky, OBR_SIPKY_CESTA);
        this.sipka.setVisible(false);
    }

    /**
     * Resetuje viditeľnosť a pozíciu prechodovej šípky úrovne.
     */
    public void resetSipku() {
        this.sipka.setVisible(false);
        this.sipka.changeCords(this.pociatocnaXSuradnicaSipky, this.pociatocnaYSuradnicaSipky);
    }

    /**
     * Zobrazí prechodovú šípku úrovne.
     */
    public void zobrazSipku() {
        this.sipka.setVisible(true);
    }

    /**
     * Vráti prvý obrázok pozadia.
     * @return Prvý obrázok pozadia typu {@code Picture}.
     */
    public Picture getPozadie1() {
        return this.pozadie1;
    }

    /**
     * Vráti druhý obrázok pozadia.
     * @return Druhý obrázok pozadia typu {@code Picture}.
     */
    public Picture getPozadie2() {
        return this.pozadie2;
    }

    /**
     * Vráti tretí obrázok pozadia.
     * @return Tretí obrázok pozadia typu {@code Picture}.
     */
    public Picture getPozadie3() {
        return this.pozadie3;
    }

    /**
     * Vráti obrázok zeme.
     * @return Obrázok zeme typu {@code Picture}.
     */
    public Picture getZem() {
        return this.zem;
    }

    /**
     * Vráti obrázok prechodovej šípky.
     * @return Obrázok šípky typu {@code Picture}.
     */
    public Picture getSipku() {
        return this.sipka;
    }

    /**
     * Posúva prvky pozadia a zeme doprava, čím vytvára efekt skrolovania.
     * Skrolovanie nastane iba vtedy, ak je príznak {@code mozeSkrolovat} pravdivý
     * (napr. keď hráč porazí aktuálneho nepriateľa).
     * @param mozeSkrolovat Indikátor, či sa má úroveň posúvať.
     */
    public void posunDoprava(boolean mozeSkrolovat) {
        if (mozeSkrolovat) {
            this.posunPrvok(this.pozadie2, LevelManager.SIRKA_POZADIA, this.pozadie2.getY(), this.rychlostPosunuPozadia2);
            this.posunPrvok(this.pozadie1, LevelManager.SIRKA_POZADIA, this.pozadie1.getY(), this.rychlostPosunuPozadia1);
            this.posunPrvok(this.zem, LevelManager.SIRKA_POZADIA, this.zem.getY(), this.rychlostPosunuZeme);
            this.posunPrvok(this.pozadie3, LevelManager.SIRKA_POZADIA, this.pozadie2.getY(), this.rychlostPosunuPozadia3);
            this.sipka.changeCords(this.sipka.getX() - this.rychlostPosunuSipky, this.sipka.getY());
        }
    }

    /**
     * Pomocná metóda na horizontálne posunutie prvku typu {@code Picture}.
     * Ak sa prvok posunie za určitú hranicu, resetuje sa na začiatok,
     * čím sa vytvorí efekt plynulého skrolovania.
     * @param obrazok Prvok typu {@code Picture}, ktorý sa má posunúť.
     * @param sirka Šírka posúvanej oblasti.
     * @param y Y-ová súradnica obrázka.
     * @param rychlost Rýchlosť posunu.
     */
    private void posunPrvok(Picture obrazok, int sirka, int y, int rychlost) {
        if (obrazok.getX() < PRAH_POSUNU_POZADIA) {
            obrazok.changeCords(0, y);
        }
        obrazok.changeCords(obrazok.getX() - rychlost, y);
    }

    /**
     * Nastaví obrázky pozadia pre nový biom hry.
     * @param nazovBiomu Názov nového biomu hry.
     */
    public void nastavBiom(String nazovBiomu) {
        this.pozadie1.changeImage(String.format(FORMAT_OBR_POZADIA1, nazovBiomu));
        this.pozadie2.changeImage(String.format(FORMAT_OBR_POZADIA2, nazovBiomu));
        this.pozadie3.changeImage(String.format(FORMAT_OBR_POZADIA3, nazovBiomu));
    }
}