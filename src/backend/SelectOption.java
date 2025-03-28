package backend;

public abstract class SelectOption {
    public SelectOption() {
    }


    protected int mod(int a, int b) {
        return (a % b < 0) ? (a % b) + Math.abs(b) : (a % b);
    }

    public abstract void selectOption(int direction);
}
