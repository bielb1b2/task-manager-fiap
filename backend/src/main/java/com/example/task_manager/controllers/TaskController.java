package com.example.task_manager.controllers;

import com.example.task_manager.http.inputs.CreateTaskInput;
import com.example.task_manager.http.inputs.UpdateTaskInput;
import com.example.task_manager.http.out.TaskOut;
import com.example.task_manager.services.ITaskService;
import com.example.task_manager.services.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final ITaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskOut> createNewTask(
            @RequestBody CreateTaskInput request
    ) {
        TaskOut createTaskOut = taskService.createTask(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(createTaskOut);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<List<TaskOut>> getAllTasks(
            @PathVariable String personId
    ) {
        List<TaskOut> tasks = taskService.getAllTasksFromPersonId(personId);
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{personId}/{taskId}")
    public ResponseEntity<TaskOut> getTask(
            @PathVariable String personId,
            @PathVariable String taskId
    ) {
        TaskOut task = taskService.getOneTaskFromThePerson(personId, taskId);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{personId}/{taskId}")
    public ResponseEntity<Void> removeTask(
            @PathVariable String personId,
            @PathVariable String taskId
    ) {
        taskService.removeOneTaskFromThePerson(personId, taskId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{personId}/{taskId}")
    public ResponseEntity<TaskOut> updateTask(
            @PathVariable String personId,
            @PathVariable String taskId,
            @RequestBody UpdateTaskInput taskInput
    ) {
        TaskOut response = taskService.updateTask(personId, taskId, taskInput);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{personId}/{taskId}/finish")
    public ResponseEntity<TaskOut> finishTask(
            @PathVariable String personId,
            @PathVariable String taskId
    ) {
        TaskOut response = taskService.finishTask(personId, taskId);
        return ResponseEntity.ok(response);
    }
}
