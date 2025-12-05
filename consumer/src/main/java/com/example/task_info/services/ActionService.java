package com.example.task_info.services;

import com.example.task_info.entities.TaskMessage;
import com.example.task_info.repository.TaskRepository;
import org.springframework.stereotype.Service;

@Service
public class ActionService implements IActionService {

    private final TaskRepository repository;

    public ActionService(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(TaskMessage taskMessage) {
        repository.incrementCounter(taskMessage.getPersonId(), taskMessage.getAction());
    }
}
