package Tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.NPC;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.util.time.Time;

public class GiveItem extends Task{
    int npcId;
    String item;


    public GiveItem(APIContext ctx, int stage, int npcId, String item) {
        super(ctx, stage);

        this.npcId = npcId;
        this.item = item;
    }

    @Override public boolean run() {
        giveItem();
        if (ctx.inventory().contains(item)) {
            return false;
        } else {
            return true;
        }
    }

    private void giveItem() {
        NPC npc = ctx.npcs().query().id(npcId).reachable().results().nearest();
        if (npc != null) {
            if (!ctx.dialogues().isDialogueOpen()) {
                if (ctx.inventory().contains(item)) {
                    ctx.inventory().interactItem("Use", item);
                    npc.interact("Use");
                    Time.sleep(1_000, () -> ctx.dialogues().isDialogueOpen());
                }
            } else {
                ctx.dialogues().selectContinue();
                Time.sleep(100, () -> ctx.dialogues().isDialogueOpen());
            }
        } else {
            walk(location);
        }
    }
}
