package com.example.task_info.services;

import com.example.task_info.entities.TaskMessage;

public interface IActionStrategy {
    void execute(TaskMessage taskMessage);
}
