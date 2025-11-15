package BYT.Classes;

import java.io.Serializable;
import java.util.*;

public class Normal extends MenuItem implements Serializable {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Normal normal = (Normal) o;
        return Objects.equals(meatTypes, normal.meatTypes);
    }
}
