package BYT.Classes;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table implements Serializable {
    private static List<Table> extent = new ArrayList<>();
    private String tableNumber; // tableNumber could be "A123" etc.
    private int maxNumberOfPeople;

    public Table(String tableNumber, int maxNumberOfPeople) {
        this.tableNumber = tableNumber;
        this.maxNumberOfPeople = Validator.negativeNumberEntered(maxNumberOfPeople);
        extent.add(this);
    }

    public static List<Table> getExtent() {
        return Collections.unmodifiableList(extent);
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public int getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(int maxNumberOfPeople) {
        this.maxNumberOfPeople = maxNumberOfPeople;
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableNumber='" + tableNumber + '\'' +
                ", maxNumberOfPeople=" + maxNumberOfPeople +
                '}';
    }
}
