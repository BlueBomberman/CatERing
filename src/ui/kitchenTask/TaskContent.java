package ui.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.kitchenTask.Assignment;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.menu.Section;
import businesslogic.recipe.Recipe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jdk.jfr.Category;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskContent {
    KitchenTaskManagement KitchenTaskManagmentPaneController;
    @FXML
    Label eventServiceLabel;
    @FXML
    ListView<Assignment> assignmentList;

    BorderPane shiftTablePane;
    ShiftTable shiftTablePaneController;

    @FXML
    Button upButton;
    @FXML
    Button downButton;
    @FXML
    Button readyButton;
    @FXML
    Button removeButton;
    @FXML
    Button modificaButton;



    public void setKitchenTaskManagmentPaneController(KitchenTaskManagement kitchenTaskManagement) {
        this.KitchenTaskManagmentPaneController = kitchenTaskManagement;
    }

    public void initialize() {
        //il foglio riepilogativo va preso in una funzione esterna, chiamata quando si clicca il pulsante
        //come per setEventList in ServiceList
        assignmentList.setItems(FXCollections.emptyObservableList());

        assignmentList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        assignmentList.getSelectionModel().select(null);
        assignmentList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Assignment>() {
            @Override
            public void changed(ObservableValue<? extends Assignment> observableValue, Assignment oldAssignment, Assignment newAssignment) {
                if (newAssignment != null && newAssignment != oldAssignment) {

                    // enable other Assignment actions

                    removeButton.setDisable(false);
                    readyButton.setDisable(false);
                    modificaButton.setDisable(false);
                    int pos = assignmentList.getSelectionModel().getSelectedIndex();
                    upButton.setDisable(pos <= 0);
                    downButton.setDisable(pos >= (CatERing.getInstance().getTaskMgr().getCurrentSheet().getAssignments().size()-1));

                } else if (newAssignment == null) {
                    // disable Assignment actions
                    removeButton.setDisable(true);
                    readyButton.setDisable(true);
                    upButton.setDisable(true);
                    downButton.setDisable(true);
                    modificaButton.setDisable(true);
                }
            }
        });

        removeButton.setDisable(true);
        readyButton.setDisable(true);
        upButton.setDisable(true);
        downButton.setDisable(true);
        modificaButton.setDisable(true);

        //load modal fxml and controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("shift-table.fxml"));

        try {
            shiftTablePane = loader.load();
            shiftTablePaneController = loader.getController();
            shiftTablePaneController.setTaskContentPaneController(this);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void init(){
        String res = CatERing.getInstance().getTaskMgr().getCurrentSheet().serviceLabel();
        eventServiceLabel.setText(res);
        System.out.println("current sheet: " + CatERing.getInstance().getTaskMgr().getCurrentSheet());
        assignmentList.setItems(CatERing.getInstance().getTaskMgr().getCurrentSheet().getAssignments());
    }

    @FXML
    public void addButtonPressed(){
        List<Recipe> recipes = CatERing.getInstance().getRecipeManager().getRecipes();

        ChoiceDialog<Recipe> dialog = new ChoiceDialog<Recipe>(new Recipe(""), recipes);
        dialog.setTitle("Definisci Assegnamento");
        dialog.setHeaderText("Segli la ricetta");
        //dialog.setContentText("Scegli la ricetta:");

// Traditional way to get the response value.
        Optional<Recipe> result = dialog.showAndWait();
        if (result.isPresent()){
            System.out.println("Your choice: " + result.get());
            if(!result.get().getName().equals(""))
                CatERing.getInstance().getTaskMgr().createAssignment(result.get());
        }
    }

    @FXML
    public void upAssPressed() {
        this.changeAssignmentPosition(-1);
    }

    @FXML
    public void downAssPressed() {
        this.changeAssignmentPosition(+1);
    }

    private void changeAssignmentPosition(int change) {
        int newpos = assignmentList.getSelectionModel().getSelectedIndex() + change;
        Assignment ass= assignmentList.getSelectionModel().getSelectedItem();
        try {
            CatERing.getInstance().getTaskMgr().editOrder(ass, newpos);
            assignmentList.refresh();
            assignmentList.getSelectionModel().select(newpos);
        } catch (UseCaseLogicException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    public void tabelloneTurniPressed(){
        Stage stage = new Stage();

        shiftTablePaneController.setOwnStage(stage);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Tabellone turni");
        stage.setScene(new Scene(shiftTablePane, 600, 400));


        stage.showAndWait();
    }
}