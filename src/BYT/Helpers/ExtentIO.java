package BYT.Helpers;

// AI-generated, see PDF
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;

/**
 * Helper / utility class for saving and loading all "extents".
 *
 * In this project an *extent* is a static List stored in each domain
 * class that keeps track of all created instances of that class.
 *
 * This helper knows how to:
 *  - walk over a set of owner classes
 *  - read their static `extent` fields using reflection
 *  - serialize them all into one file
 *  - later read that file back and restore the lists
 */
public final class ExtentIO {

    /**
     * Private constructor + 'final' class:
     *  - 'final' on the class means nobody can subclass it (no `extends ExtentIO`).
     *  - private constructor means nobody can create an instance (`new ExtentIO()`).
     *
     * Together this is the standard pattern for a "static utility" class.
     */
    private ExtentIO() {}

    /**
     * Save *all* static extents of the given owner classes into a single file.
     *
     * @param file   path to file on disk (e.g. "extents.bin")
     * @param owners classes that own an `extent` list
     *
     * The parameter type `Class<?>... owners`:
     *  - `Class<T>` is Java's runtime representation of a class (e.g. Chef.class).
     *  - The `?` is a *wildcard type parameter*: it literally means
     *    "some unknown type, we don't care which one".
     *    So `Class<?>` = "a Class of *something*".
     *  - `...` makes it a *varargs* parameter: callers can pass any number
     *    of Class objects and they arrive as an array.
     */
    public static void saveAll(String file, Class<?>... owners) throws IOException {

        /*
         * Map<String, List<?>> snapshot = new LinkedHashMap<>();
         *
         *  - Map<K,V> maps keys of type K to values of type V.
         *  - Here:
         *        K = String    (fully qualified class name)
         *        V = List<?>   (a List of *some* element type, unknown here)
         *
         *  - The `<?>` on List<?> is a wildcard: "a list of something,
         *    but we don't know or care which class exactly".
         *
         *  - LinkedHashMap instead of HashMap:
         *      * HashMap does *not* guarantee any iteration order.
         *      * LinkedHashMap remembers the order in which keys were inserted.
         *        So when we iterate later, classes come out in the same order
         *        we put them in, which makes file layout more stable/predictable.
         */
        Map<String, List<?>> snapshot = new LinkedHashMap<>();

        // For every owner class in the varargs array...
        for (Class<?> owner : owners) {
            // Read that class's static `extent` field (see helper below).
            List<?> extent = getExtentList(owner);

            // Store it in the map under the class's fully qualified name.
            snapshot.put(owner.getName(), extent);
        }

        /*
         * Actually write the map into a binary file.
         *
         * - Path.of(file) builds a java.nio.file.Path from the String.
         * - Files.newOutputStream(path) opens a byte stream for writing.
         * - ObjectOutputStream wraps that stream and allows us to write
         *   entire Java objects (anything Serializable).
         *
         * A try-with-resources block automatically closes both streams,
         * even if an exception is thrown.
         */
        Path path = Path.of(file);
        try (OutputStream out = Files.newOutputStream(path);
             ObjectOutputStream oos = new ObjectOutputStream(out)) {

            // Serialize the entire Map<String, List<?>> object graph.
            oos.writeObject(snapshot);
        }
    }

