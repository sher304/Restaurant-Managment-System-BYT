package BYT.Helpers;

// contains AI-generated elements
import BYT.Classes.*;

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
            //Employee.class, // abstract class, no extent
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
            Waiter.class
    };

    public static void saveAll() throws IOException {
        ExtentIO.saveAll(FILE.toString(), OWNERS);
    }

    public static void loadAll() throws IOException, ClassNotFoundException {
        try {
            ExtentIO.loadAll(FILE.toString());
        } catch (NoSuchFileException e) {
            System.err.println("WARNING: extent file not found. Extents will be empty.");
        } catch (ClassNotFoundException e) {
            System.err.println("WARNING: unknown class in extent file!");
            throw e;
        } catch (IOException | RuntimeException e) {
            throw e;
        }
    }
}
