package BYT.Classes.Table;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Table implements Serializable {
    private static List<Table> extent = new ArrayList<>();
    private String tableNumber; // tableNumber could be "A123" etc.
    private int maxNumberOfPeople;

    public Table(String tableNumber, int maxNumberOfPeople) {
        this.tableNumber = Validator.validateAttributes(tableNumber);
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
        this.tableNumber = Validator.validateAttributes(tableNumber);
    }

    public int getMaxNumberOfPeople() {
        return maxNumberOfPeople;
    }

    public void setMaxNumberOfPeople(int maxNumberOfPeople) {
        this.maxNumberOfPeople = Validator.negativeNumberEntered(maxNumberOfPeople);
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableNumber='" + tableNumber + '\'' +
                ", maxNumberOfPeople=" + maxNumberOfPeople +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Table table = (Table) o;
        return maxNumberOfPeople == table.maxNumberOfPeople && Objects.equals(tableNumber, table.tableNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableNumber, maxNumberOfPeople);
    }
}
