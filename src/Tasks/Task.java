package Tasks;

import com.epicbot.api.shared.APIContext;

public class Task {
    APIContext ctx;

    public Task(APIContext ctx) {this.ctx = ctx;}

    public void run() {
        ctx.script().stop("This task is not ready to be ran.");
    }
}
