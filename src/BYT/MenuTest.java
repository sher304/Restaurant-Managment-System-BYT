package BYT;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MenuTest {

    // --- helpers to access/clear the private static extent for test isolation ---
    @SuppressWarnings("unchecked")
    private static List<Menu> extent() {
        try {
            Field f = Menu.class.getDeclaredField("extent");
            f.setAccessible(true);
            return (List<Menu>) f.get(null);
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
    void constructor_addsToExtent_andSetsCurrentlyValid() {
        LocalDate today = LocalDate.now();
        Menu m = new Menu(today, today); // 1-day menu

        assertEquals(1, extent().size(), "Constructor should register in extent");
        assertSame(m, extent().get(0), "The created instance should be in extent");
        assertEquals(MenuStatus.CURRENTLYVALID, m.getMenuStatus(), "Today..today is currently valid");
    }

    @Test
    void getActiveMenus_returnsOnlyCurrentlyValid() {
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
    void delete_whenNotCreated_throws() {
        LocalDate today = LocalDate.now();
        Menu m = new Menu(today, today.plusDays(1)); // CURRENTLYVALID

        Exception ex = assertThrows(Exception.class, m::delete,
                "Deleting non-CREATED menu should throw");
        assertTrue(ex.getMessage().contains("not in status CREATED"),
                "Message should mention CREATED status");
        assertEquals(1, extent().size(), "Extent should remain unchanged on failed delete");
    }
}
