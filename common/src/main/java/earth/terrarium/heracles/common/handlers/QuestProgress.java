package earth.terrarium.heracles.common.handlers;

import earth.terrarium.heracles.api.Quest;
import earth.terrarium.heracles.api.tasks.QuestTask;

import java.util.HashMap;
import java.util.Map;

public class QuestProgress {

    private final Map<String, TaskProgress> tasks = new HashMap<>();
    private boolean complete;
    private boolean claimed;

    public QuestProgress() {
        this.complete = false;
        this.claimed = false;
    }

    public QuestProgress(boolean complete, boolean claimed, Map<String, TaskProgress> tasks) {
        this.complete = complete;
        this.claimed = claimed;
        this.tasks.putAll(tasks);
    }

    public void update(Quest quest) {
        if (complete) return;
        for (QuestTask<?, ?> task : quest.tasks()) {
            if (!tasks.containsKey(task.id())) {
                tasks.put(task.id(), new TaskProgress());
            }
        }
        for (TaskProgress task : tasks.values()) {
            if (!task.isComplete()) {
                return;
            }
        }
        complete = true;
    }

    public boolean isComplete() {
        return complete;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void claim() {
        if (!complete) return;
        this.claimed = true;
    }

    public TaskProgress getTask(String id) {
        return this.tasks.computeIfAbsent(id, s -> new TaskProgress());
    }

    public Map<String, TaskProgress> tasks() {
        return this.tasks;
    }
}