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

    public static class Builder {
        private String title;
        private String description;
        private boolean finished;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder description(String description) {
            this.description = description;
            return this;
        }

        public Builder finished(boolean finished) {
            this.finished = finished;
            return this;
        }

        public UpdateTaskInput build() {
            UpdateTaskInput input = new UpdateTaskInput();
            input.title = this.title;
            input.description = this.description;
            input.finished = this.finished;
            return input;
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}
