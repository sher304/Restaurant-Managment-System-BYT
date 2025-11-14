package BYT;

import java.util.ArrayList;
import java.util.List;

public class Table {
    private static List<Table> extent = new ArrayList<>();
    private String tableNumber; // tableNumber could be "A123" etc.
    private int maxNumberOfPeople;
    
    public static ArrayList<Table> tables = new ArrayList<Table>();
    
    public Table(String tableNumber,int maxNumberOfPeople) {
        this.tableNumber = tableNumber;
        this.maxNumberOfPeople = maxNumberOfPeople;
        extent.add(this);
    }

    @Override
    public String toString() {
        return "Table{" +
                "tableNumber='" + tableNumber + '\'' +
                ", maxNumberOfPeople=" + maxNumberOfPeople +
                '}';
    }
}
