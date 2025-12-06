package BYT.Tests;

import BYT.Classes.Restaurant.Menu;
import BYT.Classes.MenuItem.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class FoodTest extends TestBase<Food> {

    private Menu testMenu;

    protected FoodTest() {
        super(Food.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
    }

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Food> list = new ArrayList<>();
        list.add(new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, 1000, testMenu));
        testPersistence(list);
    }

    @Test
    void checkFoodWeightNonZeroAttribute() {
        assertThrows(IllegalArgumentException.class,
                () -> new Food("A", "B", 20, 0, testMenu),
                "Weights of physical attributes bust be > 0!"
        );
    }

    @Test
    void checkPriceNonZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new Food("A", "B", 0, 10, testMenu),
                "Price must be greater than 0!"
                );
    }

    @Test
    void checkPriceNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Food("A", "B", -10, 10, testMenu),
                "Price must be positive number!"
        );
    }
}
