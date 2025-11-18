package com.example.task_manager.http.inputs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateTaskInput {
    private String personId;
    private String title;
    private String description;

    public String getPersonId() {
        return personId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
