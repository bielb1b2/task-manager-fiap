package com.example.task_manager.repository;

import com.example.task_manager.entities.Task;

import java.util.List;
import java.util.Optional;

public interface ITaskRepository {
    Task save(Task task);
    List<Task> getAllFromPersonId(String personId);
    Optional<Task> getTask(String personId, String taskId);
    Optional<Task> removeTask(String personId, String taskId);
    Optional<Task> update(Task task);
}
