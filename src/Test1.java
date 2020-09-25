import businesslogic.CatERing;
import businesslogic.UseCaseLogicException;
import businesslogic.kitchenTask.SummarySheet;

public class Test1 {
    public static void main(String[] args) {
        CatERing.getInstance().getUserManager().fakeLogin("Lidia");
        System.out.println(CatERing.getInstance().getUserManager().getCurrentUser());


        //SummarySheet ss = CatERing.getInstance().getTaskMgr().generateSummarySheet(event,service);
    }
}
