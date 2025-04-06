package gui;

import backend.NamedOption;
import backend.SelectOption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;

public abstract class Options<T extends NamedOption<T>> extends SelectOption {
    private T option;
    private int counter;
    private int numberOfCoins;
    private final String dataPath;
    private final HashMap<T, Boolean> optionsBought;

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

    public HashMap<T, Boolean> getOptionsBought() {
        return this.optionsBought;
    }

    public int getNumberOfCoins() {
        return this.numberOfCoins;
    }

    public boolean tryBuy() {
        if (this.option.getPrice() <= this.numberOfCoins) {
            this.optionsBought.put(this.option, true);
            this.numberOfCoins -= this.option.getPrice();
            return true;
        }
        return false;
    }

    public boolean tryChoose() {
        return this.optionsBought.get(this.option);
    }

    public void setNumberOfCoins(int numberOfCoins) {
        this.numberOfCoins = numberOfCoins;
    }

    @Override
    public void selectOption(int direction) {
        this.counter += direction;
        this.counter = this.mod(this.counter, 3);

        @SuppressWarnings("unchecked")
        T[] values = (T[])this.option.getClass().getEnumConstants();
        this.option = values[this.counter % values.length];
    }

    public T getOption() {
        return this.option;
    }
}