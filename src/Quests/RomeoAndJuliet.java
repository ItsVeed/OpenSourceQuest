package Quests;

import Tasks.Talk;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.model.Area;

public class RomeoAndJuliet extends Quest{
    public RomeoAndJuliet(APIContext ctx) {
        super(ctx);

        requiredItems.put("Cadava berries", 1);

        setupSteps();
    }

    private void setupSteps() {
        Area varrockSquare = new Area(3206, 3435, 3220, 3422);
        Area balcony = new Area(1, 3156, 3426, 3160, 3425);
        Area church = new Area(3253, 3483, 3256, 3480);
        Area potionShop = new Area(3193, 3405, 3197, 3403);

        addStep(0, new Talk(ctx, 5037, varrockSquare, new String[] {"Perhaps I could", "Yes", "Ok, thanks"}));
        addStep(10, new Talk(ctx, 5035, balcony));
        addStep(20, new Talk(ctx, 5037, varrockSquare, new String[] {"Ok, thanks"}));
        addStep(30, new Talk(ctx, 5038, church));
        addStep(40, new Talk(ctx, 5036, potionShop, new String[] {"Talk about something else.", "Talk about Romeo"}));
        addStep(50, new Talk(ctx, 5035, balcony));
        addStep(60, new Talk(ctx, 5037, varrockSquare));
    }
}
