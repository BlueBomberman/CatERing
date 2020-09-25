package businesslogic.kitchenTask;

import businesslogic.recipe.KitchenDuty;
import businesslogic.recipe.Recipe;
import businesslogic.shift.Shift;
import businesslogic.user.User;

public class Assignement {
    private boolean ready;
    private String estTime;
    private String quantity;

    private User cook;
    private Shift shift;
    private KitchenDuty duty;


    public Assignement(Recipe itemRecipe) {
        this.duty = itemRecipe;
        this.ready = false;
    }
}
