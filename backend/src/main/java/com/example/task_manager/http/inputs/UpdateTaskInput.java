package com.example.task_manager.http.inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateTaskInput {
    private String title;
    private String description;
    private boolean finished;

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public boolean isFinished() {
        return this.finished;
    }
}
