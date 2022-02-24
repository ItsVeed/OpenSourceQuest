import Quests.GoblinDiplomacy;
import Quests.Quest;
import Quests.RomeoAndJuliet;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.GameType;
import com.epicbot.api.shared.script.LoopScript;
import com.epicbot.api.shared.script.ScriptManifest;

import java.util.Arrays;
import java.util.Optional;

@ScriptManifest(name="name", gameType = GameType.OS)
public class Main extends LoopScript {
    public static APIContext ctx;
    public Quest[] quests =
            {
                    new RomeoAndJuliet(ctx),
                    new GoblinDiplomacy(ctx),
            };
    public Optional<Quest> currentQuest = null;
    
    @Override
    protected int loop() {
        if (currentQuest == null) {
            currentQuest = Arrays.stream(quests).filter(e -> e.doQuest == true).findFirst();
        } else {
            currentQuest.get().main();
        }

        return 0;
    }

    @Override
    public boolean onStart(String... strings) {
        return false;
    }
}
