package BYT;

// contains AI-generated elements
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public final class Extents {
    private Extents() {}
    private static final Path FILE = Path.of("extents.bin");

    public static final Class<?>[] OWNERS = {
            Person.class,
            Menu.class
    };

    public static void saveAll() throws IOException {
        ExtentIO.saveAll(FILE.toString(), OWNERS);
    }

    public static void loadAll() throws IOException, ClassNotFoundException {
        try {
            ExtentIO.loadAll(FILE.toString());
        } catch (NoSuchFileException e) {
            // first run: nothing to load
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
