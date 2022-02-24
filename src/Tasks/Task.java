package Tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;

public class Task {
    // Context

    APIContext ctx;

    // End

    // Task vars

    Boolean localWalker = false;
    Area location = null;
    Tile tile = null;

    // End

    //Constructor

    public Task(APIContext ctx) {this.ctx = ctx;}

    // End

    // Main method

    public boolean run() {
        ctx.script().stop("This task is not ready to be ran.");
        return true;
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
