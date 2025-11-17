package BYT.Tests;

import BYT.Classes.Person.Chef;
import BYT.Classes.Person.Customer;
import BYT.Classes.Person.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChefTest extends TestBase<Chef> {

    protected ChefTest() {
        super(Chef.class);
    }

    @BeforeEach
    void setup() {
        clearExtentInMemoryList();
    }


    @Test
    void baseSalaryValidatorThrowsExceptionWhenNewSalaryLower() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chef("A", "B", "+48111222333", "a@gmail.com", 3000L),
                "Chef salary should be greater or equal to the base salary");
        assertEquals(0, extent().size(), "Extent should not contain object on validation failure");
    }

    @Test
    void findOrCreateCreatesNewChefWhenNotFound() {
        int initialSize = extent().size();
        Chef chef = new Chef("A", "B", "+48111222333", "a@gmail.com", 7000L);
        assertEquals(initialSize + 1, extent().size(), "findOrCreate should add a new Person to extent");
        assertEquals("A", chef.getFirstName(), "New Customer object has correct name");
    }
}

