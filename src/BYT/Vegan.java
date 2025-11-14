package BYT;

import java.util.ArrayList;
import java.util.List;

public class Vegan {
    private static List<Vegan> extent = new ArrayList<>();
    private String certificationID;
    // "ABPL2814394243"
    // A certificationID can in theory be any combination arbitrary numbers and letters

    public Vegan(String certificationID) {
        this.certificationID = Validator.validateAttributes(certificationID);
        extent.add(this);
    }

    public String getCertificationID() {
        return certificationID;
    }

    public void setCertificationID(String certificationID) {
        this.certificationID = Validator.validateAttributes(certificationID);
    }
}
