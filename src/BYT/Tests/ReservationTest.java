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

import static org.junit.jupiter.api.Assertions.*;
/*
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

    // persistence

    @Test
    void testPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        List<Reservation> list = new ArrayList<>();
        list.add(new Reservation(NOW, NOW, testCustomer, 2, table1));
        testPersistence(list);
    }

    // associations

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
    void cancelReservationViaCustomer_successfulCancellation() {
        new Reservation(NOW, NOW, testCustomer, 2, table1);
        Reservation r2 = new Reservation(NOW.plusDays(1), NOW.plusDays(1), testCustomer, 4, table2);
        assertEquals(2, extent().size(), "Precondition: 2 reservations exist.");
        testCustomer.deleteReservation(r2);
        assertEquals(1, extent().size(), "Only 1 reservation should remain in extent.");
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
    void cancelReservationViaTable_successfulCancellation() {
        new Reservation(NOW, NOW, testCustomer, 2, table1);
        Reservation r2 = new Reservation(NOW.plusDays(1), NOW.plusDays(1), testCustomer, 4, table2);
        assertEquals(2, extent().size(), "Precondition: 2 reservations exist.");
        assertEquals(2, testCustomer.getReservationMap().size(), "Precondition: 2 reservations exist in Customer.");
        assertEquals(1, table2.getReservations().size(), "Precondition: 1 reservation exists in table2.");
        table2.cancelReservation(r2);
        assertEquals(1, extent().size(), "Only 1 reservation should remain in extent.");
        assertEquals(1, testCustomer.getReservationMap().size(), "Only 1 reservation should remain in Customer.");
        assertEquals(0, table2.getReservations().size(), "No reservations exist in table2.");
    }

    @Test
    void createReservation_successfulCreation() {
        String selectedTable = table1.getTableNumber();
        Reservation r = Reservation.createReservation(NOW, NOW, testCustomer, 4, selectedTable);
        assertEquals(1, extent().size(), "Reservation should be added to extent.");
        assertEquals(selectedTable, r.getTable().getTableNumber(), "Reservation should be linked to the correct table.");
        assertEquals(testCustomer, r.getCustomer(), "Reservation must be linked to the correct customer.");
        assertTrue(table1.getReservations().contains(r), "Reservation must be added to the reverse association in Table.");
        assertTrue(testCustomer.getReservationMap().containsValue(r), "Reservation must be added to the reverse association in Customer.");
        assertThrows(IllegalArgumentException.class,
                () -> Reservation.createReservation(NOW, NOW, testCustomer, 4, selectedTable),
                "Attempting to double-book the same table/time must fail with an IllegalArgumentException.");
        assertEquals(1, extent().size(), "Extent size must remain 1 after a failed booking attempt.");
    }

    @Test
    void createReservationWithNonExistentTableNumber_throws(){
        assertThrows(IllegalArgumentException.class, () -> Reservation.createReservation(NOW, NOW, testCustomer, 4, "A343254324"));
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

    @Test
    void createMoveReservation_successfulCreationAndMove() {
        Reservation r = new Reservation(NOW, NOW, testCustomer, 2, table1);
        // test cases by sher304
        assertEquals(1, extent().size(), "Reservation should be added to extent.");

        assertEquals(table1, r.getTable(), "Reservation should be linked to table1.");
        assertEquals(testCustomer, r.getCustomer(), "Reservation must be linked to the correct customer.");

        assertTrue(table1.getReservations().contains(r), "Reservation must be added to the reverse association in Table.");
        assertTrue(testCustomer.getReservationMap().containsValue(r), "Reservation must be added to the reverse association in Customer.");

        Customer testCustomer2 = new Customer("C", "D", "+48131252333", "a@u.com", 0);
        testCustomer2.addOrMoveReservation(testCustomer.generateRandomReservationNumber(), r);

        assertEquals(1, extent().size(), "There should be 1 reservation in extent.");

        assertEquals(table1, r.getTable(), "Reservation should be linked to the correct table.");
        assertEquals(testCustomer2, r.getCustomer(), "Reservation must be linked to customer 2.");
        assertFalse(testCustomer.containsReservation(r), "Reservation must be unlinked from customer 1.");

        assertSame(table1.getReservations().iterator().next(), r, "Reservation must be still present in the reverse association in Table.");
        assertTrue(testCustomer2.getReservationMap().containsValue(r), "Reservation must be added to the reverse association in customer 2.");
        assertFalse(testCustomer.getReservationMap().containsValue(r), "Reservation must be removed from the reverse association in customer 1.");
    }

    @Test
    void addDuplicateReservationValueWithDifferentNumberToCustomer_throws(){
        Reservation r = new Reservation(NOW, NOW, testCustomer, 2, table1); // already associates the reservation with Customer
        assertEquals(1, testCustomer.getReservationMap().size(), "There should be 1 reservation associated with Customer.");
        assertThrows(IllegalArgumentException.class, () -> testCustomer.addOrMoveReservation(testCustomer.generateRandomReservationNumber(), r));
    }

    @Test
    void addDuplicateReservationNumberToCustomer_throws(){
        Reservation r = new Reservation(NOW, NOW, testCustomer, 2, table1); // already associates the reservation with Customer
        assertEquals(1, testCustomer.getReservationMap().size(), "There should be 1 reservation associated with Customer.");
        String duplicateNumber = testCustomer.getReservationMap().entrySet().iterator().next().getKey();
        assertThrows(IllegalArgumentException.class, () -> testCustomer.addOrMoveReservation(duplicateNumber, r)); // number check should throw first
    }

    @Test
    void addNullReservationToCustomer_throws(){
        assertThrows(IllegalArgumentException.class, () -> testCustomer.addOrMoveReservation(testCustomer.generateRandomReservationNumber(), null));
    }

    @Test
    void addNullReservationNumToCustomer_throws(){
        Reservation r = new Reservation(NOW, NOW, testCustomer, 2, table1);
        assertThrows(IllegalArgumentException.class, () -> testCustomer.addOrMoveReservation(null, r));
    }

    @Test
    void setNullTable_throws(){
        Reservation r = new Reservation(NOW, NOW, testCustomer, 2, table1);
        assertThrows(IllegalArgumentException.class, () -> r.setTable(null));
    }
}
*/