package ui.kitchenTask;

import businesslogic.CatERing;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.Assignment;
import businesslogic.shift.Shift;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShiftTable {
    TaskContent TaskContentPaneController;

    @FXML
    ListView<Shift> shiftList;

    @FXML
    ListView<Assignment> assignmentList;

    @FXML
    Pane AssignmentPane;
    @FXML
    GridPane nicePane;
    Pane emptyPane;
    boolean paneVisible = true;

    private Stage myStage;

    public void setTaskContentPaneController(TaskContent taskContent) {
        this.TaskContentPaneController = taskContent;
    }

    public void initialize() {

        shiftList.setItems(FXCollections.emptyObservableList());

        shiftList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        shiftList.getSelectionModel().select(null);
        shiftList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Shift>() {
            @Override
            public void changed(ObservableValue<? extends Shift> observableValue, Shift oldShift, Shift newShift) {
                if (newShift != null && newShift != oldShift) {
                    if (!paneVisible) {
                        nicePane.getChildren().remove(emptyPane);
                        nicePane.add(AssignmentPane, 1, 0);
                        paneVisible = true;
                    }

                    assignmentList.setItems(CatERing.getInstance().getTaskMgr().getShiftAssignments(newShift.getId()));
                    // disable Service actions
                }
            }
        });

        assignmentList.setItems(FXCollections.emptyObservableList());
        assignmentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        emptyPane = new BorderPane();
        nicePane.getChildren().remove(AssignmentPane);
        nicePane.add(emptyPane, 1, 0);
        paneVisible = false;
    }

    public void init(){
        int ser_id = CatERing.getInstance().getTaskMgr().getCurrentSheet().getService().getId();
        shiftList.setItems(CatERing.getInstance().getShiftManager().getShifts(ser_id));
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
