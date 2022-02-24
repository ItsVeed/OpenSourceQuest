package Quests;

import Tasks.GetRequiredItems;
import Tasks.GiveItem;
import Tasks.InteractItem;
import Tasks.Talk;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.model.Area;

public class GoblinDiplomacy extends Quest{
    public GoblinDiplomacy(APIContext ctx) {
        super(ctx);

        name = "Goblin diplomacy";

        // Need to make a way to craft these using the steps
        requiredItems.put("Orange dye", 1);
        requiredItems.put("Goblin mail", 3);
        requiredItems.put("Blue dye", 1);

        setupSteps();
    }

    private void setupSteps() {
        Area General_Hut =(new Area(2956, 3513, 2959, 3510));

        addStep(0, new GetRequiredItems(ctx, requiredItems));
        addStep(0, new InteractItem(ctx, 1767, "Use").combine(288));
        addStep(0, new InteractItem(ctx, 1769, "Use").combine(288));
        addStep(0, new Talk(ctx, 669, new String[] {"Yes, Wartface looks fat", "Do you want me to pick an armour colour for you?",
                "What about a different colour?"}).setArea(General_Hut));
        addStep(3, new GiveItem(ctx, 669, "Orange goblin mail").setArea(General_Hut));
        addStep(4, new GiveItem(ctx, 669, "Blue goblin mail").setArea(General_Hut));
        addStep(5, new GiveItem(ctx,669, "Goblin mail").setArea(General_Hut));
    }
}