package BYT.Classes.MenuItem;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Food extends MenuItem implements Serializable {
    private static final List<Food> extent = new ArrayList<>();
    private long foodWeight;

    public Food(String name, String description, long price, long foodWeight) {
        super(name, description, price);
        this.foodWeight = Validator.validateNonZeroPhysicalAttribute(foodWeight);;
        extent.add(this);
    }

    public long getFoodWeight() {
        return foodWeight;
    }

    public void setFoodWeight(long foodWeight) {
        this.foodWeight = Validator.validateNonZeroPhysicalAttribute(foodWeight);
    }

    @Override
    public String toString() {
        return "Food{" +
                "foodWeight=" + foodWeight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Food food = (Food) o;
        return foodWeight == food.foodWeight;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(foodWeight);
    }
}
