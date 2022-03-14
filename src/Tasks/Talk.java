package Tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.NPC;
import com.epicbot.api.shared.entity.WidgetChild;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.model.Tile;
import com.epicbot.api.shared.util.time.Time;

public class Talk extends Task{

    // Task vars

    int id;
    String[] chatOptions;

    // End

    // Constructor

    public Talk(APIContext ctx, int stage, int id, String[] chatOptions) {
        super(ctx, stage);
        this.id = id;
        this.chatOptions = chatOptions;
    }

    //Overload
    public Talk(APIContext ctx, int stage, int id) {
        super(ctx, stage);
        this.id = id;
        this.chatOptions = new String[] {};
    }

    // End

    // Main method

    @Override
    public boolean run() {
        if (tile != null) {
            talkTo(id, tile);
        } else if (location != null) {
            talkTo(id, location);
        }
        return false;
    }

    // End

    // Dialogue methods

    public void talk(NPC n) {
        if (ctx.dialogues().isDialogueOpen()) {
            if (ctx.dialogues().canContinue()) {
                ctx.dialogues().selectContinue();
                Time.sleep(10_000, () -> ctx.dialogues().isDialogueOpen(), 4_000);
            }
            if (ctx.dialogues().getOptions() != null) {
                handleOptions(chatOptions);
                Time.sleep(1_000);
            }
        } else {
            if (!n.isVisible()) {
                ctx.camera().turnTo(n);
            }
            n.interact("Talk-to");
            Time.sleep(10_000, () -> ctx.dialogues().isDialogueOpen(), 1_000);
        }
    }

    public void talkTo(int id, Area location) {
        NPC n = ctx.npcs().query().id(id).results().nearest();
        if (n != null) {
            talk(n);
        } else {
            walk(location);
        }
    }

    public void talkTo(int id, Tile tile) {
        NPC n = ctx.npcs().query().id(id).results().nearest();
        if (n != null) {
            talk(n);
        } else {
            walk(tile);
        }
    }

    private void handleOptions(String[] chatOptions){
        String bestOption = getBestDialogOption(chatOptions);
        if(bestOption != null) {
            ctx.dialogues().selectOption(bestOption);
            Time.sleep(5_000, () -> !ctx.dialogues().isLoading() || !ctx.dialogues().isDialogueOpen());
        }
    }

    protected String getBestDialogOption(String[] dialogOptions){
        for(String chat : dialogOptions){
            for(WidgetChild option : ctx.dialogues().getOptions()){
                if(option.getText().equals(chat)){
                    return chat;
                }
            }
        }
        return null;
    }

    // End
}
