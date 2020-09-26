package ui.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuException;
import businesslogic.menu.Section;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

public class ServiceList {
    private KitchenTaskManagement kitchenTaskManagmentController;

    @FXML
    ListView<EventInfo> eventList;

    @FXML
    ListView<ServiceInfo> serviceList;

    @FXML
    public void assegnaCompitoPressed(){
        /*try {
            Service s = menuList.getSelectionModel().getSelectedItem();
            CatERing.getInstance().getMenuManager().chooseMenu(m);
            menuManagementController.showCurrentMenu();
        } catch (UseCaseLogicException | MenuException ex) {
            ex.printStackTrace();
        }*/

        //CatERing.getInstance().getTaskMgr().generateSummarySheet()
    }

    public void initialize() {
        eventList.setItems(CatERing.getInstance().getEventManager().getEventInfo());
    }
}
