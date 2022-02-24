package Tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.util.time.Time;

import java.util.HashMap;

public class GetRequiredItems extends Task{
    HashMap<String, Integer> requiredItems;
    boolean bypass = false;

    public GetRequiredItems(APIContext ctx, HashMap<String, Integer> requiredItems) {
        super(ctx);
        this.requiredItems = requiredItems;
    }

    @Override
    public boolean run() {
        return getItems();
    }

    public boolean getItems() {
        if (bypass) {
            return true;
        } else {
            for (String i : requiredItems.keySet()) {
                if (ctx.inventory().getCount(i) != requiredItems.get(i)) {
                    withdraw(i, requiredItems.get(i) - ctx.inventory().getCount(i));
                }
            }
            if (hasAllItems(requiredItems)) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Task bypass(HashMap<String, Integer> items) {
        bypass = hasAllItems(items);
        return this;
    }

    private boolean hasAllItems(HashMap<String, Integer> items) {
        boolean missingItem = false;
        for (String i : items.keySet()) {
            if (ctx.inventory().getCount(i) < items.get(i)) {
                missingItem = true;
            }
        }
        return missingItem;
    }

    public void withdraw(String item, int amount) {
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
        } else  if (!ctx.bank().isReachable()) {
            ctx.webWalking().walkToBank();
        }
    }
}
