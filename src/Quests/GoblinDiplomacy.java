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

        setupSteps();
    }

    private void setupSteps() {
        Area General_Hut =(new Area(2956, 3513, 2959, 3510));

        HashMap<String, Integer> items = new HashMap<>();
        items.put("Blue dye", 1);
        items.put("Goblin mail", 3);
        items.put("Orange dye", 1);

        HashMap<String, Integer> items2 = new HashMap<>();
        items.put("Goblin mail", 2);
        items.put("Orange dye", 1);

        HashMap<String, Integer> items3 = new HashMap<>();
        items.put("Orange goblin mail", 1);
        items.put("Goblin mail", 1);
        items.put("Blue goblin mail", 1);

        HashMap<String, Integer> items4 = new HashMap<>();
        items.put("Goblin mail", 1);
        items.put("Blue goblin mail", 1);

        HashMap<String, Integer> items5 = new HashMap<>();
        items.put("Blue goblin mail", 1);

        addStep( new InteractItem(ctx, 0, 1767, "Use").combine(288).setRequiredItems(items));
        addStep( new InteractItem(ctx, 0, 1769, "Use").combine(288).setRequiredItems(items2));
        addStep( new Talk(ctx, 0, 669, new String[] {"So how is life for the goblins?", "Yes, Wartface looks fat", "Do you want me to pick an armour colour for you?",
                "What about a different colour?", "I have some orange armour here"}).setArea(General_Hut).setRequiredItems(items3));
        addStep( new GiveItem(ctx, 3, 669, "Orange goblin mail").setArea(General_Hut).setRequiredItems(items3));
        addStep( new GiveItem(ctx, 4, 669, "Blue goblin mail").setArea(General_Hut).setRequiredItems(items4));
        addStep( new GiveItem(ctx, 5,669, "Goblin mail").setArea(General_Hut).setRequiredItems(items5));
    }
}