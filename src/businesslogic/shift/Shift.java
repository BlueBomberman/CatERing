package businesslogic.shift;

import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.Assignment;
import businesslogic.recipe.Recipe;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Shift {
    private Timestamp startTime;
    private Timestamp endTime;
    private boolean closed;
    private int id_service;
    private int id;

    //private static Map<Integer, Shift> all = new HashMap<>();

    public static ObservableList<Shift> getServiceShifts(int id_service) {

        ObservableList<Shift> all = FXCollections.observableArrayList();
        //int uid = CatERing.getInstance().getUserManager().getCurrentUser().getId();
        System.out.println("Service id: "+ id_service);

        String query = "SELECT * FROM Shifts WHERE id_service = "+ id_service;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Shift s = new Shift();
                s.id = rs.getInt("id");
                s.id_service = id_service;
                s.closed = rs.getBoolean("closed");
                s.startTime = rs.getTimestamp("startTime");
                s.endTime = rs.getTimestamp("endTime");

                all.add(s);
            }
        });

        return all;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "Turno "+id + ": "+ startTime + " - " + endTime ;
    }
}
