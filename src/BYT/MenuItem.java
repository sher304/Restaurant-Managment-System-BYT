package BYT;

public class MenuItem {
    private String name;
    private String description;
    private long price;

    // private NormalOrVegan normalOrVegan; (abstract class?)
    private Normal normal;
    private Vegan vegan;

    private Food food;
    private Drink drink;

    public MenuItem(String name, String description, long price) {
        this.name = name;
        this.description = Validator.validateAttributes(description);
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = Validator.validateAttributes(description);
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}
