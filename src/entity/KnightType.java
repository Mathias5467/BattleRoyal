package entity;

import backend.NamedOption;

/**
 * Enum {@code KnightType} definuje rôzne typy rytierov, ktoré môže hráč v hre zvoliť.
 * Každý typ má priradenú farbu, meno, základné štatistiky (HP, útok, obrana) a cenu.
 * Implementuje rozhranie {@code NamedOption} pre jednoduché vyhľadávanie podľa mena.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public enum KnightType implements NamedOption<KnightType> {
    RED("red", "Thorne", 100, 50, 40, 0),
    GREEN("green", "Alaric", 100, 90, 70, 50),
    BLUE("blue", "Aragon", 100, 75, 85, 30);

    private String color;
    private String name;
    private int hp;
    private int attack;
    private int defend;
    private int price;

    /**
     * Konštruktor pre enum {@code KnightType}.
     * @param color Farba rytiera pre vizuálne odlíšenie.
     * @param name Meno rytiera.
     * @param hp Základné životy rytiera.
     * @param attack Sila útoku rytiera.
     * @param defend Úroveň obrany rytiera (ovplyvňuje prijaté poškodenie).
     * @param price Cena rytiera v hernej mene.
     */
    KnightType(String color, String name, int hp, int attack, int defend, int price) {
        this.color = color;
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defend = defend;
        this.price = price;
    }

    /**
     * Vráti inštanciu {@code KnightType} na základe zadaného mena.
     * @param option Meno hľadaného typu rytiera.
     * @return Inštancia {@code KnightType} s daným menom, alebo {@code null} ak sa nenájde.
     */
    @Override
    public KnightType getByName(String option) {
        return switch (option) {
            case "Thorne" -> KnightType.RED;
            case "Alaric" -> KnightType.GREEN;
            case "Aragon" -> KnightType.BLUE;
            default -> null;
        };
    }

    /**
     * Vráti cenu rytiera.
     * @return Cena rytiera.
     */
    public int getPrice() {
        return this.price;
    }

    /**
     * Vráti farbu rytiera.
     * @return Farba rytiera.
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Vráti meno rytiera.
     * @return Meno rytiera.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Vráti základné životy rytiera.
     * @return Základné životy rytiera.
     */
    public int getHp() {
        return this.hp;
    }

    /**
     * Vráti silu útoku rytiera.
     * @return Sila útoku rytiera.
     */
    public int getAttack() {
        return this.attack;
    }

    /**
     * Vráti úroveň obrany rytiera.
     * @return Úroveň obrany rytiera.
     */
    public int getDefence() {
        return this.defend;
    }
}