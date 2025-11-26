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

    public CreateTaskInput() {}

    private CreateTaskInput(Builder builder) {
        this.personId = builder.personId;
        this.title = builder.title;
        this.description = builder.description;
    }

    public static class Builder {
        private String personId;
        private String title;
        private String description;

        public Builder personId(String personId) {
            this.personId = personId;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public CreateTaskInput build() {
            return new CreateTaskInput(this);
        }
    }
}
