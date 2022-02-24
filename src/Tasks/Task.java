package Tasks;

import com.epicbot.api.shared.APIContext;

public class Task {
    // Context

    APIContext ctx;

    // End

    //Constructor

    public Task(APIContext ctx) {this.ctx = ctx;}

    // End

    // Main method

    public void run() {
        ctx.script().stop("This task is not ready to be ran.");
    }

    // End
}
