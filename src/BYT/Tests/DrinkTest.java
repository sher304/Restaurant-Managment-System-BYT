package BYT.Tests;

import BYT.Classes.MenuItem.Drink;
import BYT.Classes.MenuItem.Food;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
