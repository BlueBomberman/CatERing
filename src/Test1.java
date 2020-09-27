import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.Assignment;
import businesslogic.kitchenTask.KitchenTaskException;
import businesslogic.kitchenTask.SummarySheet;
import businesslogic.recipe.Recipe;

public class Test1 {
    public static void main(String[] args) throws KitchenTaskException, UseCaseLogicException {
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
            Assignment as = CatERing.getInstance().getTaskMgr().createAssignment(ric);
            System.out.println(ss);

            CatERing.getInstance().getTaskMgr().editOrder(as,4);
            System.out.println(ss);
        }
        else{
            System.out.println("coglione");
        }




    }
}
