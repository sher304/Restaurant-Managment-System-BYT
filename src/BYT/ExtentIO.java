package BYT;

// AI-generated, see PDF
import java.io.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.util.*;

public final class ExtentIO {
    private ExtentIO() {}

    /** Save all provided classes' static 'extent' lists into one file. */
    public static void saveAll(String file, Class<?>... owners) throws IOException {
        Map<String, List<?>> snapshot = new LinkedHashMap<>();
        for (Class<?> c : owners) {
            List<?> extent = getExtentList(c);
            snapshot.put(c.getName(), new ArrayList<>(extent)); // defensive copy
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(Files.newOutputStream(Path.of(file))))) {
            oos.writeObject(snapshot);
        }
    }

    /** Load all extents from file back into the owning classes' static 'extent' lists. */
    public static void loadAll(String file) throws IOException, ClassNotFoundException {
        Map<String, List<?>> snapshot;
        try (ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(Files.newInputStream(Path.of(file))))) {
            @SuppressWarnings("unchecked")
            Map<String, List<?>> read = (Map<String, List<?>>) ois.readObject();
            snapshot = read;
        }
        for (Map.Entry<String, List<?>> e : snapshot.entrySet()) {
            Class<?> cls = Class.forName(e.getKey());
            List<Object> target = castToObjectList(getExtentList(cls));
            target.clear();              // keep the same List instance (it's 'final' in classes)
            target.addAll(e.getValue()); // restore items
        }
    }

    /** Clear a specific class's extent (optional helper). */
    public static void clearExtent(Class<?> owner) {
        castToObjectList(getExtentList(owner)).clear();
    }

    // ---- internals ----
    private static List<?> getExtentList(Class<?> owner) {
        try {
            Field f = owner.getDeclaredField("extent");
            if (!Modifier.isStatic(f.getModifiers()))
                throw new IllegalArgumentException(owner.getName() + ".extent must be static");
            f.setAccessible(true);
            Object val = f.get(null);
            if (!(val instanceof List<?>))
                throw new IllegalArgumentException(owner.getName() + ".extent must be a List");
            return (List<?>) val;
        } catch (NoSuchFieldException | IllegalAccessException ex) {
            throw new IllegalArgumentException("Cannot access 'extent' in " + owner.getName(), ex);
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Object> castToObjectList(List<?> list) {
        return (List<Object>) list;
    }
}
