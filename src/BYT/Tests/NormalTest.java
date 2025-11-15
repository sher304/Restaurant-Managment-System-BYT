package BYT.Tests;

import BYT.Classes.Normal;
import BYT.Helpers.Extents;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NormalTest extends TestBase<Normal> {

    protected NormalTest() {
        super(Normal.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
    }

    @Test
    void testNormalPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Normal> normal = new ArrayList<>();
        normal.add(new Normal("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7));
        testPersistence(normal);
    }

    @Test
    void emptyStringAttributesThrow(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Normal normal = new Normal("", "Marinated mixed olives with orange zest and herbs", 7);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Normal normal = new Normal("Citrus-Brined Olives", "", 7);
        });
    }

    @Test
    void testMeatTypeList() {
        Normal normal = new Normal("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7);
        normal.addMeatType("Beef");
        normal.addMeatType("Chicken");

        Collection<String> meatTypes = normal.getMeatTypes();
        Assertions.assertTrue(meatTypes.contains("Beef"));
        Assertions.assertTrue(meatTypes.contains("Chicken"));
        Assertions.assertEquals(2, meatTypes.size());
    }
}
