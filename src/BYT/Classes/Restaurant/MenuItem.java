package BYT.Classes.Restaurant;

import BYT.Classes.Order.OrderMenuItem;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.*;

public class MenuItem implements Serializable {
    private static final List<MenuItem> extent = new ArrayList<>();

    private String name;
    private String description;
    private long price;
    private Set<OrderMenuItem> orderMenuItems = new HashSet<>(); // [0..*]
    private Set<Ingredient> ingredients = new HashSet<>();
    private Menu menu;
    // Normal, Vegan; Food, Drink = multi-aspect inheritance


    public MenuItem(String name, String description, long price, Menu menu) {
        this.name = Validator.validateAttributes(name);
        this.description = Validator.validateAttributes(description);
        this.price = Validator.validatePrice(price);
        this.menu = (Menu) Validator.validateNullObjects(menu);
        this.menu.createMenuItem(this);
        extent.add(this);
    }

    void addIngredient(Ingredient ingredient) {
        Validator.validateNullObjects(ingredient);
        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
            ingredient.addMenuItem(this);
        }
    }

    void removeIngredient(Ingredient ingredient) {
        if (ingredients.contains(ingredient)) {
            ingredients.remove(ingredient);
            ingredient.removeMenuItem(this);
        }
    }
    public Set<Ingredient> getIngredients() {
        return Collections.unmodifiableSet(ingredients);
    }

    public void deleteIngredients() {
        for (Ingredient ingredient : new ArrayList<>(ingredients)) {
            removeIngredient(ingredient);
        }
    }

    // delete the MenuItem
    void delete() {
        for (Ingredient ingredient : ingredients) {
            removeIngredient(ingredient);
        }

        if (menu != null && menu.getItems().contains(this)) {
            menu.removeMenuItem(this);
        }
        extent.remove(this);
        this.menu = null;
    }

    public Set<OrderMenuItem> getOrderMenuItems() {
        return Collections.unmodifiableSet(orderMenuItems);
    }

    public void addOrderMenuItem(OrderMenuItem orderMenuItem) {
        Validator.validateNullObjects(orderMenuItem);
        if(orderMenuItem.getMenuItem() != this) throw new IllegalStateException("This OrderMenuItem is already assigned to a different MenuItem. OrderMenuItem junction classes cannot be moved");
        if(orderMenuItems.contains(orderMenuItem)) throw new IllegalArgumentException("This OrderMenuItem already exists");
        orderMenuItems.add(orderMenuItem);
    }

    // no change/move method for OrderMenuItem

    public void deleteOrderMenuItem(OrderMenuItem orderMenuItem) {
        orderMenuItems.remove(orderMenuItem);
        if(orderMenuItem.getOrder().getOrderMenuItems().contains(orderMenuItem))
            orderMenuItem.getOrder().deleteOrderMenuItem(orderMenuItem);
    }

    public Menu getMenu() {
        return menu;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem menuItem = (MenuItem) o;
        return price == menuItem.price && Objects.equals(name, menuItem.name) && Objects.equals(description, menuItem.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price);
    }
}
