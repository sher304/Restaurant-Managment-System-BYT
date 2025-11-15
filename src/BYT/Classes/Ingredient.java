package BYT.Classes;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ingredient implements Serializable {
    private static final List<Ingredient> extent = new ArrayList<>();
    private String name;

    public Ingredient(String name) {
        this.name = Validator.validateAttributes(name);
        extent.add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Validator.validateAttributes(name);
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "name='" + name + '\'' +
                '}';
    }
}
