package BYT.Classes.Restaurant;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Vegan implements Serializable {
    private static List<Vegan> extent = new ArrayList<>();

    public static List<Vegan> getExtent(){
        return Collections.unmodifiableList(extent);
    }

    private MenuItem menuItem;

    private String certificationID;
    // "ABPL2814394243"
    // A certificationID can in theory be any combination arbitrary numbers and letters

    Vegan(MenuItem menuItem) {
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
        //menuItem.setVeganPart(this);
    }

    public String getCertificationID() {
        return certificationID;
    }

    public void setCertificationID(String certificationID) {
        this.certificationID = Validator.validateAttributes(certificationID);
    }

    @Override
    public String toString() {
        return "Vegan{" +
                "certificationID='" + certificationID + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vegan vegan = (Vegan) o;
        return Objects.equals(certificationID, vegan.certificationID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), certificationID);
    }
}
