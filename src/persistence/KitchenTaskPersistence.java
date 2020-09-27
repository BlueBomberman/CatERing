package persistence;

import businesslogic.kitchenTask.Assignment;
import businesslogic.kitchenTask.KitchenTaskEventReceiver;
import businesslogic.kitchenTask.SummarySheet;

public class KitchenTaskPersistence implements KitchenTaskEventReceiver {
    public void updateSummarySheetCreated(SummarySheet ss){
        SummarySheet.saveNewSheet(ss);
    }

    @Override
    public void updateAssignmentAdded(SummarySheet sh, Assignment as, int pos) {
        Assignment.saveNewAssignment(sh,as,pos);
    }

    @Override
    public void updateAssignmentsRearrenged(SummarySheet ss) {
        SummarySheet.saveAssignmentsOrder(ss);
    }
}
