package com.example.task_manager.services;

import ch.qos.logback.core.util.StringUtil;
import com.example.task_manager.entities.Task;
import com.example.task_manager.exceptions.NotFoundException;
import com.example.task_manager.exceptions.ValidateException;
import com.example.task_manager.http.inputs.CreateTaskInput;
import com.example.task_manager.http.inputs.UpdateTaskInput;
import com.example.task_manager.http.out.TaskOut;
import com.example.task_manager.repository.ITaskRepository;
import com.example.task_manager.utils.TaskMapper;
import com.example.task_manager.validators.CreateTaskValidate;
import com.example.task_manager.validators.OneTaskValidate;
import com.example.task_manager.validators.UpdateTaskValidate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TaskService implements ITaskService {

    private final ITaskRepository taskRepository;

    @Autowired
    public TaskService(ITaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public TaskOut createTask(CreateTaskInput createTaskInput) {
        CreateTaskValidate.validate(createTaskInput);

        String createdAt = LocalDateTime.now().withSecond(0).withNano(0).toString();
        Task task = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(createTaskInput.getPersonId())
                .title(createTaskInput.getTitle())
                .description(createTaskInput.getDescription())
                .finished(false)
                .createdAt(createdAt)
                .build();

        Task createdTask = taskRepository.save(task);

        return TaskMapper.toOut(task);
    }

    @Override
    public List<TaskOut> getAllTasksFromPersonId(String personId) {
        if(StringUtil.isNullOrEmpty(personId)) {
            throw new ValidateException("Person ID cannot be null/empty");
        }

        return taskRepository.getAllFromPersonId(personId)
                .stream()
                .map(TaskMapper::toOut)
                .toList();
    }

    @Override
    public TaskOut getOneTaskFromThePerson(String personId, String taskId) {
        OneTaskValidate.validate(personId, taskId);
        Optional<Task> task = taskRepository.getTask(personId, taskId);
        Task result = task.orElseThrow(() -> new NotFoundException("Task not found"));
        return TaskMapper.toOut(result);
    }

    @Override
    public Void removeOneTaskFromThePerson(String personId, String taskId) {
        OneTaskValidate.validate(personId, taskId);
        Optional<Task> taskRemoved = taskRepository.removeTask(personId, taskId);
        if(taskRemoved.isEmpty()) {
            throw new NotFoundException("Task not found");
        }
        return null;
    }

    @Override
    public TaskOut updateTask(String personId, String taskId, UpdateTaskInput taskInput) {
        UpdateTaskValidate.validate(personId, taskId, taskInput);
        Optional<Task> task = taskRepository.getTask(personId, taskId);
        Task result = task.orElseThrow(() -> new NotFoundException("Task not found"));

        Task taskUpdatedMapper = TaskMapper.toNewTask(result, taskInput);
        Optional<Task> taskUpdated = this.taskRepository.update(taskUpdatedMapper);
        Task newTask = taskUpdated.orElseThrow(() -> new NotFoundException("Task not found"));
        return TaskMapper.toOut(newTask);
    }

    @Override
    public TaskOut finishTask(String personId, String taskId) {
        OneTaskValidate.validate(personId, taskId);
        Optional<Task> task = taskRepository.getTask(personId, taskId);
        Task result = task.orElseThrow(() -> new NotFoundException("Task not found"));
        result.toggleFinish();

        Optional<Task> taskUpdated = this.taskRepository.update(result);
        Task newTask = taskUpdated.orElseThrow(() -> new NotFoundException("Task not found"));
        return TaskMapper.toOut(newTask);
    }
}
