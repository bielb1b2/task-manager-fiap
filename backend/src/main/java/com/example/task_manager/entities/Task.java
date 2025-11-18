package com.example.task_manager.entities;

public class Task {

    private String uuid;
    private String personId;
    private String title;
    private String description;
    private boolean finished;
    private String createdAt;

    private Task(Builder builder) {
        this.uuid = builder.uuid;
        this.personId = builder.personId;
        this.title = builder.title;
        this.description = builder.description;
        this.finished = builder.finished;
        this.createdAt = builder.createdAt;
    }

    public static class Builder {
        private String uuid;
        private String personId;
        private String title;
        private String description;
        private boolean finished;
        private String createdAt;

        public Builder uuid(String uuid) {
            this.uuid = uuid;
            return this;
        }

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

        public Builder finished(boolean finished) {
            this.finished = finished;
            return this;
        }

        public Builder createdAt(String createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public Task build() {
            return new Task(this);
        }
    }

    public String getUuid() {
        return this.uuid;
    }

    public String getPersonId() {
        return this.personId;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void toggleFinish() {
        this.finished = !this.finished;
    }

    public String getCreatedAt() {
        return this.createdAt;
    }
}
