package PersistanceLayer;

public class Customer {
    private int id;
    private String name;

    public Customer(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }
}