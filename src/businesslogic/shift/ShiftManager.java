package businesslogic.shift;

import businesslogic.recipe.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShiftManager {

    public ObservableList<Shift> getShifts(int id) {
        return FXCollections.unmodifiableObservableList(Shift.getServiceShifts(id));
    }
}
