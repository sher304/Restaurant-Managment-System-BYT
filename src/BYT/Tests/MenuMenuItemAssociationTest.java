package BYT.Tests;

import BYT.Classes.Menu.Menu;
import BYT.Classes.MenuItem.MenuItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class MenuMenuItemAssociationTest {
    private Menu createValidMenu() {
        LocalDate today = LocalDate.now();
        LocalDate end = today.plusDays(8);
        return new Menu(today, end);
    }
    @Test
    public void creatingMenuItemAddsItToMenu() {
        Menu menu = createValidMenu();

        MenuItem item = new MenuItem("Pizza", "Pepperoni pizza", 1000L, menu);

        Assertions.assertTrue(menu.getItems().contains(item));
        Assertions.assertEquals(menu, item.getMenu());
    }

    @Test
    public void deletingMenuItemRemovesItFromMenu() {
        Menu menu = createValidMenu();
        MenuItem item = new MenuItem("Pizza", "Pepperoni pizza", 1000L, menu);

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

        MenuItem item = new MenuItem("Pizza", "Pepperoni pizza", 1000L, menu1);

        Assertions.assertThrows(IllegalStateException.class, () ->
                menu2.createMenuItem(item)
        );
    }
}
