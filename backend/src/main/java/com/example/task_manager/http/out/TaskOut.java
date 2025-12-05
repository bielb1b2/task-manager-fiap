package com.example.task_manager.http.out;

public class TaskOut {

    private String uuid;
    private String title;
    private String description;
    private boolean finished;
    private String createdAt;

    public TaskOut() {}

    private TaskOut(Builder builder) {
        this.uuid = builder.uuid;
        this.title = builder.title;
        this.description = builder.description;
        this.finished = builder.finished;
        this.createdAt = builder.createdAt;
    }

    // Getters e Setters
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    // -----------------------
    // Builder
    // -----------------------
    public static class Builder {
        private String uuid;
        private String title;
        private String description;
        private boolean finished;
        private String createdAt;

        public Builder uuid(String uuid) {
            this.uuid = uuid;
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

        public Builder finished(boolean finished) {
            this.finished = finished;
            return this;
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public TaskOut build() {
            return new TaskOut(this);
        }
    }
}
