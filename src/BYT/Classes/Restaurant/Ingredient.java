package BYT.Classes.Restaurant;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.*;

public class Ingredient implements Serializable {
    private static final List<Ingredient> extent = new ArrayList<>();
    private String name;
    private Set<MenuItem> menuItems = new HashSet<>();

    public Ingredient(String name) {
        this.name = Validator.validateAttributes(name);
        extent.add(this);
    }
    void addMenuItem(MenuItem item) {
        Validator.validateNullObjects(item);
        if (!menuItems.contains(item)) {
            menuItems.add(item);
            item.addIngredient(this);
        }
    }

    void removeMenuItem(MenuItem item) {
        if (menuItems.contains(item)) {
            menuItems.remove(item);
            item.removeIngredient(this);
        }
    }

    public Set<MenuItem> getMenuItems() {
        return Collections.unmodifiableSet(menuItems);
    }

    public void delete() {
        for (MenuItem item : menuItems) {
            item.removeIngredient(this);
        }
        extent.remove(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Validator.validateAttributes(name);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
