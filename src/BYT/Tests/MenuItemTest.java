package BYT.Tests;

import BYT.Classes.Restaurant.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static BYT.Classes.Restaurant.MenuItem.DietInheritanceTypes.NORMAL;
import static BYT.Classes.Restaurant.MenuItem.DietInheritanceTypes.VEGAN;
import static java.awt.SystemColor.menu;
import static org.junit.jupiter.api.Assertions.*;

public class MenuItemTest extends TestBase<MenuItem> {

    private Menu testMenu;
    private Menu menu;

    protected MenuItemTest() {
        super(MenuItem.class);
    }

    @BeforeEach
    void setUp() {
        //clearExtentInMemoryList();
        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        menu = new Menu(LocalDate.now(), LocalDate.now().plusDays(3));
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
        assertTrue(extent().contains(item));
        futureMenu.delete();
        assertFalse(extent().contains(item));
    }
    // 1. CLASSICAL INHERITANCE (Food / Drink extends MenuItem)
    @Test
    void foodIsAMenuItem() {
        MenuItem food = new Food(
                "Pizza",
                "Pepperoni pizza",
                1200,
                300,
                menu,
                NORMAL
        );

        assertTrue(food instanceof MenuItem);
        assertTrue(food instanceof Food);
    }

    @Test
    void drinkIsAMenuItem() {
        MenuItem drink = new Drink(
                "Cola",
                "Sparkling soft drink",
                500,
                330,
                menu,
                VEGAN
        );

        assertTrue(drink instanceof MenuItem);
        assertTrue(drink instanceof Drink);
    }

    // =========================================================
    // 2. DISJOINT INHERITANCE (Normal XOR Vegan)
    // =========================================================

    @Test
    void normalMenuItemHasNormalPartOnly() {
        MenuItem item = new Food(
                "Burger",
                "Beef burger",
                1500,
                400,
                menu,
                NORMAL
        );

        assertNotNull(item.getNormalPart());
        assertNull(item.getVeganPart());
    }

    @Test
    void veganMenuItemHasVeganPartOnly() {
        MenuItem item = new Food(
                "Falafel",
                "Vegan falafel wrap",
                1300,
                350,
                menu,
                VEGAN
        );

        assertNotNull(item.getVeganPart());
        assertNull(item.getNormalPart());
    }



    // =========================================================
    // 3. TOTAL INHERITANCE (MenuItem MUST be Normal or Vegan)
    // =========================================================

    @Test
    void menuItemAlwaysHasExactlyOneDietType() {
        MenuItem normalItem = new Food(
                "Steak",
                "Grilled steak",
                2500,
                500,
                menu,
                NORMAL
        );

        MenuItem veganItem = new Food(
                "Tofu Bowl",
                "Tofu with veggies",
                1800,
                400,
                menu,
                VEGAN
        );

        assertTrue(
                (normalItem.getNormalPart() != null && normalItem.getVeganPart() == null)
        );

        assertTrue(
                (veganItem.getVeganPart() != null && veganItem.getNormalPart() == null)
        );
    }

    // =========================================================
    // 4. LIFECYCLE OWNERSHIP (composition behavior)
    // =========================================================

    @Test
    void deletingMenuItemDeletesDietPart() {
        MenuItem item = new Food(
                "Soup",
                "Tomato soup",
                700,
                300,
                menu,
                VEGAN
        );

        Vegan veganPart = item.getVeganPart();
        assertNotNull(veganPart);

        item.delete();

        assertFalse(Vegan.getExtent().contains(veganPart));
    }
}
