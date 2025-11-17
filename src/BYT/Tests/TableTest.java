package BYT.Tests;

import BYT.Classes.Table.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TableTest extends TestBase<Table> {

    protected TableTest() {
        super(Table.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
    }

    @Test
    void checkNegativeNumberOfPeople_OtherwiseException() {
        assertThrows(IllegalArgumentException.class,
                () -> new Table("2", -4),
                "Number of people can not be negative");
    }

    @Test
    void checkNumberOfTableSetted() {
        Table t = new Table("2", 4);
        assertEquals(4, t.getMaxNumberOfPeople(), "Number of people is not same, problem with setters, or constructor");
    }

    @Test
    void checkTableNumberSetted() {
        Table t = new Table("A123", 4);
        assertEquals("A123", t.getTableNumber(), "Table number is not same. Check setter of Constructor");
    }
}
