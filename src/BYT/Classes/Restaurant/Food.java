package BYT.Classes.Restaurant;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Food extends MenuItem implements Serializable {
    private static final List<Food> extent = new ArrayList<>();

    public static List<Food> getFoodExtent(){
        return Collections.unmodifiableList(extent);
    }

    private long foodWeight;

    public Food(String name, String description, long price, long foodWeight, Menu menu, DietInheritanceTypes dietInheritanceTypes) {
        super(name, description, price, menu, dietInheritanceTypes);
        this.foodWeight = Validator.validateNonZeroPhysicalAttribute(foodWeight);;
        extent.add(this);
    }

    protected void deleteSubclass(){
        extent.remove(this);
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
