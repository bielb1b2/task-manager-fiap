package com.example.task_info.services;

import com.example.task_info.entities.TaskMessage;

public interface IActionService {
    void execute(TaskMessage taskMessage);
}
