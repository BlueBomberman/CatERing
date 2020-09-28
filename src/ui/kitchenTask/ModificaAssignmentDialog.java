package ui.kitchenTask;

import businesslogic.CatERing;
import businesslogic.recipe.Recipe;
import businesslogic.shift.Shift;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.Optional;

public class ModificaAssignmentDialog {
    Stage myStage;

    @FXML
    ComboBox<Shift> shiftCombo;

    @FXML
    ComboBox<User> cookCombo;

    @FXML
    TextField quantityField;

    @FXML
    TextField estTimeField;

    @FXML
    Button saveButton;

    // Results
    private Shift selectedShift;
    private User selectedCook;
    private String insEstTime;
    private String insQuantity;
    private boolean confirmed;

    public void initialize() {
        shiftCombo.setItems(FXCollections.emptyObservableList());
        cookCombo.setItems(FXCollections.emptyObservableList());
        confirmed = false;
    }

    public void init(){
        int ser_id = CatERing.getInstance().getTaskMgr().getCurrentSheet().getService().getId();
        shiftCombo.setItems(CatERing.getInstance().getShiftManager().getShifts(ser_id));
    }

    @FXML
    public void shiftComboSelection() {
        Shift sel = shiftCombo.getValue();
        cookCombo.setDisable(sel == null);
        quantityField.setDisable(sel == null);
        estTimeField.setDisable(sel == null);
        saveButton.setDisable(sel == null);

        if(sel!= null)
            cookCombo.setItems(CatERing.getInstance().getShiftManager().getCooks(sel.getId()));
    }

    @FXML
    public void saveButtonPressed() {
        confirmed = true;
        selectedShift = shiftCombo.getValue();
        selectedCook = cookCombo.getValue();
        insEstTime = quantityField.getText();
        insQuantity = estTimeField.getText();
        myStage.close();
    }

    @FXML
    public void annullaButtonPressed() {
        confirmed = false;
        myStage.close();
    }

    public void setOwnStage(Stage stage) {
        if(myStage == null)
            myStage = stage;
    }

    //getter
    public Shift getSelectedShift() {
        if (!confirmed) selectedShift = null;
        return selectedShift;
    }

    public User getSelectedCook() {
        if (!confirmed) selectedCook = null;
        return selectedCook;
    }

    public String getInsEstTime() {
        if (!confirmed || insEstTime.equals("")) insEstTime = null;
        return insEstTime;
    }

    public String getInsQuantity() {
        if (!confirmed || insQuantity.equals("")) insQuantity = null;
        return insQuantity;
    }
}
