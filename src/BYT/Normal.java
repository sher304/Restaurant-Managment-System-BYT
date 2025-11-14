package BYT;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Normal {
    private static final List<Normal> extent = new ArrayList<>();
    private final List<String> meatTypes; // [0..*]

    public Normal() {
        this.meatTypes = new ArrayList<>();
        extent.add(this);
    }

    public Collection<String> getMeatTypes() {
        return Collections.unmodifiableCollection(meatTypes);
    }

    public void addMeatType(String meatType) {
        this.meatTypes.add(meatType);
    }
}
