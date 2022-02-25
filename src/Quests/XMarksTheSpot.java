package Quests;

import Tasks.InteractItem;
import Tasks.PickupItem;
import Tasks.Talk;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.methods.IQuestAPI;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;

public class XMarksTheSpot extends Quest{
    public XMarksTheSpot(APIContext ctx) {
        super(ctx);

        quest = IQuestAPI.Quest.X_MARKS_THE_SPOT;
        name = "X marks the spot";

        setupSteps();
    }

    private void setupSteps() {
        Area spadeLocation = new Area(3120, 3360, 3123, 3357);
        Area lumbridgePub = new Area(3228, 3241, 3232, 3239);
        Tile firstSpot = new Tile(3230, 3209, 0);
        Tile secondSpot = new Tile(3203, 3212, 0);
        Tile thirdSpot = new Tile(3108, 3264, 0);
        Tile fourthSpot = new Tile(3077, 3261, 0);
        Tile dock = new Tile(3054, 3247, 0);

        addStep(new PickupItem(ctx, 0, spadeLocation, 952));
        addStep(new Talk(ctx, 0, 8484, new String[] {"I'm looking for a quest.", "Yes."}).setArea(lumbridgePub));
        addStep(new Talk(ctx, 1, 8484).setArea(lumbridgePub));
        addStep(new InteractItem(ctx , 2,952, "Dig").setTile(firstSpot));
        addStep(new InteractItem(ctx , 3, 952, "Dig").setTile(secondSpot));
        addStep(new InteractItem(ctx , 4, 952, "Dig").setTile(thirdSpot));
        addStep(new InteractItem(ctx , 5,952, "Dig").setTile(fourthSpot));
        addStep(new Talk(ctx, 6, 8484).setTile(dock));
        addStep(new Talk(ctx, 7, 8484).setTile(dock));

    }
}
