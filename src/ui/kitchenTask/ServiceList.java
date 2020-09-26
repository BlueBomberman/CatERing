package ui.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.KitchenTaskException;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuException;
import businesslogic.menu.MenuItem;
import businesslogic.menu.Section;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class ServiceList {
    private KitchenTaskManagement kitchenTaskManagmentController;

    @FXML
    ListView<EventInfo> eventList;

    @FXML
    ListView<ServiceInfo> serviceList;

    @FXML
    Button assignServiceButton;
    @FXML
    Button endButton;

    @FXML
    Pane ServicePane;
    @FXML
    GridPane centralPane;
    Pane emptyPane;
    boolean paneVisible = true;

    @FXML
    public void assegnaCompitoPressed(){
        try {
            EventInfo e = eventList.getSelectionModel().getSelectedItem();
            ServiceInfo s = serviceList.getSelectionModel().getSelectedItem();
            CatERing.getInstance().getTaskMgr().generateSummarySheet(e , s);
            kitchenTaskManagmentController.showCurrentSheet();
        } catch (UseCaseLogicException | KitchenTaskException ex) {
            ex.printStackTrace();
        }

        //
    }

    public void initialize() {
        eventList.setItems(CatERing.getInstance().getEventManager().getEventInfo());

        eventList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        eventList.getSelectionModel().select(null);
        eventList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<EventInfo>() {
            @Override
            public void changed(ObservableValue<? extends EventInfo> observableValue, EventInfo oldEvent, EventInfo newEvent) {
                if (newEvent != null && newEvent != oldEvent) {
                    if (!paneVisible) {
                        centralPane.getChildren().remove(emptyPane);
                        centralPane.add(ServicePane, 1, 0);
                        paneVisible = true;
                    }

                    serviceList.setItems(newEvent.getServices());
                    // disable Service actions
                    assignServiceButton.setDisable(true);

                } else if (newEvent == null) {
                    // disable Service actions
                    assignServiceButton.setDisable(true);
                }
            }
        });

        serviceList.setItems(FXCollections.emptyObservableList());
        serviceList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        serviceList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ServiceInfo>() {
            @Override
            public void changed(ObservableValue<? extends ServiceInfo> observableValue, ServiceInfo oldService, ServiceInfo newService) {
                if (newService != null && newService != oldService) {
                    assignServiceButton.setDisable(false);

                } else if (newService == null) {
                    assignServiceButton.setDisable(true);
                }
            }
        });
        emptyPane = new BorderPane();
        centralPane.getChildren().remove(ServicePane);
        centralPane.add(emptyPane, 1, 0);
        paneVisible = false;
    }
}
