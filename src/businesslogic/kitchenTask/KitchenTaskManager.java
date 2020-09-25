package businesslogic.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.MenuEventReceiver;
import businesslogic.user.User;
import persistence.KitchenTaskPersistence;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class KitchenTaskManager {
    private SummarySheet currentSheet;
    private ArrayList<KitchenTaskEventReceiver> eventReceivers;

    public KitchenTaskManager() {
        this.eventReceivers = new ArrayList<>();
    }

    //dsd1
    public SummarySheet generateSummarySheet(EventInfo event, ServiceInfo service) throws UseCaseLogicException, KitchenTaskException {
        User user = CatERing.getInstance().getUserManager().getCurrentUser();

        if (!user.isChef() || !user.equals(event.getChef())) {
            throw new UseCaseLogicException();
        }

        if(!event.hasService(service)){
            throw new KitchenTaskException();
        }

        //check if the sheet is already created for the service
        SummarySheet ss = summarySheetExists(service);
        if(ss == null){
            ss = new SummarySheet(service);
            this.notifySummarySheetCreated(ss);
        }

        this.setCurrentSheet(ss);

        return ss;
    }

    private SummarySheet summarySheetExists(ServiceInfo service) {
        SummarySheet load = new SummarySheet(service);
        String query = "SELECT * FROM catering.summarysheet WHERE id_service = "+service.getId();
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                load.setId(rs.getInt("id"));
            }
        });
        if(load.getId() == 0)
            return null;
        else
            return load;
    }

    private void notifySummarySheetCreated(SummarySheet ss) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateSummarySheetCreated(ss);
        }
    }

    private void setCurrentSheet(SummarySheet ss) {
        this.currentSheet = ss;
    }

    public void addEventReceiver(KitchenTaskPersistence rec) {
        this.eventReceivers.add(rec);
    }

    public SummarySheet getCurrentSheet() {
        return currentSheet;
    }
}
