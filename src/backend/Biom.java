package backend;

public enum Biom {
    FOREST("Forest", 0),
    DUNE("Dune", 80),
    MOUNTAIN("Mountain", 40);


    private final String name;
    private final int price;
    Biom(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    public Biom getByName(String name) {
        return switch (name) {
            case "Forest" -> FOREST;
            case "Mountain" -> MOUNTAIN;
            case "Dune" -> DUNE;
            default -> null;
        };
    }
}
