package com.example.task_manager.utils;

import com.example.task_manager.entities.Task;
import com.example.task_manager.http.inputs.UpdateTaskInput;
import com.example.task_manager.http.out.TaskOut;

public class TaskMapper {
    public static TaskOut toOut(Task task) {
        return new TaskOut.Builder()
                .uuid(task.getUuid())
                .title(task.getTitle())
                .description(task.getDescription())
                .finished(task.isFinished())
                .createdAt(task.getCreatedAt())
                .build();
    }

    public static Task toNewTask(Task task, UpdateTaskInput taskInput) {
        return new Task.Builder()
                .uuid(task.getUuid())
                .personId(task.getPersonId())
                .title(taskInput.getTitle())
                .description(taskInput.getDescription())
                .finished(taskInput.isFinished())
                .createdAt(task.getCreatedAt())
                .build();
    }
}
