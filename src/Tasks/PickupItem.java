package Tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.GroundItem;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.util.time.Time;

public class PickupItem extends Task{
    int id;

    public PickupItem(APIContext ctx, Area location, int id) {
        super(ctx);

        this.location = location;
        this.id = id;
    }

    @Override
    public boolean run() {
        if (ctx.inventory().contains(id)) {
            return true;
        } else {
            pickupItem(location, id);
            return false;
        }

    }

    public void pickupItem(Area location, int item) {
        GroundItem i = ctx.groundItems().query().id(item).reachable().results().nearest();
        if (i != null && i.canReach(ctx)) {
            if (!i.isVisible()) {
                ctx.camera().turnTo(i);
            }
            if (i.interact("Take")) {
                Time.sleep(1_000, () -> ctx.inventory().contains(item), 1_000);
            }
        } else if (!location.contains(ctx.localPlayer().getLocation())) {
            walk(location);
        }
    }
}
