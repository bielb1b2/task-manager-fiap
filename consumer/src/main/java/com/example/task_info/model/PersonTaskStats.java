package com.example.task_info.model;

import java.time.LocalDateTime;

public class PersonTaskStats {
    private String personId;
    private Integer tasksCreated;
    private Integer tasksCompleted;
    private Integer tasksDeleted;
    private LocalDateTime updatedAt;

    public PersonTaskStats() {}

    public PersonTaskStats(String personId, Integer tasksCreated, Integer tasksCompleted, Integer tasksDeleted, LocalDateTime updatedAt) {
        this.personId = personId;
        this.tasksCreated = tasksCreated;
        this.tasksCompleted = tasksCompleted;
        this.tasksDeleted = tasksDeleted;
        this.updatedAt = updatedAt;
    }

    // Getters e Setters
    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public Integer getTasksCreated() {
        return tasksCreated;
    }

    public void setTasksCreated(Integer tasksCreated) {
        this.tasksCreated = tasksCreated;
    }

    public Integer getTasksCompleted() {
        return tasksCompleted;
    }

    public void setTasksCompleted(Integer tasksCompleted) {
        this.tasksCompleted = tasksCompleted;
    }

    public Integer getTasksDeleted() {
        return tasksDeleted;
    }

    public void setTasksDeleted(Integer tasksDeleted) {
        this.tasksDeleted = tasksDeleted;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
