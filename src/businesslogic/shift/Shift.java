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

    private static Map<Integer, Shift> allShifts = new HashMap<>();

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

    public static ObservableList<User> getShiftCooks(int id) {

        ObservableList<User> cooks = FXCollections.observableArrayList();
        //int uid = CatERing.getInstance().getUserManager().getCurrentUser().getId();
        //System.out.println("Service id: "+ id_service);

        String query = "SELECT id_user FROM assignedshifts s, userroles ur WHERE s.id_user = ur.user_id AND role_id = 'c' AND  id_shift = "+ id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                User u = User.loadUserById(rs.getInt("id_user"));


                cooks.add(u);
            }
        });

        return cooks;

    }

    public static ObservableList<Shift> getOpenShifts(int id_service) {

        ObservableList<Shift> all = FXCollections.observableArrayList();
        //int uid = CatERing.getInstance().getUserManager().getCurrentUser().getId();
        System.out.println("Service id: "+ id_service);

        String query = "SELECT * FROM Shifts WHERE id_service = "+ id_service +" AND closed = 0";
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

    public static Shift loadShiftById(int id_shift) {

        if (allShifts.containsKey(id_shift)) return allShifts.get(id_shift);

        Shift load = new Shift();
        String userQuery = "SELECT * FROM shifts WHERE id="+id_shift;
        PersistenceManager.executeQuery(userQuery, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                load.id = rs.getInt("id");
                load.id_service = rs.getInt("id_service");
                load.startTime = rs.getTimestamp("startTime");
                load.endTime = rs.getTimestamp("endTime");
                load.closed = rs.getBoolean("closed");
            }
        });
        if (load.id > 0) {
            allShifts.put(load.id, load);

        }
        return load;
    }

    public int getId() {
        return id;
    }

    public String toString() {
        return "Turno "+id + ": dal "+ startTime.getDay() + "/" + startTime.getMonth()+"/"+ startTime.getYear()+ " alle " + endTime ;
    }

    public boolean isClosed() {
        return closed;
    }
}
