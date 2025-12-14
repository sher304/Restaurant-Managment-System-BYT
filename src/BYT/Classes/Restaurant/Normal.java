package BYT.Classes.Restaurant;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.*;

public final class Normal implements Serializable {
    private static final List<Normal> extent = new ArrayList<>();

    public static List<Normal> getExtent(){
        return Collections.unmodifiableList(extent);
    }

    private MenuItem menuItem;

    private final List<String> meatTypes; // [0..*]

    Normal(MenuItem menuItem) {
        meatTypes = new ArrayList<>();
        this.setMenuItem(menuItem);
        extent.add(this);
    }

    public void delete(){
        extent.remove(this);
        menuItem.delete();
        this.menuItem = null;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    private void setMenuItem(MenuItem menuItem) {
        Validator.validateNullObjects(menuItem);
        this.menuItem = menuItem;
        //menuItem.setNormalPart(this);
    }

    public List<String> getMeatTypes() {
        return Collections.unmodifiableList(meatTypes);
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

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), meatTypes);
    }
}
