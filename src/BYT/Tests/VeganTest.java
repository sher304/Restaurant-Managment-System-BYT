package BYT.Tests;

import BYT.Classes.Restaurant.Food;
import BYT.Classes.Restaurant.Menu;
import BYT.Classes.Restaurant.Vegan;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static BYT.Classes.Restaurant.MenuItem.DietInheritanceTypes.VEGAN;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class VeganTest extends TestBase<Vegan> {

    private Menu testMenu;
    private Food example;

    protected VeganTest() {
        super(Vegan.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
        testMenu = new Menu(LocalDate.now(), LocalDate.now().plusDays(5));
    }

    @Test
    void testVeganPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Vegan> vegan = new ArrayList<>();
        Food item = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 7, 1000, testMenu, VEGAN);
        Vegan veganPart = item.getVeganPart();
        veganPart.setCertificationID("PLABC123");
        vegan.add(veganPart);
        testPersistence(vegan);
    }

    @Test
    void testVeganCertificationID(){
        Food item = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 1, 1000, testMenu, VEGAN);
        Vegan vegan = item.getVeganPart();
        vegan.setCertificationID("ABC123");
        assertEquals(vegan.getCertificationID(), "ABC123");
    }

    @Test
    void nullCertificationIDThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Food item = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 1, 1000, testMenu, VEGAN);
            Vegan vegan = item.getVeganPart();
            vegan.setCertificationID(null);
        });
    }

    @Test
    void setNullCertificationIDThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Food item = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 1, 1000, testMenu, VEGAN);
            Vegan vegan = item.getVeganPart();
            vegan.setCertificationID("ABC");
            vegan.setCertificationID(null);
        });
    }

    @Test
    void emptyCertificationIDThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Food item = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 1, 1000, testMenu, VEGAN);
            Vegan vegan = item.getVeganPart();
            vegan.setCertificationID("");
        });
    }
    @Test
    void setEmptyCertificationIDThrows() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Food item = new Food("Citrus-Brined Olives", "Marinated mixed olives with orange zest and herbs", 1, 1000, testMenu, VEGAN);
            Vegan vegan = item.getVeganPart();
            vegan.setCertificationID("1643A");
            vegan.setCertificationID("");
        });
    }
}
