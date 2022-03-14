import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import data.Vars;

@ScriptManifest(name="VeedsQuester", gameType = GameType.OS)
public class Main extends LoopScript {
    public static APIContext ctx;
    private boolean checkedSettings = false;
    
    @Override
    protected int loop() {
        if (!Vars.start) {
            return 0;
        }

        if (!ctx.client().isLoggedIn()) {
            return 0;
        }

        if (Vars.currentQuest == null) {
            for (int i=0; i < Vars.quests.length; i++) {
                if (Vars.quests[i].getDoQuest() == true) {
                    Vars.currentQuest = Vars.quests[i];
                }
            }
            if (Vars.currentQuest == null) {
                ctx.script().stop("Completed all quests.");
            }
        } else {
            Vars.currentQuest.main();
        }

        return 0;
    }

    @Override
    public boolean onStart(String... strings) {
        System.out.println("Staring quester.");

        ctx = getAPIContext();

        Vars.initialiseQuests(ctx);

        Gui gui = new Gui(getAPIContext());

        return true;
    }
}
