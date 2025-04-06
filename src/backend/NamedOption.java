package backend;

public interface NamedOption<T> {
    T getByName(String option);
    String getName();
    int getPrice();
}