    /**
     * Load extents from the given file and push them back into the static
     * {@code extent} lists of the given owner classes.
     *
     * IMPORTANT:
     *  - We do NOT replace the  lists themselves,
     *    because we declared them as {@code public static final List<...>}.
     *  - Instead we:
     *        1. read the serialized list contents from the file
     *        2. find each class's existing {@code extent} list via reflection
     *        3. clear it
     *        4. add all loaded elements into that list
     */
    public static void loadAll(String file, Class<?>... owners)
            throws IOException, ClassNotFoundException {

        /*
         * Build a Path object from the file name.
         *
         * Path represents a location in the filesystem in an
         * OS-independent way (Windows, Linux, etc.).
         */
        Path path = Path.of(file);

        /*
         * We'll read *one* serialized object from the file.
         * According to saveAll(...), that object should be:
         *
         *     Map<String, List<?>>
         *
         * but at this point we only know it's "some Object",
         * so we store it in a plain Object variable first.
         */
        Object obj;
        try (InputStream in = Files.newInputStream(path);
             ObjectInputStream ois = new ObjectInputStream(in)) {

            /*
             * ObjectInputStream.readObject() reads back the next object
             * from the stream. This is the reverse operation of
             * ObjectOutputStream.writeObject(...) used in saveAll(...).
             */
            obj = ois.readObject();
        }

        /*
         * Basic sanity check of the file contents.
         *
         * At runtime, generics are erased, so we can only check the
         * "raw" type (Map) but not the generic parameters
         * (String, List<?>).
         */
        if (!(obj instanceof Map)) {
            throw new IllegalArgumentException("Extent file has unexpected format: not a Map");
        }

        /*
         * We *know* (by design) that saveAll wrote a Map<String, List<?>>.
         *
         * The cast to Map<String, List<?>> is "unchecked" from the compiler's
         * point of view (because of type erasure), but logically correct here.
         *
         * The @SuppressWarnings annotation tells the compiler:
         * "Yes, this cast is unchecked. It's intentional and safe in this context."
         */
        @SuppressWarnings("unchecked")
        Map<String, List<?>> snapshot = (Map<String, List<?>>) obj;

        /*
         * Now we walk through all owner classes that should receive data.
         *
         * owners is a varargs parameter of type Class<?>...,
         * so it's just an array of Class<?> at runtime.
         */
        for (Class<?> owner : owners) {

            /*
             * Look up the list that was stored for this particular class.
             *
             * Key:   fully-qualified class name, e.g. "BYT.Classes.Person.Chef"
             * Value: List<?> containing previously saved instances of that class
             *
             * If there is no entry in the map (e.g. nothing was saved for this
             * class), snapshot.get(...) will return null.
             */
            List<?> loadedList = snapshot.get(owner.getName());

            @SuppressWarnings("unchecked")
            List<Object> currentList = (List<Object>) getExtentList(owner);

            /*
             * Remove all current elements from the list so we start
             * with an "empty" extent before adding loaded ones.
             */
            currentList.clear();

            /*
             * If the snapshot contained data for this class, copy those
             * elements into the existing list.
             *
             * If loadedList is null, that means nothing was saved for
             * this class; we simply leave the list empty.
             */
            if (loadedList != null) {
                /*
                 * List.addAll(...) takes a Collection<? extends E>.
                 *
                 * Here:
                 *  - currentList is a List<Object>
                 *  - loadedList is a List<?>
                 *
                 * The wildcard means: the elements are of some type,
                 * and since every reference type extends Object,
                 * this is safe at runtime.
                 */
                currentList.addAll(loadedList);
            }

        }
    }


    // -------------------------------------------------------------------------
    // Reflection helper: read the static `extent` field value from a class.
    // -------------------------------------------------------------------------

    /**
     * Look up a static field called {@code extent} on the given class
     * and return its value as a List<?>, or throw if the setup is invalid.
     */
    private static List<?> getExtentList(Class<?> owner) {
        try {
            // Look up the field named "extent" (regardless of its access modifier).
            Field f = owner.getDeclaredField("extent");

            // Bypass Java's normal access checks (private/protected/package-private).
            f.setAccessible(true);

            /*
             * Because we're dealing with a *static* field, we pass null as the
             * instance parameter to get(...).
             */
            Object val = f.get(null);

            /*
             * We expect the field to be some kind of List<...>.
             *
             * `instanceof List<?>` uses a wildcard again:
             * "a List of *some* element type".
             * We don't care what that element type is here,
             * only that it's a List at all.
             */
            if (!(val instanceof List<?>)) {
                throw new IllegalArgumentException(owner.getName() + ".extent must be a List");
            }

            /*
             * The return type is List<?> because we *still* don't know what the
             * element type is (Chef, Customer, ...). The wildcard in List<?>
             * expresses exactly that: "a list of some unknown element type".
             */
            return (List<?>) val;
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            // If the field doesn't exist or isn't accessible, the class is misconfigured.
            throw new IllegalArgumentException(
                    "Cannot access 'extent' in " + owner.getName(), ex);
        }
    }
}
