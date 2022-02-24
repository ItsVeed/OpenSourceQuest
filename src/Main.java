import Quests.GoblinDiplomacy;
import Quests.Quest;
import Quests.RomeoAndJuliet;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import data.Vars;

@ScriptManifest(name="Quester", gameType = GameType.OS)
public class Main extends LoopScript {
    public static APIContext ctx;
    
    @Override
    protected int loop() {


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

        Vars.quests = new Quest[]
                {
                        new RomeoAndJuliet(ctx),
                        new GoblinDiplomacy(ctx),
                };

        Vars.quests[0].setDoQuest(true);
        Vars.quests[1].setDoQuest(true);
        Vars.quests[2].setDoQuest(true);

        return true;
    }
}
