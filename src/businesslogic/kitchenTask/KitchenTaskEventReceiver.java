package businesslogic.kitchenTask;

public interface KitchenTaskEventReceiver {
    public void updateSummarySheetCreated(SummarySheet ss);

    void updateAssignmentAdded(SummarySheet sh,Assignment as, int pos);

    void updateAssignmentsRearrenged(SummarySheet currentSheet);
}
