package businesslogic.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.MenuEventReceiver;
import businesslogic.user.User;

import java.util.ArrayList;

public class KitchenTaskManager {
    private SummarySheet currentSheet;
    private ArrayList<KitchenTaskEventReceiver> eventReceivers;

    public SummarySheet generateSummarySheet(EventInfo event, ServiceInfo service) throws UseCaseLogicException, KitchenTaskException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!user.isChef()) { //TODO: gestire anche che lo user sia lo chef assegnato per l'evento
            throw new UseCaseLogicException();
        }

        if(!event.hasService(service)){
            throw new KitchenTaskException();
        }

        SummarySheet ss = new SummarySheet(service);
        this.setCurrentSheet(ss);
        this.notifySummarySheetCreated(ss);

        return ss;
    }

    private void notifySummarySheetCreated(SummarySheet ss) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateSummarySheetCreated(ss);
        }
    }

    private void setCurrentSheet(SummarySheet ss) {
        this.currentSheet = ss;
    }
}
