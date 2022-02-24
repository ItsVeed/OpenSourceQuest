package Tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.SceneObject;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;
import com.epicbot.api.shared.util.time.Time;

public class InteractObject extends Task{
    int id;
    String interaction;

    public InteractObject(APIContext ctx,int id, String interaction) {
        super(ctx);
        this.id = id;
        this.interaction = interaction;

    }

    @Override
    public boolean run() {
        if (tile != null) {
            interactObject(tile, id, interaction);
        } else if (location != null) {
            interactObject(location, id, interaction);
        }
        return true;
    }

    // Interaction methods
    public void interactObject(Area location, int id, String interaction) {
        SceneObject s = ctx.objects().query().id(id).results().first();
        if (s != null && s.canReach(ctx)) {
            if (!s.isVisible()) {
                ctx.camera().turnTo(s);
            }
            if (interaction == null) {
                s.interact();
            } else {
                s.interact(interaction);
            }
            Time.sleep(1_000, () -> !ctx.localPlayer().isAnimating() && !ctx.localPlayer().isMoving(), 3_000);
        } else {
            ctx.webWalking().walkTo(location.getCentralTile());
        }
    }

    public void interactObject(Tile tile, int id, String interaction) {
        SceneObject s = ctx.objects().query().id(id).results().first();
        if (tile.distanceTo(ctx) < 1) {
            if (!s.isVisible()) {
                ctx.camera().turnTo(s);
            }
            if (interaction == null) {
                s.interact();
            } else {
                s.interact(interaction);
            }
            Time.sleep(1_000, () -> !ctx.localPlayer().isAnimating() && !ctx.localPlayer().isMoving(), 3_000);
        } else {
            ctx.webWalking().walkTo(tile);
        }
    }
}
