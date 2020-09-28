package businesslogic.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.MenuEventReceiver;
import businesslogic.menu.Section;
import businesslogic.recipe.KitchenDuty;
import businesslogic.shift.Shift;
import businesslogic.shift.ShiftException;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jdk.jfr.Category;
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

        if (!user.isChef() || user.getId() != (event.getChef().getId())) {
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

    //dsd2
    public Assignment createAssignment(KitchenDuty duty){
        Assignment as = getCurrentSheet().addAssignment(duty);
        this.notifyAssignmentAdded(as);
        return as;
    }
    //dsd3
    public void editOrder(Assignment as, int position) throws UseCaseLogicException {
        if (currentSheet == null || currentSheet.getAssignmentPosition(as) < 0) throw new UseCaseLogicException();
        if (position < 0 || position >= currentSheet.getAssignementsCount()) throw new IllegalArgumentException();
        this.currentSheet.changeOrder(as, position);

        this.notifyAssignmentsRearranged(currentSheet);
    }

    //dsd5d
    public void deleteAssignment(Assignment as) {
        currentSheet.deleteAssignment(as);
        this.notifyDeletedAssignment(currentSheet,as);
    }

    //dsd5b TODO: magari cambiamo e mettiamo una checkbox in modo da poter rimettere ready = false
    public void setAssignmentReady(Assignment as) {
        as.setReady(true);
        this.notifyAssignmentReady(as);
    }

    //DSD 5
    public void defineAssignment(Assignment a, Shift s, User cook, String estTime, String quantity) throws UseCaseLogicException, ShiftException {
        if(CatERing.getInstance().getTaskMgr().getCurrentSheet() == null ){
            throw new UseCaseLogicException();
        }
        if(s.isClosed()){
            throw new ShiftException();
        }
        Assignment as = currentSheet.defineAssignment( a,  s,  cook,  estTime,  quantity);
        this.notifyAssignmentDefined(as);
    }




    private SummarySheet summarySheetExists(ServiceInfo service) {
        final int[] i = {0};
        final String[] query = {"SELECT * FROM catering.summarysheet WHERE id_service = " + service.getId()};
        PersistenceManager.executeQuery(query[0], new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                 i[0] =(rs.getInt("id"));
            }
        });
        if(i[0] == 0)
            return null;
        else{
            return new SummarySheet(i[0],service);
        }
    }

    private void notifySummarySheetCreated(SummarySheet ss) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateSummarySheetCreated(ss);
        }
    }

    private void notifyAssignmentAdded(Assignment as) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateAssignmentAdded(currentSheet,as,currentSheet.getAssignments().size());
        }
    }

    private void notifyAssignmentsRearranged(SummarySheet currentSheet) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateAssignmentsRearrenged(currentSheet);
        }
    }

    private void notifyDeletedAssignment(SummarySheet currentSheet, Assignment as) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateDeletedAssignment(currentSheet,as);
        }
    }

    private void notifyAssignmentReady(Assignment as) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateAssignmentReady(as);
        }
    }

    private void notifyAssignmentDefined(Assignment as) {
        for (KitchenTaskEventReceiver er : this.eventReceivers) {
            er.updateAssignmentDefined(as);
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

    public ObservableList<Assignment> getShiftAssignments(int shift_id) {
        return FXCollections.unmodifiableObservableList(Assignment.getShiftAssignments(shift_id));
    }
}
