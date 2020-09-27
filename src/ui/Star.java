package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class Star {

    private Main mainPaneController;

    public void setParent(Main main) {
        this.mainPaneController = main;
    }

    public void initialize() {
    }

    @FXML
    void beginMenuManagement() {
        mainPaneController.startMenuManagement();
    }

    @FXML
    public void beginKitchenTaskManagement() {
        mainPaneController.startKitchenTaskManagement();
    }
}
