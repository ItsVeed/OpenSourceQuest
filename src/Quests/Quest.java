package Quests;

import Tasks.Task;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.methods.IQuestAPI;
import com.epicbot.api.shared.util.time.Time;


import java.util.HashMap;

public class Quest {
    APIContext ctx;
    IQuestAPI.Quest quest;
    HashMap<String, Integer> requiredItems;

    public Quest(APIContext ctx) {this.ctx = ctx;}

    // Step management

    HashMap<Integer, Task> steps = new HashMap<>();

    public void addStep(int i, Task task) {steps.put(i, task);}

    // Execute steps

    public void main() {
        if (inCutscene()) {
            cutscene();
        } else {
            Task currentTask = steps.get(getStage(quest));
            currentTask.run();
        }
    }

    boolean gotItems = false;
    public void getItems() {
        for (String i : requiredItems.keySet()) {
            if (ctx.inventory().getCount(i) != requiredItems.get(i)) {
                withdraw(i, requiredItems.get(i) - ctx.inventory().getCount(i));
            }
        }
        if (ctx.inventory().containsAll(String.valueOf(requiredItems.keySet()))) {
            gotItems = true;
        }
    }

    // Item methods
    public void withdraw(String item, int amount) {
        if (ctx.bank().isReachable()) {
            ctx.camera();
            if (ctx.bank().isOpen()) {
                Time.sleep(200 - 50, 200 + 50);
                if (ctx.bank().contains(item)) {
                    ctx.bank().withdraw(amount, item);
                } else {
                    ctx.script().stop("You don't have " + item + " in your bank.");
                }
            } else if (!ctx.bank().isOpen()) {
                ctx.bank().open();
                Time.sleep(200, () -> ctx.bank().isOpen(), 1_000);
            }
        } else  if (!ctx.bank().isReachable()) {
            ctx.webWalking().walkToBank();
        }
    }

    // Stage methods
    private void cutscene() {
        if (ctx.dialogues().isDialogueOpen()) {
            if (ctx.dialogues().canContinue()) {
                ctx.dialogues().selectContinue();
            }
        }
    }

    private boolean inCutscene() {
        return ctx.vars().getVarbit(IQuestAPI.QuestVarbits.CUTSCENE.getId()) == 1;
    }

    private int getStage(IQuestAPI.Quest quest){
        if(quest.getVarPlayer() != null){
            return ctx.vars().getVarp(quest.getVarPlayer().getId());
        } else if(quest.getVarbit() != null){
            return ctx.vars().getVarbit(quest.getVarbit().getId());
        }
        return -1;
    }
}
