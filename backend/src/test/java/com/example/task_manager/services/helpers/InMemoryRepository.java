package com.example.task_manager.services.helpers;

import com.example.task_manager.entities.Task;
import com.example.task_manager.repository.ITaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryRepository implements ITaskRepository {
    public final List<Task> tasks = new ArrayList<>();

    @Override
    public Task save(Task task) {
        if(task == null) {
            throw new RuntimeException("Task cannot be null");
        }
        this.tasks.add(task);
        return task;
    }

    @Override
    public List<Task> getAllFromPersonId(String personId) {
        if(personId == null) {
            return List.of();
        }

        return tasks.stream().filter(
                item -> Objects.equals(item.getPersonId(), personId)
        ).collect(Collectors.toList());
    }

    @Override
    public Optional<Task> getTask(String personId, String taskId) {
        return tasks.stream().filter(item ->
                Objects.equals(item.getPersonId(), personId) &&
                        Objects.equals(item.getUuid(), taskId)
        ).findFirst();
    }

    @Override
    public Optional<Task> removeTask(String personId, String taskId) {

        Optional<Task> toRemove = tasks.stream()
                .filter(task ->
                        Objects.equals(task.getPersonId(), personId) &&
                                Objects.equals(task.getUuid(), taskId)
                )
                .findFirst();

        toRemove.ifPresent(tasks::remove);

        return toRemove;
    }

    @Override
    public Optional<Task> update(Task task) {
        boolean removed = tasks.removeIf(item ->
                Objects.equals(item.getPersonId(), task.getPersonId()) &&
                        Objects.equals(item.getUuid(), task.getUuid())
        );

        if (!removed) {
            return Optional.empty();
        }

        tasks.add(task);
        return Optional.of(task);
    }
}
