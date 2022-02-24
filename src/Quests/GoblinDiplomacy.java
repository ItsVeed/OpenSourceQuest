package Quests;

import Tasks.GiveItem;
import Tasks.Talk;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.model.Area;

public class GoblinDiplomacy extends Quest{
    public GoblinDiplomacy(APIContext ctx) {
        super(ctx);

        name = "Goblin diplomacy";

        // Need to make a way to craft these using the steps
        requiredItems.put("Orange goblin mail", 1);
        requiredItems.put("Goblin mail", 1);
        requiredItems.put("Blue goblin mail", 1);

        setupSteps();
    }

    private void setupSteps() {
        Area General_Hut =(new Area(2956, 3513, 2959, 3510));

        addStep(0, new Talk(ctx, 669,General_Hut, new String[] {"Yes, Wartface looks fat", "Do you want me to pick an armour colour for you?",
                "What about a different colour?"}));
        addStep(3, new GiveItem(ctx, General_Hut, 669, "Orange goblin mail"));
        addStep(4, new GiveItem(ctx, General_Hut, 669, "Blue goblin mail"));
        addStep(5, new GiveItem(ctx, General_Hut, 669, "Goblin mail"));
    }
}