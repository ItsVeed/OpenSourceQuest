package Quests;

import Tasks.Talk;
import Tasks.Task;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.methods.IQuestAPI;
import com.epicbot.api.shared.model.Tile;

import java.util.HashMap;

public class TheKnightsSword extends Quest{
    public TheKnightsSword(APIContext ctx) {
        super(ctx);

        quest = IQuestAPI.Quest.THE_KNIGHTS_SWORD;
        name = "The knights sword";

        setupSteps();
    }

    private void setupSteps() {
        Tile Squire = new Tile(2978, 3341, 0);

        HashMap<String, Integer> items = new HashMap<>();
        items.put("Redberry pie", 1);
        items.put("Iron bar", 2);
        items.put("Blurite ore", 1);
        items.put("Bronze pickaxe", 1);

        addStep(new Talk(ctx, 0, 4737, new String[] {"And how is life as a squire?", "I can make a new sword if you like...", "So would these dwarves make another one?", "Ok, I'll give it a go."}).setTile(Squire).setRequiredItems(items));
    }
}
