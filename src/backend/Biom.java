package backend;

public enum Biom implements NamedOption<Biom> {
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

    @Override
    public Biom getByName(String option) {
        return switch (option) {
            case "Forest" -> FOREST;
            case "Mountain" -> MOUNTAIN;
            case "Dune" -> DUNE;
            default -> null;
        };
    }
}