import Quests.GoblinDiplomacy;
import Quests.Quest;
import Quests.RomeoAndJuliet;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;
import data.Vars;

import java.util.Arrays;
import java.util.Optional;

@ScriptManifest(name="name", gameType = GameType.OS)
public class Main extends LoopScript {
    public static APIContext ctx;
    
    @Override
    protected int loop() {
        if (Vars.currentQuest == null) {
            Optional<Quest> q = Arrays.stream(Vars.quests).filter(e -> e.doQuest == true).findFirst();
            if (!q.isPresent()) {
                ctx.script().stop("All quests have been completed.");
            } else {
                Vars.currentQuest = q.get();
            }
        } else {
            Vars.currentQuest.main();
        }

        return 0;
    }

    @Override
    public boolean onStart(String... strings) {
        GoblinDiplomacy.doQuest = true;

        Vars.quests = new Quest[]
                {
                        new RomeoAndJuliet(ctx),
                        new GoblinDiplomacy(ctx),
                };

        return true;
    }
}
