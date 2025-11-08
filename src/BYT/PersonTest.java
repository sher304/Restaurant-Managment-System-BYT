package BYT;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @SuppressWarnings("unchecked")
    private static List<Person> extent() {
        try {
            Field f = Person.class.getDeclaredField("extent");
            f.setAccessible(true);
            return (List<Person>) f.get(null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private static void clearExtent() {
        extent().clear();
    }

    @BeforeEach
    void setUp() {
        clearExtent();
    }


    @Test
    void findOrCreateCreatesNewPersonWhenNotFound() {
        int initialSize = extent().size();
        Person p1 = Person.findOrCreate("A", "B", "111-222-333", "a@gmail.com");
        assertEquals(initialSize + 1, extent().size(), "findOrCreate should add a new Person to extent");
        assertEquals("A", p1.getFirstName(), "New Person object has correct name");
    }

    @Test
    void findOrCreateReturnsExistingPersonWhenFoundByPhoneNumber() {
        Person existing = new Person("A", "B", "444-555-666", "a@gmail.com");

        Person found = Person.findOrCreate("A", "B", "444-555-666", "a@pjwstk.com");

        assertEquals(1, extent().size(), "findOrCreate should NOT add a duplicate to extent");
        assertSame(existing, found, "The returned object must be the existing instance");
        assertEquals("a@gmail.com", found.getEmail(), "Existing data should be returned, not the new input");
    }

    @Test
    void constructorThrowsWhenMandatoryFieldIsEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> new Person("", "B", "123-456-789", "a@b.com"),
                "Empty string for First Name should throw exception");

        assertThrows(IllegalArgumentException.class,
                () -> new Person("D", "C", "   ", "a@b.com"),
                "Whitespace string for Phone Number should throw exception");

        assertEquals(0, extent().size(), "Extent should not contain object on validation failure");
    }

    @Test
    void setterThrowsWhenMandatoryFieldIsSetToEmpty() {
        Person p = new Person("Valid", "Name", "123-456-789", null);
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
                () -> new Person("John", "Doe", "123-456-789", "invalid-email.com"),
                "Email without '@' symbol should throw exception");

        assertEquals(0, extent().size(), "Extent should not contain object on validation failure");
    }

    @Test
    void constructorEmailValidationAllowsNullAndEmpty() {
        Person p1 = new Person("A", "B", "123-456-789", null);
        assertNull(p1.getEmail());
        Person p2 = new Person("C", "D", "987-654-321", "   ");
        assertNull(p2.getEmail(), "Whitespace email should be treated as null");
        assertEquals(2, extent().size(), "Both valid cases should be added to extent");
    }
}
