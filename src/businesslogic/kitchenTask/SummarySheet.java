package businesslogic.kitchenTask;

import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuItem;
import businesslogic.menu.Section;
import businesslogic.recipe.KitchenDuty;
import businesslogic.recipe.Recipe;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;
import persistence.ResultHandler;

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

    public SummarySheet(int id_sum,ServiceInfo service){
        this.service = service;
        this.assignments = FXCollections.observableArrayList();

        openSummarySheet(id_sum);


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

    public ObservableList<Assignment> getAssignments() {
        return assignments; //TODO: AGGIUNGI ROBA PER NON MODIFICARE
    }

    public Assignment addAssignment(KitchenDuty duty) {
        Assignment ass = new Assignment(duty);
        assignments.add(ass);
        return ass;
    }

    private void openSummarySheet(int id_ss){
        String query = "SELECT * FROM Assignments a, recipes r WHERE (a.id_duty = r.id) AND id_sheet = "+ id_ss;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Recipe r = new Recipe(rs.getString("name"));
                r.setId(rs.getInt("id_duty"));

                Assignment as = new Assignment(r);
                as.setId(rs.getInt("a.id"));
                as.setCook(User.loadUserById(rs.getInt("id_cook")));
                as.setEstTime(rs.getString("estTime"));
                as.setQuantity(rs.getString("quantity"));
                as.setReady(rs.getBoolean("ready"));
                as.setPosition(rs.getInt("position"));

               // as.setShift("id_shift"); //TODO: Fare object di shift


                assignments.add(as);

            }
        });
    }
}
