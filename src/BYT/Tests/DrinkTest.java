package BYT.Tests;

import BYT.Classes.MenuItem.Drink;
import BYT.Classes.MenuItem.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DrinkTest extends TestBase<Drink> {

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
        list.add(new Drink("Small water", "Small water bottle", 7, 1000));
        testPersistence(list);
    }

    @Test
    void checkDrinkVolumeNonZeroAttribute() {
        assertThrows(IllegalArgumentException.class,
                () -> new Drink("A", "B", 20, 0),
                "Volume of physical attributes bust be > 0!"
        );
    }

    @Test
    void checkPriceNonZero() {
        assertThrows(IllegalArgumentException.class,
                () -> new Drink("A", "B", 0, 10),
                "Price must be greater than 0!"
        );
    }

    @Test
    void checkPriceNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> new Drink("A", "B", -10, 10),
                "Price must be positive number!"
        );
    }


}
