package BYT.Tests;

import BYT.Classes.Restaurant.Menu;
import BYT.Classes.Restaurant.MenuItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MenuItemTest extends TestBase<MenuItem> {

    private Menu testMenu;

    protected MenuItemTest() {
        super(MenuItem.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
    }

    @Test
    void testMenuItemPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<MenuItem> menuItem = new ArrayList<>();
        menuItem.add(new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        testPersistence(menuItem);
    }

    @Test
    void emptyStringAttributesThrow(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("", "Marinated mixed olives with orange zest and herbs", 7, testMenu);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("Citrus-Brined Olives", "", 7, testMenu);
        });
    }

    @Test
    void nullNonOptionalAttributesThrow(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem(null, "Marinated mixed olives with orange zest and herbs", 7, testMenu);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("Citrus-Brined Olives", null, 7, testMenu);
        });
    }

    @Test
    void zeroPricesThrow() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 0, testMenu);
        });
    }

    @Test
    void negativePricesThrow(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", -3, testMenu);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            MenuItem normal = new MenuItem("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", -6, testMenu);
        });
    }
}
