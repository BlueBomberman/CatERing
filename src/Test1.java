import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.Assignment;
import businesslogic.kitchenTask.KitchenTaskException;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.recipe.Recipe;
import businesslogic.shift.Shift;
import businesslogic.shift.ShiftException;
import businesslogic.user.User;

public class Test1 {
    public static void main(String[] args) throws KitchenTaskException, UseCaseLogicException, ShiftException {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

        EventInfo event = CatERing.getInstance().getEventManager().getEventInfo().get(0);
        ServiceInfo service = event.getServiceById(2);

        //passo 1
        if(service != null){
            SummarySheet ss = CatERing.getInstance().getTaskMgr().generateSummarySheet(event,service);
            System.out.println(ss);

            //passo 2
            Recipe ric = CatERing.getInstance().getRecipeManager().getRecipes().get(1);
            Assignment as = ss.getAssignments().get(7);
            System.out.println(ss);
            System.out.println("Hello id1: " + as.getId());

            CatERing.getInstance().getTaskMgr().editOrder(as,4);
            System.out.println(ss);

            Shift s= CatERing.getInstance().getShiftManager().getShifts(2).get(0);
            User c = User.loadUserById(5);
            //User c1 = new User();
            //System.out.println("UTENTE NON ESISTENTE: " + c1.getId());
            //Passo5
            CatERing.getInstance().getTaskMgr().defineAssignment(as,s,null,null,"9");

            System.out.println("Hello id: " + as.getId());
        }
        else{
            System.out.println("coglione");
        }




    }
}
