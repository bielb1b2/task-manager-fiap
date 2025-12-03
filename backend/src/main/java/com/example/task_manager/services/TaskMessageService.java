package com.example.task_manager.services;

import com.example.task_manager.config.RabbitMQConfig;
import com.example.task_manager.entities.TaskMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class TaskMessageService implements ITaskMessageService {

    private static final Logger logger = LoggerFactory.getLogger(TaskMessageService.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public TaskMessageService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void execute(TaskMessage taskMessage) {
        try {
            String convertedValue = objectMapper.writeValueAsString(taskMessage);
            logger.info("Send message: {}", convertedValue);
            rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, convertedValue);
            logger.info("Message sent ID: {}", taskMessage.getTaskId());
        } catch (Exception e) {
            logger.error("Fail to send message: ", e);
        }
    }
}
