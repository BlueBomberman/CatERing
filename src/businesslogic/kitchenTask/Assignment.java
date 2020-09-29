package businesslogic.kitchenTask;

import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.menu.MenuItem;
import businesslogic.menu.Section;
import businesslogic.recipe.KitchenDuty;
import businesslogic.recipe.Recipe;
import businesslogic.shift.Shift;
import businesslogic.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import persistence.BatchUpdateHandler;
import persistence.PersistenceManager;
import persistence.ResultHandler;

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

    public static void saveNewAssignment(SummarySheet sh,Assignment as, int pos) {
        System.out.println("sheet: "+sh.getId()+", ass: "+ as.getDuty().getId()+ ", pos: "+ pos);
        String assInsert = "INSERT INTO catering.Assignments (id_duty, id_sheet,position,ready) VALUES (?,?,?,?);";
        PersistenceManager.executeBatchUpdate(assInsert, 1, new BatchUpdateHandler() {
            @Override
            public void handleBatchItem(PreparedStatement ps, int batchCount) throws SQLException {
                ps.setInt(1, as.getDuty().getId());
                ps.setInt(2, sh.getId());
                ps.setInt(3, pos);
                ps.setBoolean(4, as.ready);
            }

            @Override
            public void handleGeneratedIds(ResultSet rs, int count) throws SQLException {
                as.id = rs.getInt(1);
            }
        });
    }

    public static ObservableList<Assignment> getShiftAssignments(int shift_id) {
        ObservableList<Assignment> ret = FXCollections.observableArrayList();
        System.out.println("Assignments for shift n° " + shift_id);

        String query = "SELECT * FROM Assignments a, recipes r WHERE (a.id_duty = r.id) AND id_shift = "+ shift_id;
        PersistenceManager.executeQuery(query, new ResultHandler() {
            @Override
            public void handle(ResultSet rs) throws SQLException {
                Recipe r = new Recipe(rs.getString("name"));
                r.setId(rs.getInt("id_duty"));

                Assignment as = new Assignment(r);
                as.setId(rs.getInt("a.id"));
                if(rs.getInt("id_cook")!=0)
                as.setCook(User.loadUserById(rs.getInt("id_cook")));
                as.setEstTime(rs.getString("estTime"));
                as.setQuantity(rs.getString("quantity"));
                as.setReady(rs.getBoolean("ready"));
                as.setPosition(rs.getInt("position"));

                ret.add(as);
            }
        });

        return ret;
    }

    public static void saveChanges(Assignment as) throws UseCaseLogicException {

        String itemdel = "";
        int idShift = as.getShift().getId();
        User cook = as.getCook();
        int idCook =0;
        if(cook!= null)
        idCook = cook.getId();
        String estTime = as.getEstTime();
        String quantity = as.getQuantity();

       if(idCook != 0 &&  estTime != null && quantity != null){
           System.out.println("Caso 1");
           itemdel = "UPDATE Assignments SET id_shift = " + idShift +
                ", id_cook = " + idCook + ", estTime = '"+ estTime  +
                "', quantity = '" + quantity + "' WHERE id = " + as.getId();

       } else if(idCook != 0 && estTime != null){
           System.out.println("Caso 2");

           itemdel = "UPDATE Assignments SET id_shift = " + idShift +
                   ", id_cook = " + idCook + ", estTime = '"+ estTime  +
                   "' WHERE id = " + as.getId();

       } else if ( idCook != 0 && quantity != null){
           System.out.println("Caso 3");

           itemdel = "UPDATE Assignments SET id_shift = " + idShift +
                   ", id_cook = " + idCook + ", quantity = '" + quantity + "' WHERE id = " + as.getId();

       } else if (quantity != null && estTime != null){
           System.out.println("Caso 4");

           itemdel = "UPDATE Assignments SET id_shift = " + idShift +
                   ", estTime = '"+ estTime  +
                   "', quantity = '" + quantity + "' WHERE id = " + as.getId();

       } else if (idCook != 0) {
           System.out.println("Caso 5");

           itemdel = "UPDATE Assignments SET id_shift = " + idShift +
                   ", id_cook = " + idCook + " WHERE id = " + as.getId();

       } else if (estTime != null){
           System.out.println("Caso 6");

           itemdel = "UPDATE Assignments SET id_shift = " + idShift +
                   ", estTime = '"+ estTime  +
                   "' WHERE id = " + as.getId();

       } else if (quantity != null){
           System.out.println("Caso 7");

           itemdel = "UPDATE Assignments SET id_shift = " + idShift +
                   ", quantity = '" + quantity + "' WHERE id = " + as.getId();

       } else if(idShift != 0){


           itemdel = "UPDATE Assignments SET id_shift = " + idShift +
                  " WHERE id = " + as.getId();

       }
       else{
           throw new UseCaseLogicException();
       }
        PersistenceManager.executeUpdate(itemdel);

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

    public int getId() {
        return id;
    }

    //setter

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public void setEstTime(String estTime) {
        this.estTime = estTime;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCook(User cook) {
        this.cook = cook;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }

    public void setDuty(KitchenDuty duty) {
        this.duty = duty;
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

    public static void saveReady(Assignment as) {
        String itemdel = "UPDATE Assignments SET ready=1 WHERE id = " + as.getId();
        PersistenceManager.executeUpdate(itemdel);
    }

    public String toString() {
        //DEVO STAMPARE: nome ricetta, cuoco, pronto, quantità, estTime
        String pronto = "";
        if(ready)
            pronto = "la mansione è già pronta!";
         if(cook != null &&  estTime != null && quantity != null){
           System.out.println("Caso 1");

           return "Ricetta: "+duty.getName() +", Cuoco: " + cook.getUserName() + ",  quantita': " + quantity +", tempo stimato: " + estTime + ", " + pronto;

       } else if(cook != null && estTime != null){
           System.out.println("Caso 2");

             return "Ricetta: "+duty.getName() +", Cuoco: " + cook.getUserName() + ", tempo stimato: " + estTime + ", " + pronto;


       } else if ( cook != null && quantity != null){
           System.out.println("Caso 3");

           return "Ricetta: "+duty.getName() +", Cuoco: " + cook.getUserName() + ",  quantita': " + quantity  + ", " + pronto;


       } else if (quantity != null && estTime != null){
           System.out.println("Caso 4");

             return "Ricetta: "+duty.getName()  + ",  quantita': " + quantity +", tempo stimato: " + estTime + ", " + pronto;


       } else if (cook != null) {
           System.out.println("Caso 5");

             return "Ricetta: "+duty.getName() +", Cuoco: " + cook.getUserName() +  ", " + pronto;

         } else if (estTime != null){
           System.out.println("Caso 6");
             return "Ricetta: "+duty.getName() +", tempo stimato: " + estTime + ", " + pronto;


       } else if (quantity != null){
           System.out.println("Caso 7");
             return "Ricetta: "+duty.getName() + ",  quantita': " + quantity + ", " + pronto;


       } else
             return "Ricetta: "+duty.getName() + ", " + pronto;
    }

    public Assignment define(Shift s, User cook, String estTime, String quantity) {
        this.shift = s;
        if(cook != null)
            this.cook = cook;
        if(estTime != null)
            this.estTime = estTime;
        if(quantity != null)
            this.quantity = quantity;
        return this;

    }
}
