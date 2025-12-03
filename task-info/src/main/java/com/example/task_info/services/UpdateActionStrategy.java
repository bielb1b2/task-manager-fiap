package com.example.task_info.services;

import com.example.task_info.entities.TaskMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("UPDATE")
public class UpdateActionStrategy implements IActionStrategy {

    private final static Logger logger = LoggerFactory.getLogger(UpdateActionStrategy.class);

    @Override
    public void execute(TaskMessage taskMessage) {
        logger.info("[CONSUMER - UPDATE]: ", taskMessage.getTaskId());
    }
}
