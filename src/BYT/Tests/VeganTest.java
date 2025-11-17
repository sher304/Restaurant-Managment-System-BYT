package BYT.Tests;

import BYT.Classes.MenuItem.MenuItem;
import BYT.Classes.MenuItem.Normal;
import BYT.Classes.MenuItem.Vegan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class VeganTest extends TestBase<Vegan> {

    protected VeganTest() {
        super(Vegan.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
    }

    @Test
    void testVeganPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Vegan> vegan = new ArrayList<>();
        vegan.add(new Vegan("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, "PLABC123"));
        testPersistence(vegan);
    }

    @Test
    void testVeganCertificationID(){
        Vegan vegan = new Vegan("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 1, "ABC123");
        assertEquals(vegan.getCertificationID(), "ABC123");
    }

    @Test
    void nullCertificationIDThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Vegan vegan = new Vegan("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 1, null);
        });
    }

    @Test
    void setNullCertificationIDThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Vegan vegan = new Vegan("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 1, "ABC");
            vegan.setCertificationID(null);
        });
    }

    @Test
    void emptyCertificationIDThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Vegan vegan = new Vegan("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 1, "");
        });
    }
    @Test
    void setEmptyCertificationIDThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Vegan vegan = new Vegan("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 1, "1643A");
            vegan.setCertificationID("");
        });
    }
}
