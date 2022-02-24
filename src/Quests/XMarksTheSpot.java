package Quests;

import Tasks.InteractItem;
import Tasks.PickupItem;
import Tasks.Talk;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;

public class XMarksTheSpot extends Quest{
    public XMarksTheSpot(APIContext ctx) {
        super(ctx);

        name = "X marks the spot";

    }

    private void setupSteps() {
        Area spadeLocation = new Area(3120, 3360, 3123, 3357);
        Area lumbridgePub = new Area(3228, 3241, 3232, 3239);
        Tile firstSpot = new Tile(3230, 3209, 0);
        Tile secondSpot = new Tile(3203, 3212, 0);
        Tile thirdSpot = new Tile(3108, 3264, 0);
        Tile fourthSpot = new Tile(3077, 3260, 0);
        Area dock = new Area(3051, 3248, 3055, 3245);

        addStep(0, new PickupItem(ctx, spadeLocation, 952));
        addStep(0, new Talk(ctx, 8484, new String[] {"I'm looking for a quest.", "Yes."}).setArea(lumbridgePub));
        addStep(1, new Talk(ctx, 8484).setArea(lumbridgePub));
        addStep(2, new InteractItem(ctx ,952, "Dig").setTile(firstSpot));
        addStep(3, new InteractItem(ctx ,952, "Dig").setTile(secondSpot));
        addStep(4, new InteractItem(ctx ,952, "Dig").setTile(thirdSpot));
        addStep(5, new InteractItem(ctx ,952, "Dig").setTile(fourthSpot));
        addStep(6, new Talk(ctx, 8484).setArea(dock));
        addStep(7, new Talk(ctx, 8484).setArea(dock));

    }
}
