package com.example.task_manager.entities;

import com.example.task_manager.utils.Action;

public class TaskMessage {
    private String personId;
    private String taskId;
    private Action action;

    private TaskMessage(String personId, String taskId, Action action) {
        this.personId = personId;
        this.taskId = taskId;
        this.action = action;
    }

    public TaskMessage() {}

    public static TaskMessage create(String personId, String taskId, Action action) {
        return new TaskMessage(personId, taskId, action);
    }

    public String getPersonId() {
        return personId;
    }

    public String getTaskId() {
        return taskId;
    }

    public Action getAction() {
        return action;
    }
}
