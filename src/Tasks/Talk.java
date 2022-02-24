package Tasks;

import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.entity.NPC;
import com.epicbot.api.shared.entity.WidgetChild;
import com.epicbot.api.shared.model.Area;
import com.epicbot.api.shared.util.time.Time;

public class Talk extends Task{

    // Task vars

    int id;
    String[] chatOptions;

    // End

    // Constuctor

    public Talk(APIContext ctx, int id, String[] chatOptions) {
        super(ctx);
        this.id = id;
        this.location = location;
        this.chatOptions = chatOptions;
    }

    //Overload
    public Talk(APIContext ctx, int id) {
        super(ctx);
        new Talk(ctx, id, new String[] {});
    }

    // End

    // Main method

    @Override
    public boolean run() {
        talkTo(id, location, chatOptions);
        return true;
    }

    // End

    // Dialogue methods

    public void talkTo(int id, Area location, String[] chatOptions) {
        if (ctx.dialogues().isDialogueOpen()) {
            if (ctx.dialogues().canContinue()) {
                ctx.dialogues().selectContinue();
                Time.sleep(1_000, () -> ctx.dialogues().isDialogueOpen(), 4_000);
            }
            if (ctx.dialogues().getOptions() != null) {
                handleOptions(chatOptions);
                Time.sleep(100);
            }
            return;
        }
        if (!ctx.npcs().query().id(id).results().isEmpty()) {
            NPC n = ctx.npcs().query().id(id).results().nearest();
            if (n != null) {
                if (!n.isVisible()) {
                    ctx.camera().turnTo(n);
                }
                n.interact("Talk-to");
                Time.sleep(1_000, () -> ctx.dialogues().isDialogueOpen(), 1_000);
            }
        } else {
            ctx.webWalking().walkTo(location.getCentralTile());
        }
    }

    private void handleOptions(String[] chatOptions){
        String bestOption = getBestDialogOption(chatOptions);
        if(bestOption != null) {
            ctx.dialogues().selectOption(bestOption);
        }
    }

    protected String getBestDialogOption(String[] dialogOptions){
        for(String chat : dialogOptions){
            for(WidgetChild option : ctx.dialogues().getOptions()){
                if(option.getText().contains(chat)){
                    return chat;
                }
            }
        }
        return null;
    }

    // End
}
