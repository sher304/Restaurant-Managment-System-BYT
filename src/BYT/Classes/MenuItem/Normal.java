package BYT.Classes.MenuItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Normal extends MenuItem {
    private static final List<Normal> extent = new ArrayList<>();
    private final List<String> meatTypes; // [0..*]

    public Normal(String name, String description, long price) {
        super(name, description, price);
        meatTypes = new ArrayList<>();
        extent.add(this);
    }

    public Collection<String> getMeatTypes() {
        return Collections.unmodifiableCollection(meatTypes);
    }

    public void addMeatType(String meatType) {
        this.meatTypes.add(meatType);
    }
}
