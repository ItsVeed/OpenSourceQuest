package Quests;

import Tasks.GetRequiredItems;
import Tasks.Task;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.methods.IQuestAPI;
import com.epicbot.api.shared.util.time.Time;
import data.Vars;


import java.util.HashMap;

public class Quest {
    // Context
    APIContext ctx;
    // End

    // Quest info
    IQuestAPI.Quest quest;
    HashMap<String, Integer> requiredItems = new HashMap<>();
    // End

    // Public vars
    public boolean doQuest = false;
    public String name;
    // End

    public boolean getDoQuest() {
        return doQuest;
    }

    public void setDoQuest(boolean x) {
        doQuest = x;
    }

    // Constructor
    public Quest(APIContext ctx) {
        this.ctx = ctx;
    }
    // End

    // Step management

    HashMap<Integer, Task> steps = new HashMap<>();

    public void addStep(int i, Task task) {steps.put(i, task);}
    // End

    // Execute steps

    public void main() {
        if (inCutscene()) {
            cutscene();
        } else if (ctx.quests().isCompleted(quest)) {
            setDoQuest(false);
            Vars.currentQuest = null;
        } else {
            int currentKey = getStage(quest);
            Task currentTask = steps.get(getStage(quest));
            if (currentTask.run()) {
                steps.remove(currentKey);
            }
        }
    }
    // End

    // End

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
