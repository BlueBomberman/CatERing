package businesslogic.shift;

import businesslogic.recipe.Recipe;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ShiftManager {

    public ObservableList<Shift> getShifts(int id) {
        return FXCollections.unmodifiableObservableList(Shift.getServiceShifts(id));
    }

    public ObservableList<User> getCooks(int id) {
        return FXCollections.unmodifiableObservableList(Shift.getShiftCooks(id));
    }

    public ObservableList<Shift> getOpenShifts(int id) {
        return FXCollections.unmodifiableObservableList(Shift.getOpenShifts(id));
    }
}
