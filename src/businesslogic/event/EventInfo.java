package businesslogic.event;

import businesslogic.CatERing;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class EventInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date dateStart;
    private Date dateEnd;
    private int participants;
    private User organizer;
    private User chef;

    private enum state{NUOVO,INCORSO,TERMINATO,ANNULLATO};
    private state currentState;

    private ObservableList<ServiceInfo> services;

    public EventInfo(String name) {
        this.name = name;
        id = 0;
    }

    public ObservableList<ServiceInfo> getServices() {
        return FXCollections.unmodifiableObservableList(this.services);
    }

    public String toString() {
        String end = null;
        String start = null;
        try {
             start = dateFormat(dateStart);

            end = dateFormat(dateEnd);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(end != null && end.equals(start)){
            return name + ": il giorno " + end + ", numero di partecipanti: " + participants + " ( organizzatore: " + organizer.getUserName() + ")";

        }else {

            return name + ": dal " + start + " al " + end + ", numero di partecipanti: " + participants + "  ( organizzatore: " + organizer.getUserName() + ")";
        }
    }

    public static String dateFormat(Date date) throws ParseException {
        final String OLD_FORMAT = "yyyy-MM-dd";
        final String NEW_FORMAT = "dd/MM/yyyy";

// August 12, 2010
        System.out.println("Data inizio: " + date);
        String oldDateString = date.toString();
        System.out.println("Data Stringa: " + oldDateString);

        String newDateString;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        java.util.Date d =  sdf.parse(oldDateString);
        System.out.println("data parsificata: " + d);
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);
        System.out.println("Data finale: "+newDateString );
        return newDateString;
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ObservableList<EventInfo> loadAllEventInfo() {
        ObservableList<EventInfo> all = FXCollections.observableArrayList();
        String query = "SELECT * FROM Events WHERE true";
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String n = rs.getString("name");
                EventInfo e = new EventInfo(n);
                e.id = rs.getInt("id");
                e.dateStart = rs.getDate("date_start");
                e.dateEnd = rs.getDate("date_end");
                e.participants = rs.getInt("expected_participants");
                int org = rs.getInt("organizer_id");
                e.organizer = User.loadUserById(org);
                int ch = rs.getInt("chef_id");
                e.chef = User.loadUserById(ch);
                e.currentState = state.NUOVO; //TODO: PRENDI DAL DB
                all.add(e);
            }
        });

        for (EventInfo e : all) {
            e.services = ServiceInfo.loadServiceInfoForEvent(e.id);
        }
        return all;
    }

    public static ObservableList<EventInfo> loadMyEvent(int uid) {
        ObservableList<EventInfo> all = FXCollections.observableArrayList();
        //int uid = CatERing.getInstance().getUserManager().getCurrentUser().getId();
        System.out.println("User id: "+ uid);

        String query = "SELECT * FROM Events WHERE chef_id = "+ uid;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String n = rs.getString("name");
                EventInfo e = new EventInfo(n);
                e.id = rs.getInt("id");
                e.dateStart = rs.getDate("date_start");
                e.dateEnd = rs.getDate("date_end");
                e.participants = rs.getInt("expected_participants");
                int org = rs.getInt("organizer_id");
                e.organizer = User.loadUserById(org);
                int ch = rs.getInt("chef_id");
                e.chef = User.loadUserById(ch);
                e.currentState = state.NUOVO; //TODO: PRENDI DAL DB
                all.add(e);
            }
        });

        for (EventInfo e : all) {
            e.services = ServiceInfo.loadServiceInfoForEvent(e.id);
        }
        return all;
    }

    public boolean hasService(ServiceInfo service){
        return services.contains(service);
    }

    public void setChef(User chef) {
        this.chef = chef;
        //TODO:NOTIFY
    }

    public User getChef() {
        return chef;
    }

    public ServiceInfo getServiceById(int i) {
        for(ServiceInfo ser: services){
            if(ser.getId() == i)
                return ser;
        }
        return null;
    }
}
