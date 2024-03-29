package Tasks;

import Quests.Quest;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;
import com.epicbot.api.shared.util.time.Time;

import java.util.HashMap;

public class Task {
    // Context

    APIContext ctx;

    // End

    // Task vars

    public boolean stageCheck = false;
    public int stage;

    HashMap<String, Integer> requiredItems = new HashMap<>();
    boolean bypass = false;

    boolean skip = false;

    boolean localWalker = false;
    Area location = null;
    Tile tile = null;

    // End

    //Constructor

    public Task(Quest quest, int stage) {
        this.ctx = quest.ctx;
        setStageCheck(stage);
    }

    // End

    // Main methods

    public boolean main() {
        System.out.println(this.skip);
        if (this.skip) {
            System.out.println("Skip");
            System.out.println(this.skip);
            return true;
        } else if (getItems()) {
             return run();
        } else {
             return false;
        }
    }

    public boolean run() {
        ctx.script().stop("This task is not ready to be ran.");
        return true;
    }

    // End

    // Items

    public boolean getItems() {
            if (bypass) {
                return true;
            } else {
                for (String i : requiredItems.keySet()) {
                    if (ctx.inventory().getCount(i) < requiredItems.get(i)) {
                        withdraw(i, requiredItems.get(i) - ctx.inventory().getCount(i));
                    }
                }
                return !missingItems(requiredItems);
            }
    }

    private boolean missingItems(HashMap<String, Integer> items) {
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

    // End


    // Stage

    public void setStageCheck(int i) {
        this.stageCheck = true;
        this.stage = i;
    }

    // End


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

    // End


    // Var methods

    public Task localWalker() {
        this.localWalker = true;
        return this;
    }

    public Task setRequiredItems(HashMap<String, Integer> requiredItems) {
        this.requiredItems = requiredItems;
        return this;
    }

    public Task bypass(HashMap<String, Integer> items) {
        this.bypass = !missingItems(items);
        return this;
    }

    public Task skipIfHasItems(HashMap<String, Integer> items) {
        this.skip = !missingItems(items);
        return this;
    }

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
