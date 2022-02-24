package Quests;

import Tasks.GetRequiredItems;
import com.epicbot.api.shared.APIContext;

public class CooksAssistant extends Quest{
    public CooksAssistant(APIContext ctx) {
        super(ctx);

        requiredItems.put("Egg", 1);
        requiredItems.put("Pot of flour", 1);
        requiredItems.put("Bucket of milk", 1);

        setupSteps();
    }

    private void setupSteps() {

        addStep(0, new GetRequiredItems(ctx, requiredItems));
    }
}
