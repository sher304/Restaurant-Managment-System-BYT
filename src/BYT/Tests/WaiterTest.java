package BYT.Tests;

import BYT.Classes.Menu.Menu;
import BYT.Classes.MenuItem.Vegan;
import BYT.Classes.Person.Chef;
import BYT.Classes.Person.Customer;
import BYT.Classes.Person.Waiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class WaiterTest extends TestBase<Waiter> {

    protected WaiterTest() {
        super(Waiter.class);
    }

    @BeforeEach
    void setup() {
        clearExtentInMemoryList();
    }

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Waiter> list = new ArrayList<>();
        list.add(new Waiter("John", "Doe", "+48123456789", "a@a.com", 10000));
        testPersistence(list);
    }

    @Test
    void findOrCreateCreatesNewCustomerWhenNotFound() {
        int initialSize = extent().size();
        Waiter waiter = Waiter.findOrCreate("A", "B", "+48111222333", "a@gmail.com", 7000L);
        assertEquals(initialSize + 1, extent().size(), "findOrCreate should add a new Person to extent");
        assertEquals("A", waiter.getFirstName(), "New Waiter object has correct name");
    }

    @Test
    void checkSalaryFields() {
        Waiter waiter = new Waiter("A", "B", "+48111222333", "a@gmail.com", 7000L);
        assertEquals(waiter.getSalary(), 7000L, "Salary is not same. Problem with Constructor");
        assertEquals(waiter.getFirstName(), "A", "Name is not same. Problem with Constructor");
        assertEquals(waiter.getLastName(), "B", "Last Name is not same. Problem with Constructor");
    }

    @Test
    void constructor_throwsWhenMandatoryFieldIsInvalid() {
        assertThrows(IllegalArgumentException.class,
                () -> new Waiter("A", " ", "+48111222333", null, 7000L),
                "Constructor must enforce validation inherited from Person/Waiter.");

        assertEquals(0, extent().size(), "No object should be added to extent on validation failure.");
    }

    @Test
    void baseSalaryValidatorThrowsExceptionWhenNewSalaryLower() {
        assertThrows(IllegalArgumentException.class,
                () -> new Waiter("A", "B", "+48111222333", "a@gmail.com", 3000L),
                "Chef salary should be greater or equal to the base salary");
        assertEquals(0, extent().size(), "Extent should not contain object on validation failure");
    }



}
