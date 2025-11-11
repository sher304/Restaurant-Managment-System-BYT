package BYT;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;

public class NormalTest {

    // if Normal relies on the MenuItem extent through inheritance that we don't implement in this assignment,
    // we won't be able to test persistence?

    @Test
    void testAttributes() {
        Normal normal = new Normal();
        normal.addMeatType("Beef");
        normal.addMeatType("Chicken");

        Collection<String> meatTypes = normal.getMeatTypes();
        Assertions.assertTrue(meatTypes.contains("Beef"));
        Assertions.assertTrue(meatTypes.contains("Chicken"));
    }
}
