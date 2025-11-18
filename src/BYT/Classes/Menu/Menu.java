package BYT.Classes.Menu;

import BYT.Helpers.Validator;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private LocalDate releaseDate;
    private LocalDate endDate;

    public Menu(LocalDate releaseDate, LocalDate endDate) throws IllegalArgumentException {
        Validator.validateMenuDate(releaseDate, endDate);

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

    public void setReleaseDate(LocalDate releaseDate) {
        Validator.validateMenuDate(releaseDate, this.endDate);

        this.releaseDate = releaseDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        Validator.validateMenuDate(this.releaseDate, endDate);

        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "releaseDate=" + releaseDate +
                ", endDate=" + endDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Menu menu = (Menu) o;
        return Objects.equals(releaseDate, menu.releaseDate) && Objects.equals(endDate, menu.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(releaseDate, endDate);
    }
}
