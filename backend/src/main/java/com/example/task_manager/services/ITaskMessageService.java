package com.example.task_manager.services;

import com.example.task_manager.entities.TaskMessage;

public interface ITaskMessageService {
    void execute(TaskMessage taskMessage);
}
