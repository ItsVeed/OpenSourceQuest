package Quests;

import Tasks.GetRequiredItems;
import Tasks.Talk;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.model.Area;

public class RomeoAndJuliet extends Quest{
    public RomeoAndJuliet(APIContext ctx) {
        super(ctx);

        name = "Romeo and juliet";

        requiredItems.put("Cadava berries", 1);

        setupSteps();
    }

    private void setupSteps() {
        Area varrockSquare = new Area(3206, 3435, 3220, 3422);
        Area balcony = new Area(1, 3156, 3426, 3160, 3425);
        Area church = new Area(3253, 3483, 3256, 3480);
        Area potionShop = new Area(3193, 3405, 3197, 3403);

        addStep(0, new GetRequiredItems(ctx, requiredItems));
        addStep(0, new Talk(ctx, 5037, new String[] {"Perhaps I could", "Yes", "Ok, thanks"}).setArea(varrockSquare));
        addStep(10, new Talk(ctx, 5035).setArea(balcony));
        addStep(20, new Talk(ctx, 5037, new String[] {"Ok, thanks"}).setArea(varrockSquare));
        addStep(30, new Talk(ctx, 5038).setArea(church));
        addStep(40, new Talk(ctx, 5036, new String[] {"Talk about something else.", "Talk about Romeo"}).setArea(potionShop));
        addStep(50, new Talk(ctx, 5035).setArea(balcony));
        addStep(60, new Talk(ctx, 5037).setArea(varrockSquare));
    }
}
