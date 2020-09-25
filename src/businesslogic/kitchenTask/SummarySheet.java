package businesslogic.kitchenTask;

import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuItem;
import businesslogic.menu.Section;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SummarySheet {
    private ObservableList<Assignment> assignments;
    private ServiceInfo service;
    private int id = 0;

    public SummarySheet(ServiceInfo service){
        this.service = service;
        this.assignments = FXCollections.observableArrayList();

        Menu m = service.getApprovedMenu();
        Assignment ass;

        if(m != null){
            for(Section sec: m.getSections()){
                for(MenuItem mi: sec.getItems()){
                    assignments.add(new Assignment(mi.getItemRecipe()));
                }
            }
            for(MenuItem mi: m.getFreeItems()){
                assignments.add(new Assignment(mi.getItemRecipe()));
            }
        }
    }

    public String toString() {
        String result = "";
        for(Assignment as: assignments){
            result += as.getDuty() +", ";
        }
        return "Summary Sheet for service "+ service.getName() + ": " + result;
    }

    /*STATIC METHODS FOR PERSISTENCE*/

    public static void saveNewSheet(SummarySheet ss) {
        String sheetInsert = "INSERT INTO catering.SummarySheet (id_service) VALUES (?);";
        int[] result = PersistenceManager.executeBatchUpdate(sheetInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, ss.service.getId());
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                // should be only one
                if (count == 0) {
                    ss.id = rs.getInt(1);
                }
            }
        });

        if (result[0] > 0) { // foglio effettivamente inserito
            // salva tutti gli assignments
            if (ss.assignments.size() > 0) {
                Assignment.saveAllNewAssignments(ss.id, ss.assignments);
            }
        }
    }

    public void setId(int id) {
        this.id= id;
    }

    public int getId() {
        return id;
    }
}
