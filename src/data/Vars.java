package data;

import Quests.GoblinDiplomacy;
import Quests.Quest;
import Quests.RomeoAndJuliet;
import Quests.XMarksTheSpot;
import com.epicbot.api.shared.APIContext;

public class Vars {
    public static Quest[] quests = null;
    public static Quest currentQuest = null;

    public static boolean start = false;

    public static void initialiseQuests(APIContext ctx) {
        Vars.quests = new Quest[]
                {
                        new RomeoAndJuliet(ctx),
                        new GoblinDiplomacy(ctx),
                        new XMarksTheSpot(ctx),
                };
    }
}
