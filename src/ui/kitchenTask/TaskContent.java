package ui.kitchenTask;

import businesslogic.CatERing;
import businesslogic.kitchenTask.SummarySheet;

public class TaskContent {
    KitchenTaskManagement KitchenTaskManagmentPaneController;

    public void setKitchenTaskManagmentPaneController(KitchenTaskManagement kitchenTaskManagement) {
        this.KitchenTaskManagmentPaneController = kitchenTaskManagement;
    }

    public void initialize() {
        if(CatERing.getInstance().getTaskMgr().getCurrentSheet() == null){
            //TODO:TUTTO
        }
        else{
            SummarySheet ss = CatERing.getInstance().getTaskMgr().getCurrentSheet();
        }
    }
}
