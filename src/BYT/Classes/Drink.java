package BYT.Classes;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Drink extends MenuItem implements Serializable {
    private static final List<Drink> extent = new ArrayList<>();
    private long drinkVolume;

    public Drink(String name, String description, long price, long drinkVolume) {
        super(name, description, price);
        this.drinkVolume = Validator.validateNonZeroPhysicalAttribute(drinkVolume);
        extent.add(this);
    }

    public long getDrinkVolume() {
        return drinkVolume;
    }

    public void setDrinkVolume(long drinkVolume) {
        this.drinkVolume = Validator.validateNonZeroPhysicalAttribute(drinkVolume);
    }

    @Override
    public String toString() {
        return "Drink{" +
                "drinkVolume=" + drinkVolume +
                '}';
    }
}
