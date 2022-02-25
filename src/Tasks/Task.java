package Tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;
import com.epicbot.api.shared.util.time.Time;

import java.util.HashMap;

public class Task {
    // Context

    APIContext ctx;

    // End

    public boolean stageCheck = false;
    public int stage;

    // Task vars

    HashMap<String, Integer> requiredItems = new HashMap<>();

    boolean localWalker = false;
    Area location = null;
    Tile tile = null;

    // End

    //Constructor

    public Task(APIContext ctx, int stage) {
        this.ctx = ctx;
        setStageCheck(stage);
    }

    // End

    // Main method

    public boolean main() {
         if (getItems()) {
             return run();
         } else {
             return false;
         }
    }

    public Task setRequiredItems(HashMap<String, Integer> requiredItems) {
        this.requiredItems = requiredItems;
        return this;
    }

    private boolean getItems() {
        {   System.out.println(requiredItems.keySet());
            for (String i : requiredItems.keySet()) {
                System.out.println("Loop");
                if (ctx.inventory().getCount(i) < requiredItems.get(i)) {
                    withdraw(i, requiredItems.get(i) - ctx.inventory().getCount(i));
                }
            }
            return hasAllItems(requiredItems);
        }
    }

    private boolean hasAllItems(HashMap<String, Integer> items) {
        boolean missingItem = false;
        for (String i : items.keySet()) {
            if (!ctx.inventory().contains(i)) {
                missingItem = true;
            }
        }
        return missingItem;
    }

    private void withdraw(String item, int amount) {
        if (ctx.bank().isReachable()) {
            ctx.camera();
            if (ctx.bank().isOpen()) {
                Time.sleep(200 - 50, 200 + 50);
                if (ctx.bank().contains(item)) {
                    ctx.bank().withdraw(amount, item);
                } else {
                    ctx.script().stop("You don't have " + item + " in your bank.");
                }
            } else if (!ctx.bank().isOpen()) {
                ctx.bank().open();
                Time.sleep(200, () -> ctx.bank().isOpen(), 1_000);
            }
        } else {
            ctx.webWalking().walkToBank();
        }
    }

    public boolean run() {
        ctx.script().stop("This task is not ready to be ran.");
        return true;
    }

    // End

    public void setStageCheck(int i) {
        stageCheck = true;
        stage = i;
    }


    // Walking

    public void walk(Area x) {
        if (localWalker) {
            ctx.walking().walkTo(x.getCentralTile());
        } else {
            ctx.webWalking().walkTo(x.getCentralTile());
        }
    }

    public void walk(Tile x) {
        if (localWalker) {
            ctx.walking().walkTo(x);
        } else {
            ctx.webWalking().walkTo(x);
        }
    }

    public Task localWalker() {
        localWalker = true;
        return this;
    }

    // End


    // Var methods

    public Task setArea(Area location) {

        this.location = location;
        return this;
    }

    public Task setTile(Tile tile) {
        this.tile = tile;
        return this;
    }

    // End
}
