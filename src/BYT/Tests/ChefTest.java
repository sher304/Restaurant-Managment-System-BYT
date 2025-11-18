package BYT.Tests;

import BYT.Classes.MenuItem.Food;
import BYT.Classes.Person.Chef;
import BYT.Classes.Person.Customer;
import BYT.Classes.Person.Person;
import BYT.Classes.Person.Waiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Chef> list = new ArrayList<>();
        list.add(new Chef("John", "Doe", "+48123456789", "a@a.com", 10000));
        testPersistence(list);
    }

    @Test
    void checkSalaryFields() {
        Chef chef = Chef.findOrCreate("A", "B", "+48111222333", "a@gmail.com", 7000L);
        assertEquals(chef.getSalary(), 7000L, "Salary is not same. Problem with Constructor");
        assertEquals(chef.getFirstName(), "A", "Name is not same. Problem with Constructor");
        assertEquals(chef.getLastName(), "B", "Last Name is not same. Problem with Constructor");
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

    @Test
    void constructor_throwsWhenMandatoryFieldIsInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Chef("A", " ", "+48111222333", null, 7000L),
                "Constructor must enforce validation inherited from Person/Chef.");

        assertEquals(0, extent().size(), "No object should be added to extent on validation failure.");
    }
}

