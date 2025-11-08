package BYT;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Menu implements Serializable {
    private static final List<Menu> extent = new ArrayList<>();

    public static List<Menu> getActiveMenus(){
        List<Menu> returnList = new ArrayList<>();
        for(Menu menu : extent){
            if(menu.getMenuStatus() == MenuStatus.CURRENTLYVALID){
                returnList.add(menu);
            }
        }
        return returnList;
    }

    // should this be a separate method or just use the constructor?
    //public static void createNewMenu(Menu newMenu){
        //extent.add(newMenu);
    //}

    // The Menu is VALID from [releaseDate, endDate] - bounds included:
    // When the date is equal releaseDate or endDate, the menu is VALID.
    private final LocalDate releaseDate;
    private final LocalDate endDate;

    public Menu(LocalDate releaseDate, LocalDate endDate) throws IllegalArgumentException {
        LocalDate today = LocalDate.now();

        if(today.isAfter(releaseDate))
            throw new IllegalArgumentException("The releaseDate cannot be *before* today. It must be at the earliest equal to today.");

        if (today.isAfter(endDate))
            throw new IllegalArgumentException("The endDate cannot be *before* today. It must be at the earliest equal to today.");

        if(endDate.isBefore(releaseDate))
            throw new IllegalArgumentException("The endDate cannot be before the releaseDate. The endDate must be equal (1-day menu) or after the releaseDate.");

        this.releaseDate = releaseDate;
        this.endDate = endDate;

        extent.add(this);
    }

    //public List<MenuItem> getMenuItemList(){

    //}

    //public void createMenuItem(MenuItem newMenuItem){

    //}

    public void delete() throws Exception {
        if(this.getMenuStatus() == MenuStatus.CREATED){
            Menu.extent.remove(this);
        }else{
            throw new Exception("This Menu is not in status CREATED and cannot be deleted.");
        }
    }

    // /Status derived attribute (converted to method)
    public MenuStatus getMenuStatus(){
        LocalDate today = LocalDate.now();

        if(today.isAfter(endDate)){
            return MenuStatus.ENDED;
        }else if(today.isAfter(releaseDate) || today.isEqual(releaseDate)){
            return MenuStatus.CURRENTLYVALID;
        }else{
            return MenuStatus.CREATED;
        }
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }
}
