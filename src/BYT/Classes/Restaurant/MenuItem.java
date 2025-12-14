package BYT.Classes.Restaurant;

import BYT.Classes.Order.OrderMenuItem;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.*;

public abstract class MenuItem implements Serializable {
    /*private static final List<MenuItem> extent = new ArrayList<>();

    public static List<MenuItem> getMenuItemExtent(){
        return Collections.unmodifiableList(extent);
    }*/

    private String name;
    private String description;
    private long price;
    private Set<OrderMenuItem> orderMenuItems = new HashSet<>(); // [0..*]
    private Set<Ingredient> ingredients = new HashSet<>();
    private Menu menu;
    // Food, Drink = normal Java inheritance
    // Normal, Vegan = Composition, separate classes
    private Normal normalPart;
    private Vegan veganPart;

    public enum DietInheritanceTypes {
        NORMAL,
        VEGAN
    }

    protected MenuItem(String name, String description, long price, Menu menu, DietInheritanceTypes dietInheritanceTypes) {
        this.name = Validator.validateAttributes(name);
        this.description = Validator.validateAttributes(description);
        this.price = Validator.validatePrice(price);
        this.menu = (Menu) Validator.validateNullObjects(menu);

        switch(dietInheritanceTypes) {
            case NORMAL:
                setNormalPart(new Normal(this));
                break;
            case VEGAN:
                setVeganPart(new Vegan(this));
                break;
            default:
                throw new IllegalArgumentException("Diet type not implemented");
        }

        this.menu.createMenuItem(this);
        //extent.add(this);
    }

    private boolean isNormalVeganInheritanceCreated(){
        return normalPart != null || veganPart != null;
    }

    public Normal getNormalPart() {
        return normalPart;
    }

    private void setNormalPart(Normal normalPart) {
        if(isNormalVeganInheritanceCreated()) throw new IllegalStateException("This inheritance is Disjoint and has already been created.");
        Validator.validateNullObjects(normalPart);
        this.normalPart = normalPart;
    }

    public Vegan getVeganPart() {
        return veganPart;
    }

    private void setVeganPart(Vegan veganPart) {
        if(isNormalVeganInheritanceCreated()) throw new IllegalStateException("This inheritance is Disjoint and has already been created.");
        Validator.validateNullObjects(veganPart);
        this.veganPart = veganPart;
    }

    public void addIngredient(Ingredient ingredient) {
        Validator.validateNullObjects(ingredient);
        if (!ingredients.contains(ingredient)) {
            ingredients.add(ingredient);
            ingredient.addMenuItem(this);
        }
    }

    public void removeIngredient(Ingredient ingredient) {
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

    // Hook: each concrete subclass deletes itself
    protected abstract void deleteSubclass();

    // delete the MenuItem
    public void delete() {
        for (Ingredient ingredient : ingredients) {
            removeIngredient(ingredient);
        }

        if (menu != null && menu.getItems().contains(this)) {
            menu.removeMenuItem(this);
        }
        this.deleteSubclass();
        this.menu = null;
        if(normalPart != null && Normal.getExtent().contains(normalPart)) normalPart.delete();
        if(veganPart != null && Vegan.getExtent().contains(veganPart)) veganPart.delete();
        this.normalPart = null;
        this.veganPart = null;
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
        return price == menuItem.price && name.equals(menuItem.name) && description.equals(menuItem.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price);
    }
}
