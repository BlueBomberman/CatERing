package ui.kitchenTask;

import businesslogic.CatERing;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import ui.Main;
import ui.menu.MenuContent;

import java.io.IOException;

public class KitchenTaskManagement {

    @FXML
    Label userLabel;

    @FXML
    BorderPane containerPane;

    @FXML
    BorderPane serviceList;

    @FXML
    ServiceList serviceListController;

    BorderPane taskContentPane;
    TaskContent taskContentPaneController;

    Main mainPaneController;

    public void setMainPaneController(Main main) {
        mainPaneController = main;
    }

    public void initialize() {
        if (CatERing.getInstance().getUserManager().getCurrentUser() != null) {
            String uname = CatERing.getInstance().getUserManager().getCurrentUser().getUserName();
            userLabel.setText(uname);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("task-content.fxml"));
        try {
            taskContentPane = loader.load();
            taskContentPaneController = loader.getController();
            taskContentPaneController.setKitchenTaskManagmentPaneController(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //TODO: Caricare elenco eventi
    }
}
