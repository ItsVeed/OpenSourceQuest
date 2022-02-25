package Quests;

import Tasks.Task;
import com.epicbot.api.shared.APIContext;
import com.epicbot.api.shared.methods.IQuestAPI;
import data.Vars;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class Quest {
    // Context
    APIContext ctx;
    // End

    // Quest info
    IQuestAPI.Quest quest;
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


//    HashMap<Integer, Task> steps = new HashMap<>();
    List<Task> steps = new ArrayList();

    public void addStep(Task task) {steps.add(task);}
    // End

    // Execute steps

    boolean init = true;
    public void main() {
        if (inCutscene()) {
            cutscene();
        } else if (ctx.quests().isCompleted(quest)) {
            setDoQuest(false);
            Vars.currentQuest = null;
        } else {
            Task currentTask = steps.get(0);
            System.out.println(currentTask);
            if (currentTask.stageCheck) {
                if (currentTask.stage < getStage(quest)) {
                    steps.remove(0);
                } else {
                    if (currentTask.main()) {
                        steps.remove(0);
                    }
                }
            }

        }
    }
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
