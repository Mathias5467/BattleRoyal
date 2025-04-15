package gui;

import backend.NamedOption;
import backend.SelectOption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Abstraktná trieda {@code Options} poskytuje základnú implementáciu pre obrazovky s možnosťami výberu
 * v hre, ako sú výber postavy alebo mapy. Umožňuje prechádzanie, výber a nákup možností,
 * a tiež ukladanie stavu zakúpených možností do súboru.
 * @param <T> Typ enumu, ktorý implementuje rozhranie {@code NamedOption}.
 * @author Matúš Pytel
 * @version 15.4.2025
 */
public abstract class Options<T extends NamedOption<T>> extends SelectOption {
    private T option;
    private int counter;
    private int numberOfCoins;
    private final String dataPath;
    private final HashMap<T, Boolean> optionsBought;

    /**
     * Konštruktor triedy {@code Options}. Inicializuje aktuálne vybratú možnosť, počítadlo prechádzania,
     * aktuálny počet mincí hráča, cestu k dátovému súboru a mapu zakúpených možností.
     * Načíta stav zakúpených možností zo súboru.
     * @param numberOfCoins Počiatočný počet mincí hráča.
     * @param dataPath Cesta k dátovému súboru, kde sú uložené informácie o zakúpených možnostiach.
     * @param option Predvolená vybratá možnosť pri inicializácii.
     */
    public Options(int numberOfCoins, String dataPath, T option) throws FileNotFoundException {
        this.option = option;
        this.counter = 0;
        this.numberOfCoins = numberOfCoins;
        this.dataPath = dataPath;
        this.optionsBought = new HashMap<>();

        File file = new File(String.format("res/data/%s.txt", this.dataPath));
        Scanner input = new Scanner(file);
        while (input.hasNextLine()) {
            String[] data = input.nextLine().split(" ");
            if (Integer.parseInt(data[1]) == 1) {
                this.optionsBought.put(this.option.getByName(data[0]), true);
            } else {
                this.optionsBought.put(this.option.getByName(data[0]), false);
            }
        }
        input.close();
    }

    /**
     * Zapíše aktuálny stav zakúpených možností do dátového súboru.
     */
    public void writeIntoFile() throws FileNotFoundException {
        File file = new File(String.format("res/data/%s.txt", this.dataPath));
        PrintWriter input = new PrintWriter(file);
        for (T optionInStore : this.optionsBought.keySet()) {
            if (this.optionsBought.get(optionInStore)) {
                input.println(String.format("%s 1", optionInStore.getName()));
            } else {
                input.println(String.format("%s 0", optionInStore.getName()));
            }
        }
        input.close();
    }

    /**
     * Vráti nemodifikovateľnú mapu, ktorá obsahuje informácie o tom, či bola daná možnosť zakúpená.
     * @return Nemodifikovateľná mapa, kde kľúčom je možnosť typu {@code T} a hodnotou {@code Boolean} indikujúci zakúpenie.
     */
    protected Map<T, Boolean> getOptionsBought() {
        return Collections.unmodifiableMap(this.optionsBought);
    }

    /**
     * Vráti aktuálny počet mincí hráča.
     * @return Aktuálny počet mincí.
     */
    public int getNumberOfCoins() {
        return this.numberOfCoins;
    }

    /**
     * Pokúsi sa zakúpiť aktuálne vybratú možnosť. Ak má hráč dostatok mincí, možnosť sa označí ako zakúpená
     * a z počtu mincí sa odpočíta cena možnosti.
     * @return {@code true}, ak sa nákup podaril, inak {@code false}.
     */
    public boolean tryBuy() {
        if (this.option.getPrice() <= this.numberOfCoins) {
            this.optionsBought.put(this.option, true);
            this.numberOfCoins -= this.option.getPrice();
            return true;
        }
        return false;
    }

    /**
     * Kontroluje, či je aktuálne vybratá možnosť už zakúpená.
     * @return {@code true}, ak je aktuálna možnosť zakúpená, inak {@code false}.
     */
    public boolean tryChoose() {
        return this.optionsBought.get(this.option);
    }

    /**
     * Nastaví aktuálny počet mincí hráča.
     * @param numberOfCoins Nový počet mincí.
     */
    public void setNumberOfCoins(int numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    /**
     * Posunie výber možnosti v danom smere. Aktualizuje počítadlo a nastaví aktuálnu vybratú možnosť.
     * @param direction Smer posunu (záporné číslo pre posun dolava, kladné pre doprava).
     */
    @Override
    public void selectOption(int direction) {
        this.counter += direction;
        @SuppressWarnings("unchecked")
        T[] values = (T[])this.option.getClass().getEnumConstants();
        this.option = values[this.mod(this.counter, values.length)];
    }

    /**
     * Vráti aktuálne vybratú možnosť.
     * @return Aktuálne vybratá možnosť typu {@code T}.
     */
    public T getOption() {
        return this.option;
    }
}