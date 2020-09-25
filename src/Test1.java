import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.event.EventInfo;
import businesslogic.event.ServiceInfo;
import businesslogic.kitchenTask.KitchenTaskException;
import businesslogic.kitchenTask.SummarySheet;

public class Test1 {
    public static void main(String[] args) throws KitchenTaskException, UseCaseLogicException {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());

        EventInfo event = CatERing.getInstance().getEventManager().getEventInfo().get(0);
        ServiceInfo service = event.getServiceById(5);
        if(service != null){SummarySheet ss = CatERing.getInstance().getTaskMgr().generateSummarySheet(event,service);
            System.out.println(ss);}
        else{
            System.out.println("coglione");
        }


    }
}
