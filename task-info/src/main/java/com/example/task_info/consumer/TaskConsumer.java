package com.example.task_info.consumer;

import com.example.task_info.entities.TaskMessage;
import com.example.task_info.services.ActionHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class TaskConsumer {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final ActionHandler actionHandler;

    public TaskConsumer(ActionHandler actionHandler) {
        this.actionHandler = actionHandler;
    }

    @RabbitListener(queues = "tasks")
    public void receiveMessage(String taskMessageString) {
        TaskMessage task = objectMapper.readValue(taskMessageString, TaskMessage.class);
        actionHandler.handle(task);
    }

}
