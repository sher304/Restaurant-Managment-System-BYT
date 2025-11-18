package BYT.Classes.MenuItem;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Vegan extends MenuItem implements Serializable {
    private static List<Vegan> extent = new ArrayList<>();
    private String certificationID;
    // "ABPL2814394243"
    // A certificationID can in theory be any combination arbitrary numbers and letters

    public Vegan(String name, String description, long price, String certificationID) {
        super(name, description, price);
        this.certificationID = Validator.validateAttributes(certificationID);
        extent.add(this);
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
        if (!super.equals(o)) return false;
        Vegan vegan = (Vegan) o;
        return Objects.equals(certificationID, vegan.certificationID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), certificationID);
    }
}
