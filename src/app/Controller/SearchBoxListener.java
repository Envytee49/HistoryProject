package app.Controller;

public interface SearchBoxListener {
    void handleSearchName(String name);
    void handleSearchID(int id);
    void handleBlank();
}
