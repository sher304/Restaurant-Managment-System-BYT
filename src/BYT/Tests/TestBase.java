package BYT.Tests;

import BYT.Classes.Normal;
import BYT.Helpers.Extents;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

// intentional non-public class
// extent should not be accessible outside of the classes as per the requirements
class TestBase<T> {

    private final Class<T> entityClass;

    protected TestBase(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @SuppressWarnings("unchecked")
    protected List<T> extent() {
        try {
            Field f = entityClass.getDeclaredField("extent");
            f.setAccessible(true);
            return (List<T>) f.get(null);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    protected void clearExtentInMemoryList() {
        extent().clear();
    }

    protected void saveExtentsToFile() throws IOException {
        Extents.saveAll();
    }

    protected void loadExtentsFromFile() throws IOException, ClassNotFoundException {
        Extents.loadAll();
    }

    protected void testPersistence(List<T> createdObjects) throws IOException, ClassNotFoundException {
        assertEquals(createdObjects.size(), extent().size(), "Extent should contain " + createdObjects.size() + " item(s) after creating Menu object");

        saveExtentsToFile();
        clearExtentInMemoryList();
        assertEquals(0, extent().size(), "Extent should be empty after clearing");
        loadExtentsFromFile();

        List<T> loadedClasses = extent();
        assertEquals(createdObjects.size(), loadedClasses.size(), "Extent should contain " + createdObjects.size() + " item(s) after loading from file");

        if (createdObjects.size() == loadedClasses.size())
            for (int i = 0; i < loadedClasses.size(); i++) {
                assertEquals(createdObjects.get(i), loadedClasses.get(i), "Classes should be equal after loading");
            }
    }
}
