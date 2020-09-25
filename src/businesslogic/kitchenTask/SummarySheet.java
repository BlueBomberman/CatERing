package businesslogic.kitchenTask;

import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuItem;
import businesslogic.menu.Section;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SummarySheet {
    private ObservableList<Assignement> assignements;
    private ServiceInfo service;

    public SummarySheet(ServiceInfo service){
        this.service = service;
        this.assignements = FXCollections.observableArrayList();

        Menu m = service.getApprovedMenu();
        Assignement ass;

        if(m != null){
            for(Section sec: m.getSections()){
                for(MenuItem mi: sec.getItems()){
                    assignements.add(new Assignement(mi.getItemRecipe()));
                }
            }
            for(MenuItem mi: m.getFreeItems()){
                assignements.add(new Assignement(mi.getItemRecipe()));
            }
        }
    }
}
