package BYT.Tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import BYT.Helpers.Extents;
import BYT.Classes.Menu.Menu;
import BYT.Classes.Menu.MenuStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MenuTest extends TestBase<Menu> {

    protected MenuTest() {
        super(Menu.class);
    }

    @BeforeEach
    void setUp() {
        // Before each test, we assume a clear extent
        clearExtentInMemoryList();
    }

    @Test
    void testMenuPersistence_SavingAndLoading() throws IOException, ClassNotFoundException {
        // extent cleared at setup
        LocalDate today = LocalDate.now();
        Menu m = new Menu(today, today.plusDays(3));
        assertEquals(1, extent().size(), "Extent should contain 1 item after creating Menu object");

        saveExtentsToFile();
        clearExtentInMemoryList();
        assertEquals(0, extent().size(), "Extent should be empty after clearing");
        loadExtentsFromFile();

        List<Menu> activeMenus = Menu.getActiveMenus();
        assertEquals(1, activeMenus.size(), "Extent should contain 1 item after loading from file");

        Menu ml = activeMenus.getFirst();

        assertEquals(today, ml.getReleaseDate(), "Loaded Menu releaseDate should match");
        assertEquals(today.plusDays(3), ml.getEndDate(), "Loaded Menu endDate should match");
    }

    @Test
    void testDateAttributes() {
        LocalDate today = LocalDate.now();
        Menu m = new Menu(today.plusDays(123), today.plusDays(456));

        assertEquals(today.plusDays(123), m.getReleaseDate());
        assertEquals(today.plusDays(456), m.getEndDate());
    }

    @Test
    void constructor_addsToExtent_andSetsCurrentlyValid_WhenReleaseDateIsToday() {
        LocalDate today = LocalDate.now();
        Menu m = new Menu(today, today); // 1-day menu

        assertEquals(1, extent().size(), "Constructor should register in extent");
        assertSame(m, extent().get(0), "The created instance should be in extent");
        assertEquals(MenuStatus.CURRENTLYVALID, m.getMenuStatus(), "Today..today is currently valid");
    }

    @Test
    void releaseDateInTheFuture_returnsCreated() {
        LocalDate today = LocalDate.now();
        Menu m = new Menu(today.plusDays(1), today.plusDays(1));

        assertEquals(MenuStatus.CREATED, m.getMenuStatus(), "Menu with release date in the future should return state CREATED");
    }

    @Test
    void getActiveMenus_returnsOnlyCurrentlyValidMenus() {
        LocalDate today = LocalDate.now();

        Menu valid = new Menu(today, today.plusDays(1));     // CURRENTLYVALID
        Menu created = new Menu(today.plusDays(5), today.plusDays(6)); // CREATED

        List<Menu> active = Menu.getActiveMenus();
        assertTrue(active.contains(valid), "Active should include currently valid menus");
        assertFalse(active.contains(created), "Active should not include CREATED menus");
        assertEquals(1, active.size(), "Exactly one active menu expected");
    }

    @Test
    void constructor_throwsWhenReleaseBeforeToday() {
        LocalDate today = LocalDate.now();
        assertThrows(IllegalArgumentException.class,
                () -> new Menu(today.minusDays(1), today),
                "releaseDate before today should throw");
    }

    @Test
    void constructor_throwsWhenEndBeforeToday() {
        LocalDate today = LocalDate.now();
        assertThrows(IllegalArgumentException.class,
                () -> new Menu(today, today.minusDays(1)),
                "endDate before today should throw");
    }

    @Test
    void constructor_throwsWhenEndBeforeRelease() {
        LocalDate today = LocalDate.now();
        assertThrows(IllegalArgumentException.class,
                () -> new Menu(today.plusDays(2), today.plusDays(1)),
                "endDate before releaseDate should throw");
    }

    @Test
    void delete_whenCreated_removesFromExtent() throws Exception {
        LocalDate today = LocalDate.now();
        Menu m = new Menu(today.plusDays(1), today.plusDays(2)); // CREATED

        assertEquals(1, extent().size(), "Precondition: extent has the new menu");
        m.delete();
        assertEquals(0, extent().size(), "Menu in CREATED status should be deletable");
    }

    @Test
    void delete_whenCurrentlyValid_throws() {
        LocalDate today = LocalDate.now();
        Menu m = new Menu(today, today.plusDays(1)); // CURRENTLYVALID

        Exception ex = assertThrows(Exception.class, m::delete,
                "Deleting CURRENTLYVALID menu should throw");
        assertTrue(ex.getMessage().contains("not in status CREATED"),
                "Message should mention CREATED status");
        assertEquals(1, extent().size(), "Extent should remain unchanged on failed delete");
    }
}
