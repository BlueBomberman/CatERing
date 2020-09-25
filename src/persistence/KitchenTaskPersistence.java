package persistence;

import businesslogic.kitchenTask.KitchenTaskEventReceiver;
import businesslogic.kitchenTask.SummarySheet;

public class KitchenTaskPersistence implements KitchenTaskEventReceiver {
    public void updateSummarySheetCreated(SummarySheet ss){
        SummarySheet.saveNewSheet(ss);
    }
}
