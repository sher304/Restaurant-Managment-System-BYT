package BYT.Tests;

import BYT.Classes.Restaurant.Food;
import BYT.Classes.Restaurant.Menu;
import BYT.Classes.Restaurant.Normal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static BYT.Classes.Restaurant.MenuItem.DietInheritanceTypes.NORMAL;

public class NormalTest extends TestBase<Normal> {
    private Menu testMenu;

    protected NormalTest() {
        super(Normal.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
    }

    @Test
    void testNormalPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Normal> normal = new ArrayList<>();
        Food food = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, 1000, testMenu, NORMAL);
        normal.add(food.getNormalPart());
        testPersistence(normal);
    }

    @Test
    void testMeatTypeList() {
        Food food = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, 1000, testMenu, NORMAL);
        Normal normal = food.getNormalPart();
        normal.addMeatType("Beef");
        normal.addMeatType("Chicken");

        Collection<String> meatTypes = normal.getMeatTypes();
        Assertions.assertTrue(meatTypes.contains("Beef"));
        Assertions.assertTrue(meatTypes.contains("Chicken"));
        Assertions.assertEquals(2, meatTypes.size());
    }
}
