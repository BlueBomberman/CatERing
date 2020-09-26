package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Star {

    private Main mainPaneController;

    @FXML
    void beginMenuManagement() {
        mainPaneController.startMenuManagement();
    }

    public void setParent(Main main) {
        this.mainPaneController = main;
    }

    public void initialize() {
    }

    @FXML
    public void beginKitchenTaskManagement() {
        mainPaneController.startKitchenTaskManagement();
    }
}
