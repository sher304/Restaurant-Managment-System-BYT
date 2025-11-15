package BYT.Classes;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Serializable {
    private static final List<MenuItem> extent = new ArrayList<>();

    private String name;
    private String description;
    private long price;

    // Normal, Vegan; Food, Drink = multi-aspect inheritance

    public MenuItem(String name, String description, long price) {
        this.name = Validator.validateAttributes(name);
        this.description = Validator.validateAttributes(description);
        this.price = price;
        extent.add(this);
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

    @Override
    public String toString() {
        return "MenuItem{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
