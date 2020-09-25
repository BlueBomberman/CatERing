package businesslogic.kitchenTask;

import businesslogic.menu.MenuItem;
import businesslogic.menu.Section;
import businesslogic.recipe.KitchenDuty;
import businesslogic.recipe.Recipe;
import businesslogic.shift.Shift;
import businesslogic.user.User;
import javafx.collections.ObservableList;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Assignment {
    private boolean ready;
    private String estTime;
    private String quantity;
    private int id;
    private int position;

    private User cook;
    private Shift shift;
    private KitchenDuty duty;


    public Assignment(KitchenDuty itemRecipe) {
        this.duty = itemRecipe;
        this.ready = false;
    }




    //getter
    public boolean isReady() {
        return ready;
    }

    public String getEstTime() {
        return estTime;
    }

    public String getQuantity() {
        return quantity;
    }

    public User getCook() {
        return cook;
    }

    public Shift getShift() {
        return shift;
    }

    public KitchenDuty getDuty() {
        return duty;
    }


    public static void saveAllNewAssignments(int id, ObservableList<Assignment> assignments) {
        String assInsert = "INSERT INTO catering.Assignments (ready, position, id_duty, id_sheet) VALUES (?, ?, ?, ?);";
        PersistenceManager.executeBatchUpdate(assInsert, assignments.size(), new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setBoolean(1, assignments.get(batchCount).ready);
                ps.setInt(2, batchCount);
                ps.setInt(3, assignments.get(batchCount).duty.getId());
                ps.setInt(4, id);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                assignments.get(count).id = rs.getInt(1);
            }
        });
    }
}
