package BYT.Tests;

import BYT.Classes.Menu.Menu;
import BYT.Classes.MenuItem.Ingredient;
import BYT.Classes.MenuItem.MenuItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class MenuItemIngredientAssociationTest {
    private Menu createValidMenu() {
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(8);
        return new Menu(today, end);
    }
    @Test
    public void addIngredientShouldAffectBothSides(){
        Menu menu=createValidMenu();
        MenuItem item=new MenuItem("Pizza","Pepperoni pizza",1000L,menu);
        Ingredient ingredient=new Ingredient("Pepperoni");
        item.addIngredient(ingredient);
        Assertions.assertTrue(item.getIngredients().contains(ingredient));
        Assertions.assertTrue(ingredient.getMenuItems().contains(item));

    }
    @Test
    public void removeIngredientShouldEffectBothSides(){
        Menu menu=createValidMenu();
        MenuItem item=new MenuItem("Pizza","Pepperoni pizza",1000L,menu);
        Ingredient ingredient=new Ingredient("Pepperoni");
        item.addIngredient(ingredient);
        item.removeIngredient(ingredient);
        Assertions.assertFalse(item.getIngredients().contains(ingredient));
        Assertions.assertFalse(ingredient.getMenuItems().contains(item));

    }
    @Test
    public void removeMenuItemShouldRemoveIngredientToo(){
        Menu menu=createValidMenu();
        MenuItem item=new MenuItem("Pizza","Pepperoni pizza",1000L,menu);
        Ingredient ingredient=new Ingredient("Pepperoni");
        item.addIngredient(ingredient);
        ingredient.removeMenuItem(item);
        Assertions.assertFalse(item.getIngredients().contains(ingredient));
        Assertions.assertFalse(ingredient.getMenuItems().contains(item));
    }
    @Test
    public void addMenuItemFromIngredientSideShouldAffectBothSides() {
        Menu menu = createValidMenu();
        MenuItem item = new MenuItem("Pizza", "Pepperoni pizza", 1000L, menu);
        Ingredient ingredient = new Ingredient("Pepperoni");
        ingredient.addMenuItem(item);
        Assertions.assertTrue(item.getIngredients().contains(ingredient));
        Assertions.assertTrue(ingredient.getMenuItems().contains(item));
    }
    @Test
    public void addingSameIngredientTwiceToMenuItemShouldNotDuplicate() {
        Menu menu = createValidMenu();
        MenuItem item = new MenuItem("Pizza", "Pepperoni pizza", 1000L, menu);
        Ingredient ingredient = new Ingredient("Pepperoni");
        item.addIngredient(ingredient);
        item.addIngredient(ingredient);
        Assertions.assertEquals(1, item.getIngredients().size());
        Assertions.assertEquals(1, ingredient.getMenuItems().size());
        Assertions.assertTrue(item.getIngredients().contains(ingredient));
        Assertions.assertTrue(ingredient.getMenuItems().contains(item));
    }
    @Test
    public void deleteIngredientsOnMenuItemShouldClearAssociations() {
        Menu menu = createValidMenu();
        MenuItem item = new MenuItem("Pizza", "Pepperoni pizza", 1000L, menu);
        Ingredient cheese = new Ingredient("Cheese");
        Ingredient ham = new Ingredient("Ham");
        item.addIngredient(cheese);
        item.addIngredient(ham);
        item.deleteIngredients();
        Assertions.assertTrue(item.getIngredients().isEmpty());
        Assertions.assertFalse(cheese.getMenuItems().contains(item));
        Assertions.assertFalse(ham.getMenuItems().contains(item));
    }
    @Test
    public void deletingIngredientShouldRemoveItFromAllMenuItems() {
        Menu menu = createValidMenu();
        MenuItem pizza = new MenuItem("Pizza", "Pepperoni pizza", 1000L, menu);
        MenuItem pasta = new MenuItem("Pasta", "Cheese pasta", 900L, menu);
        Ingredient cheese = new Ingredient("Cheese");
        pizza.addIngredient(cheese);
        pasta.addIngredient(cheese);
        cheese.delete();
        Assertions.assertFalse(pizza.getIngredients().contains(cheese));
        Assertions.assertFalse(pasta.getIngredients().contains(cheese));
        Assertions.assertTrue(cheese.getMenuItems().isEmpty());
    }
    @Test
    public void addIngredientShouldThrowWhenNull() {
        Menu menu = createValidMenu();
        MenuItem item = new MenuItem("Pizza", "Pepperoni pizza", 1000L, menu);

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> item.addIngredient(null));
    }

    @Test
    public void addMenuItemShouldThrowWhenNull() {
        Ingredient ingredient = new Ingredient("Pepperoni");

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> ingredient.addMenuItem(null));
    }


}
