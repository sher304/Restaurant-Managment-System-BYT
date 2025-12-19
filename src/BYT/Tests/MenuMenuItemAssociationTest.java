package BYT.Tests;

import BYT.Classes.Restaurant.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class MenuMenuItemAssociationTest {
    private Menu createValidMenu() {
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(8);
        return new Menu(today, end);
    }
    /*@Test
    public void creatingMenuItemAddsItToMenu() {
        Menu menu = createValidMenu();

        MenuItem item = new Food("Pizza", "Pepperoni pizza", 1000L, 1000, menu, MenuItem.DietInheritanceTypes.NORMAL);

        Assertions.assertTrue(menu.getItems().contains(item));
        Assertions.assertEquals(menu, item.getMenu());
    }*/

    @Test
    public void deletingMenuItemRemovesItFromMenu() {
        Menu menu = createValidMenu();
        MenuItem item = new Food("Pizza", "Pepperoni pizza", 1000L, 1000, menu, MenuItem.DietInheritanceTypes.NORMAL);

        item.delete();

        Assertions.assertFalse(menu.getItems().contains(item));
        Assertions.assertNull(item.getMenu());
    }

    @Test
    public void createMenuItemWithNullThrowsException() {
        Menu menu = createValidMenu();

        Assertions.assertThrows(IllegalArgumentException.class, () ->
                menu.createMenuItem(null)
        );
    }

    @Test
    public void menuItemCannotBelongToTwoMenus() {
        Menu menu1 = createValidMenu();
        Menu menu2 = createValidMenu();

        MenuItem item = new Food("Pizza", "Pepperoni pizza", 1000L, 1000, menu1, MenuItem.DietInheritanceTypes.NORMAL);

        Assertions.assertThrows(IllegalStateException.class, () ->
                menu2.createMenuItem(item)
        );
    }
}
