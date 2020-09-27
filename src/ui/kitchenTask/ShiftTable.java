package ui.kitchenTask;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ShiftTable {
    TaskContent TaskContentPaneController;

    private Stage myStage;

    public void setTaskContentPaneController(TaskContent taskContent) {
        this.TaskContentPaneController = taskContent;
    }

    @FXML
    public void indietroPressed() {
        myStage.close();
    }

    public void setOwnStage(Stage stage) {
        if(myStage == null)
            myStage = stage;
    }
}
