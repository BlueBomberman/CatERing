package businesslogic.event;

import businesslogic.menu.Menu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.PersistenceManager;
import persistence.ResultHandler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class ServiceInfo implements EventItemInfo {
    private int id;
    private String name;
    private Date date;
    private Time timeStart;
    private Time timeEnd;
    private int participants;
    private Menu approvedMenu;

    public ServiceInfo(String name) {
        this.name = name;
    }


    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
        String timeStart1 = sdf.format(timeStart);
        String timeEnd1 = sdf.format(timeEnd);
        String date1 = null;
        try {
            date1 = EventInfo.dateFormat(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return name + ": " + date1 + " (" + timeStart1 + "-" + timeEnd1 + "), " + participants + " partecipanti.";
    }

    // STATIC METHODS FOR PERSISTENCE

    public static ObservableList<ServiceInfo> loadServiceInfoForEvent(int event_id) {
        ObservableList<ServiceInfo> result = FXCollections.observableArrayList();
        String query = "SELECT * " +
                "FROM Services WHERE event_id = " + event_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                String s = rs.getString("name");
                ServiceInfo serv = new ServiceInfo(s);
                serv.id = rs.getInt("id");
                serv.date = rs.getDate("service_date");
                serv.timeStart = rs.getTime("time_start");
                serv.timeEnd = rs.getTime("time_end");
                serv.participants = rs.getInt("expected_participants");
                int id = rs.getInt("approved_menu_id");
                if(id!=0)
                    serv.approvedMenu = Menu.loadMenuById(id);
                result.add(serv);
            }
        });

        return result;
    }

    public void setApprovedMenu(Menu approvedMenu) {
        this.approvedMenu = approvedMenu;
        //TODO: Aggiungere persistenza
    }

    public String getName() {
        return name;
    }

    public Menu getApprovedMenu() {
        return approvedMenu;
    }

    public int getId() {
        return id;
    }
}
