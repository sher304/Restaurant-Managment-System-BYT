package BYT.Tests;

import BYT.Classes.Restaurant.Menu;
import BYT.Classes.MenuItem.Normal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
        normal.add(new Normal("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu));
        testPersistence(normal);
    }

    @Test
    void testMeatTypeList() {
        Normal normal = new Normal("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, testMenu);
        normal.addMeatType("Beef");
        normal.addMeatType("Chicken");

        Collection<String> meatTypes = normal.getMeatTypes();
        Assertions.assertTrue(meatTypes.contains("Beef"));
        Assertions.assertTrue(meatTypes.contains("Chicken"));
        Assertions.assertEquals(2, meatTypes.size());
    }
}
