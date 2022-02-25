package Tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.ItemWidget;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;
import com.epicbot.api.shared.util.time.Time;

public class InteractItem extends Task{
    int firstId;
    int secondId;
    boolean combine = false;
    String interaction;

    public InteractItem(APIContext ctx, int stage, int id, String interaction) {
        super(ctx, stage);

        this.firstId = id;
        this.interaction = interaction;

    }

    @Override
    public boolean run() {
        if (tile != null) {
            interactItem(tile, firstId, interaction);
        } else if (location != null) {
            interactItem(location, firstId, interaction);
        } else {
            interact();
        }
        return true;
    }


    // Interaction methods
    public Task combine(int id) {
        this.secondId = id;
        combine = true;
        return this;
    }

    public void interact() {
        if (ctx.bank().isOpen()) {
            ctx.bank().close();
        }
        ItemWidget x = ctx.inventory().getItem(firstId);
        if (x != null && x.interact(interaction)) {
            if (combine) {
                ItemWidget y = ctx.inventory().getItem(secondId);
                if (y != null) {
                    if (y.click()) {
                        Time.sleep(1_500);
                    }
                }
            }
            Time.sleep(5_000, () -> !ctx.localPlayer().isAnimating() && !ctx.localPlayer().isMoving(), 3_000);
        }
    }

    public void interactItem(Area location, int id, String interaction) {
        if (location.contains(ctx.localPlayer().getLocation())) {

        } else {
            walk(location);
        }
    }

    public void interactItem(Tile tile, int id, String interaction) {
        if (tile.distanceTo(ctx) < 1) {
            ItemWidget s = ctx.inventory().getItem(firstId);
            if (s != null && s.interact(interaction)) {
                Time.sleep(1_000, () -> !ctx.localPlayer().isAnimating() && !ctx.localPlayer().isMoving(), 3_000);
            }
        } else {
            walk(tile);
        }
    }
}
