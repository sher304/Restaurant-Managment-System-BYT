package BYT.Tests;

import BYT.Classes.Restaurant.Food;
import BYT.Classes.Restaurant.Menu;
import BYT.Classes.Restaurant.MenuItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static BYT.Classes.Restaurant.MenuItem.DietInheritanceTypes.NORMAL;

public class MenuItemTest extends TestBase<MenuItem> {

    private Menu testMenu;

    protected MenuItemTest() {
        super(MenuItem.class);
    }

    @BeforeEach
    void setUp() {
        //clearExtentInMemoryList();
        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
    }

    @Test
    void testMenuItemPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<MenuItem> menuItem = new ArrayList<>();
        menuItem.add(new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, 1000, testMenu, NORMAL));
        testPersistence(menuItem);
    }

    @Test
    void emptyStringAttributesThrow(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new Food("", "Marinated mixed olives with orange zest and herbs", 7, 1000, testMenu, NORMAL);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new Food("Citrus-Brined Olives", "", 7, 1000, testMenu, NORMAL);
        });
    }

    @Test
    void nullNonOptionalAttributesThrow(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new Food(null, "Marinated mixed olives with orange zest and herbs", 7, 1000, testMenu, NORMAL);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new Food("Citrus-Brined Olives", null, 7, 1000, testMenu, NORMAL);
        });
    }

    @Test
    void zeroPricesThrow() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 0, 1000, testMenu, NORMAL);
        });
    }

    @Test
    void negativePricesThrow(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", -3, 1000, testMenu, NORMAL);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", -6, 1000, testMenu, NORMAL);
        });
    }

    @Test
    public void deletingMenuDeletesItsMenuItems() throws Exception {
        Menu futureMenu = new Menu(LocalDate.now().plusDays(1), LocalDate.now().plusDays(5));
        MenuItem item = new Food("Pizza", "Pepperoni pizza", 1000L, 1000, futureMenu, NORMAL);

        // TODO: broken because of changed extent
        Assertions.assertTrue(extent().contains(item));
        futureMenu.delete();
        Assertions.assertFalse(extent().contains(item));
    }
}
