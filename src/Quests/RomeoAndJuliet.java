package Quests;

import Tasks.GetRequiredItems;
import Tasks.Talk;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.methods.IQuestAPI;
import com.epicbot.api.shared.model.Area;

import java.util.HashMap;

public class RomeoAndJuliet extends Quest{
    public RomeoAndJuliet(APIContext ctx) {
        super(ctx);

        quest = IQuestAPI.Quest.ROMEO_AND_JULIET;
        name = "Romeo and juliet";

        setupSteps();
    }

    private void setupSteps() {
        Area varrockSquare = new Area(3206, 3435, 3220, 3422);
        Area balcony = new Area(1, 3156, 3426, 3160, 3425);
        Area church = new Area(3253, 3483, 3256, 3480);
        Area potionShop = new Area(3193, 3405, 3197, 3403);

        HashMap<String, Integer> items = new HashMap<>();
        items.put("Cadava berries", 1);

        addStep(new Talk(ctx, 0, 5037, new String[] {"Perhaps I could", "Yes", "Ok, thanks"}).setArea(varrockSquare).setRequiredItems(items));
        addStep( new Talk(ctx, 10, 5035).setArea(balcony));
        addStep( new Talk(ctx, 20, 5037, new String[] {"Ok, thanks"}).setArea(varrockSquare));
        addStep(new Talk(ctx, 30, 5038).setArea(church));
        addStep(new Talk(ctx, 40, 5036, new String[] {"Talk about something else.", "Talk about Romeo"}).setArea(potionShop).setRequiredItems(items));
        addStep(new Talk(ctx, 50, 5035).setArea(balcony));
        addStep(new Talk(ctx, 60, 5037).setArea(varrockSquare));
    }
}
