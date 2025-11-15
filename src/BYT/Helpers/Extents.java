package BYT.Helpers;

// contains AI-generated elements
import BYT.Classes.Menu.Menu;
import BYT.Classes.MenuItem.*;
import BYT.Classes.Order.Order;
import BYT.Classes.Person.Employee;
import BYT.Classes.Person.Person;
import BYT.Classes.Table.Reservation;
import BYT.Classes.Table.Table;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public final class Extents {
    private Extents() {}
    private static final Path FILE = Path.of("extents.bin");

    public static final Class<?>[] OWNERS = {
            Chef.class,
            Customer.class,
            Drink.class,
            Employee.class,
            Food.class,
            Ingredient.class,
            Menu.class,
            MenuItem.class,
            Normal.class,
            Order.class,
            Person.class,
            Reservation.class,
            Table.class,
            Vegan.class,
            Waiter.class,
    };

    public static void saveAll() throws IOException {
        ExtentIO.saveAll(FILE.toString(), OWNERS);
    }

    public static void loadAll() throws IOException, ClassNotFoundException {
        try {
            ExtentIO.loadAll(FILE.toString());
        } catch (NoSuchFileException e) {
            // first run: nothing to load
            System.out.println("WARNING: extent file not found. Extents will be empty.");
        } catch (ClassNotFoundException e) {
            // Unknown class in the file
            throw e;
        } catch (IOException e) {
            // I/O problem—rethrow after optional logging
            throw e;
        } catch (RuntimeException e) {
            // If ExtentIO validates and throws IllegalArgumentException, etc.
            // Decide: either rethrow or clear. Safer to rethrow in dev.
            throw e;
        }
    }
}
