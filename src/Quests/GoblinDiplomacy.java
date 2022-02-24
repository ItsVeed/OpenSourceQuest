package Quests;

import Tasks.GetRequiredItems;
import Tasks.GiveItem;
import Tasks.InteractItem;
import Tasks.Talk;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.methods.IQuestAPI;
import com.epicbot.api.shared.model.Area;

import java.util.HashMap;

public class GoblinDiplomacy extends Quest{
    public GoblinDiplomacy(APIContext ctx) {
        super(ctx);

        quest = IQuestAPI.Quest.GOBLIN_DIPLOMACY;
        name = "Goblin diplomacy";

        this.requiredItems.put("Orange dye", 1);
        this.requiredItems.put("Goblin mail", 3);
        this.requiredItems.put("Blue dye", 1);

        setupSteps();
    }

    private void setupSteps() {
        Area General_Hut =(new Area(2956, 3513, 2959, 3510));

        HashMap<String, Integer> bypassItems = new HashMap<>();
        bypassItems.put("Orange goblin mail", 1);
        bypassItems.put("Goblin mail", 1);
        bypassItems.put("Blue goblin mail", 1);

        addPreStep(0, new GetRequiredItems(ctx, requiredItems).bypass(bypassItems));
        addPreStep(1, new InteractItem(ctx, 1767, "Use").combine(288));
        addPreStep(2, new InteractItem(ctx, 1769, "Use").combine(288));

        addStep(0, new Talk(ctx, 669, new String[] {"Yes, Wartface looks fat", "Do you want me to pick an armour colour for you?",
                "What about a different colour?"}).setArea(General_Hut));
        addStep(3, new GiveItem(ctx, 669, "Orange goblin mail").setArea(General_Hut));
        addStep(4, new GiveItem(ctx, 669, "Blue goblin mail").setArea(General_Hut));
        addStep(5, new GiveItem(ctx,669, "Goblin mail").setArea(General_Hut));
    }
}