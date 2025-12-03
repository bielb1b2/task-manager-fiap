package com.example.task_info.services;

import com.example.task_info.entities.TaskMessage;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ActionHandler {

    private final Map<String, IActionStrategy> strategies;

    public ActionHandler(Map<String, IActionStrategy> strategies) {
        this.strategies = strategies;
    }

    public void handle(TaskMessage message) {
        IActionStrategy strategy = strategies.get(message.getAction().getKey().toUpperCase());

        if(strategy == null) {
            throw new IllegalArgumentException("Action inválida: " + message.getAction().getKey());
        }

        strategy.execute(message);
    }
}
