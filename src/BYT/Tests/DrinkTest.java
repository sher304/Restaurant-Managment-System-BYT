package BYT.Tests;

import BYT.Classes.Restaurant.Menu;
import BYT.Classes.MenuItem.Drink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrinkTest extends TestBase<Drink> {

    private Menu testMenu;
    protected DrinkTest(){
        super(Drink.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
    }

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Drink> list = new ArrayList<>();
        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
        list.add(new Drink("Small water", "Small water bottle", 7, 1000, testMenu));
        testPersistence(list);
    }

    @Test
    void checkDrinkVolumeNonZeroAttribute() {
        assertThrows(IllegalArgumentException.class,
                () -> new Drink("A", "B", 20, 0, testMenu),
                "Volume of physical attributes bust be > 0!"
        );
    }

    @Test
    void checkPriceNonZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new Drink("A", "B", 0, 10, testMenu),
                "Price must be greater than 0!"
        );
    }

    @Test
    void checkPriceNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Drink("A", "B", -10, 10, testMenu),
                "Price must be positive number!"
        );
    }


}
