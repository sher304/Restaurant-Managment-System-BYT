package BYT.Tests;
import static org.junit.jupiter.api.Assertions.*;
import BYT.Classes.Person.Person;
import BYT.Classes.Person.Waiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersonTest extends TestBase<Person> {

    protected PersonTest() {
        super(Person.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
    }

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Person> list = new ArrayList<>();
        list.add(new Person("John", "Doe", "+48123456789", "a@a.com"));
        testPersistence(list);
    }

    @Test
    void findOrCreateCreatesNewPersonWhenNotFound() {
        int initialSize = extent().size();
        Person p1 = Person.findOrCreate("A", "B", "+48 111-222-333", "a@gmail.com");
        assertEquals(initialSize + 1, extent().size(), "findOrCreate should add a new Person to extent");
        assertEquals("A", p1.getFirstName(), "New Person object has correct name");
    }

    @Test
    void findOrCreateReturnsExistingPersonWhenFoundByPhoneNumber() {
        Person existing = new Person("A", "B", "+48444555666", "a@gmail.com");

        Person found = Person.findOrCreate("A", "B", "+48444555666", "a@pjwstk.com");

        assertEquals(1, extent().size(), "findOrCreate should NOT add a duplicate to extent");
        assertSame(existing, found, "The returned object must be the existing instance");
        assertEquals("a@gmail.com", found.getEmail(), "Existing data should be returned, not the new input");
    }

    @Test
    void constructorThrowsWhenMandatoryFieldIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new Person("", "B", "+48 123-456-789", "a@b.com"),
                "Empty string for First Name should throw exception");

        assertThrows(IllegalArgumentException.class,
                () -> new Person("D", "C", "   ", "a@b.com"),
                "Whitespace string for Phone Number should throw exception");

        assertEquals(0, extent().size(), "Extent should not contain object on validation failure");
    }

    @Test
    void setterThrowsWhenMandatoryFieldIsSetToEmpty() {
        Person p = new Person("Valid", "Name", "+48 123-456-789", null);
        assertThrows(IllegalArgumentException.class,
                () -> p.setLastName(""),
                "Setter for mandatory field should throw on empty string");
        assertThrows(IllegalArgumentException.class,
                () -> p.setPhoneNumber(null),
                "Setter for mandatory field should throw on null");
        assertEquals("Name", p.getLastName(), "Last name should remain unchanged after failed set");
    }

    @Test
    void constructorEmailValidationThrowsWhenInvalidFormatProvided() {
        assertThrows(IllegalArgumentException.class,
                () -> new Person("John", "Doe", "+48 123-456-789", "invalid-email.com"),
                "Email without '@' symbol should throw exception");

        assertEquals(0, extent().size(), "Extent should not contain object on validation failure");
    }

    @Test
    void constructorEmailValidationAllowsNullAndEmpty() {
        Person p1 = new Person("A", "B", "+48 123-456-789", null);
        assertNull(p1.getEmail());
        Person p2 = new Person("C", "D", "+48 987-654-321", "a@gmail.com");
        assertEquals(p2.getEmail(), "a@gmail.com", "Email attribute should match");
        assertEquals(2, extent().size(), "Both valid cases should be added to extent");
    }

}
