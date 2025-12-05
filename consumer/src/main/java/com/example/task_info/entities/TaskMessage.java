package com.example.task_info.entities;

import com.example.task_info.utils.Action;

public class TaskMessage {
    private String personId;
    private String taskId;
    private Action action;

    public TaskMessage() {}

    public TaskMessage(String personId, String taskId, Action action) {
        this.personId = personId;
        this.taskId = taskId;
        this.action = action;
    }

    public String getTaskId() {
        return taskId;
    }

    public String getPersonId() {
        return personId;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }
}