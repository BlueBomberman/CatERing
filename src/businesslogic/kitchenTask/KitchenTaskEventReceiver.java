package businesslogic.kitchenTask;

import businesslogic.UseCaseLogicException;

public interface KitchenTaskEventReceiver {
    public void updateSummarySheetCreated(SummarySheet ss);

    void updateAssignmentAdded(SummarySheet sh,Assignment as, int pos);

    void updateAssignmentsRearrenged(SummarySheet currentSheet);

    void updateDeletedAssignment(SummarySheet currentSheet, Assignment as);

    void updateAssignmentReady(Assignment as);

    void updateAssignmentDefined(Assignment as) throws UseCaseLogicException;
}
