package backend;

public enum Biom {
    FOREST("Forest"),
    MOUNTAIN("Mountain"),
    DUNE("Dune");

    private final String name;
    Biom(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
}
