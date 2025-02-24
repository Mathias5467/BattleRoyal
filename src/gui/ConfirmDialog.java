package gui;

public enum ConfirmDialog {
    YES("Yes"),
    NO("No");

    private String name;

    ConfirmDialog(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }
}
