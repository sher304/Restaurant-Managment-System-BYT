package BYT.Tests;

import BYT.Classes.MenuItem.Ingredient;
import BYT.Classes.Person.Waiter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class IngredientTest extends TestBase<Ingredient> {

    protected IngredientTest(){
        super(Ingredient.class);
    }


    @BeforeEach
    void setup() {
        clearExtentInMemoryList();
    }

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Ingredient> list = new ArrayList<>();
        list.add(new Ingredient("Egg"));
        testPersistence(list);
    }

    @Test
    void constructor_initializesCorrectlyAndAddsToExtent() {
        Ingredient i1 = new Ingredient("Salt");
        assertEquals(1, extent().size(), "Ingredient should be added to its extent upon creation");
        assertSame(i1, extent().get(0), "The created instance must be in the extent list");
        assertEquals("Salt", i1.getName(), "Name attribute should be set correctly");
    }

    @Test
    void checkFieldOrThrowsWhenNameIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new Ingredient(" "),
                "Set Validator for empty/whitespace name");
        assertEquals(0, extent().size(), "No object should be added to extent on validation failure");
    }

    @Test
    void setName_throwsWhenNewNameIsEmpty() {
        Ingredient i1 = new Ingredient("Sugar");
        assertThrows(IllegalArgumentException.class,
                () -> i1.setName(null),
                "Setter must use Validator to throw exception for null name");
        assertEquals("Sugar", i1.getName(), "Name should remain unchanged after failed set operation");
    }
}

