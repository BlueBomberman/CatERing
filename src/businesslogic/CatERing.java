package businesslogic;

import businesslogic.event.EventManager;
import businesslogic.kitchenTask.KitchenTaskManager;
import businesslogic.menu.Menu;
import businesslogic.menu.MenuManager;
import businesslogic.recipe.RecipeManager;
import businesslogic.shift.ShiftManager;
import businesslogic.user.UserManager;
import persistence.KitchenTaskPersistence;
import persistence.MenuPersistence;
import persistence.PersistenceManager;

public class CatERing {
    private static CatERing singleInstance;

    public static CatERing getInstance() {
        if (singleInstance == null) {
            singleInstance = new CatERing();
        }
        return singleInstance;
    }

    private MenuManager menuMgr;
    private RecipeManager recipeMgr;
    private UserManager userMgr;
    private EventManager eventMgr;
    private KitchenTaskManager taskMgr;
    private ShiftManager shiftMgr;

    private MenuPersistence menuPersistence;
    private KitchenTaskPersistence kTPersistence;



    private CatERing() {
        menuMgr = new MenuManager();
        recipeMgr = new RecipeManager();
        userMgr = new UserManager();
        eventMgr = new EventManager();
        shiftMgr = new ShiftManager();


        menuPersistence = new MenuPersistence();
        menuMgr.addEventReceiver(menuPersistence);

        taskMgr = new KitchenTaskManager();
        kTPersistence = new KitchenTaskPersistence();
        taskMgr.addEventReceiver(kTPersistence);
    }


    public MenuManager getMenuManager() {
        return menuMgr;
    }

    public RecipeManager getRecipeManager() {
        return recipeMgr;
    }

    public UserManager getUserManager() {
        return userMgr;
    }

    public EventManager getEventManager() { return eventMgr; }

    public KitchenTaskManager getTaskMgr() { return taskMgr; }

    public ShiftManager getShiftManager() { return shiftMgr; }

}
