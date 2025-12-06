package BYT.Classes.MenuItem;

import BYT.Classes.Restaurant.Menu;
import BYT.Classes.Restaurant.MenuItem;
import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Drink extends MenuItem implements Serializable {
    private static final List<Drink> extent = new ArrayList<>();
    private long drinkVolume;

    public Drink(String name, String description, long price, long drinkVolume, Menu menu) {
        super(name, description, price, menu);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Drink drink = (Drink) o;
        return drinkVolume == drink.drinkVolume;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(drinkVolume);
    }
}
