package BYT.Tests;

import BYT.Classes.Person.Customer;
import BYT.Classes.Table.Reservation;
import BYT.Classes.Table.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReservationTest extends TestBase<Reservation> {

    private final LocalDateTime NOW = LocalDateTime.now();
    private final LocalDateTime LATER = NOW.plusHours(1);
    private Customer testCustomer;
    private Table table1;
    private Table table2;
    private TestBase<Table> tableTestBase = new TestBase<>(Table.class);
    protected ReservationTest() {
        super(Reservation.class);
    }

    @BeforeEach
    void setUp() {
        clearExtentInMemoryList();
        tableTestBase.clearExtentInMemoryList();

        this.testCustomer = new Customer("A", "B", "+48111222333", "t@u.com", 0);
        this.table1 = new Table("T1", 4);
        this.table2 = new Table("T2", 8);
    }

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Reservation> list = new ArrayList<>();
        list.add(new Reservation(NOW, NOW, testCustomer, 2, table1));
        testPersistence(list);
    }

    @Test
    void getFreeTables_returnsOnlyAvailableTables() {
        new Reservation(NOW, NOW, testCustomer, 2, table1);
        ArrayList<Table> smallGroup = Reservation.getFreeTables(3, NOW, NOW);
        assertEquals(1, smallGroup.size(), "Should only return 1 free table (T2)");
        assertEquals(table2.getTableNumber(), smallGroup.get(0).getTableNumber());
        ArrayList<Table> largeGroup = Reservation.getFreeTables(5, NOW, NOW);
        assertEquals(1, largeGroup.size(), "Should only return 1 free table (T2)");
    }

    @Test
    void getFreeTables_handlesTimeSlotConflicts() {
        new Reservation(LATER, LATER, testCustomer, 4, table2);
        ArrayList<Table> todayTables = Reservation.getFreeTables(2, NOW, NOW);
        assertEquals(2, todayTables.size(), "Both tables should be free for a different day.");
    }

    @Test
    void cancelReservation_successfulCancellation() {
        new Reservation(NOW, NOW, testCustomer, 2, table1);
        Reservation r2 = new Reservation(NOW.plusDays(1), NOW.plusDays(1), testCustomer, 4, table2);
        assertEquals(2, extent().size(), "Precondition: 2 reservations exist.");
        r2.deleteTable();
        assertEquals(1, extent().size(), "Only 1 reservation should remain in extent.");
    }

    @Test
    void createReservation_successfulCreation() {
        String selectedTable = table1.getTableNumber();
        Reservation r = Reservation.createReservation(NOW, NOW, testCustomer, 4, selectedTable);
        assertEquals(1, extent().size(), "Reservation should be added to extent.");
        assertEquals(selectedTable, r.getTable().getTableNumber(), "Reservation should be linked to the correct table.");
        assertEquals(testCustomer, r.getCustomer(), "Reservation must be linked to the correct customer.");
        assertThrows(IllegalArgumentException.class,
                () -> Reservation.createReservation(NOW, NOW, testCustomer, 4, selectedTable),
                "Attempting to double-book the same table/time must fail with an IllegalArgumentException.");
        assertEquals(1, extent().size(), "Extent size must remain 1 after a failed booking attempt.");
    }

    @Test
    void createReservation_throwsWhenTableExceedsCapacity() {
        String selectedTable = table1.getTableNumber();
        assertThrows(IllegalArgumentException.class,
                () -> Reservation.createReservation(NOW, NOW, testCustomer,
                        5,
                        selectedTable),
                "Booking must fail if group size exceeds table capacity.");

        assertEquals(0, extent().size(), "Extent must be empty on validation failure.");
    }
}
