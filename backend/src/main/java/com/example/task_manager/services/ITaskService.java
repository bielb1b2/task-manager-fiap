package com.example.task_manager.services;

import com.example.task_manager.http.inputs.CreateTaskInput;
import com.example.task_manager.http.inputs.UpdateTaskInput;
import com.example.task_manager.http.out.TaskOut;

import java.util.List;

public interface ITaskService {
    TaskOut createTask(CreateTaskInput createTaskInput);
    List<TaskOut> getAllTasksFromPersonId(String personId);
    TaskOut getOneTaskFromThePerson(String personId, String taskId);
    Void removeOneTaskFromThePerson(String personId, String taskId);
    TaskOut updateTask(String personId, String taskId, UpdateTaskInput taskInput);
    TaskOut finishTask(String personId, String taskId);
}
