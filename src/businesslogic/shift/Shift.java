package businesslogic.shift;

import businesslogic.recipe.Recipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.sql.Date;

public class Shift {
    private Time startTime;
    private Time endTime;
    private boolean closed;
    private Date date;

    private static Map<Integer, Shift> all = new HashMap<>();

    /*public static ObservableList<Shift> loadAllShifts() {
        String query = "SELECT * FROM Shifts";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                int id = rs.getInt("id");
                if (all.containsKey(id)) {
                    Shift sh = all.get(id);
                    sh.startTime = rs.getTime("startTime");
                    sh.endTime = rs.getTime("endTime");
                    sh.closed = rs.getBoolean("closed");
                    //TODO: vai avanti
                }else {
                    Recipe rec = new Recipe(rs.getString("name"));
                    rec.id = id;
                    all.put(rec.id, rec);
                }
            }
        });
        ObservableList<Recipe> ret =  FXCollections.observableArrayList(all.values());
        Collections.sort(ret, new Comparator<Recipe>() {
            @Override
            public int compare(Recipe o1, Recipe o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });
        return ret;
    }*/

}
