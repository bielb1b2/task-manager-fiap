package com.example.task_info.services;

import com.example.task_info.entities.TaskMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("FINISH")
public class FinishActionStrategy implements IActionStrategy {

    private final static Logger logger = LoggerFactory.getLogger(FinishActionStrategy.class);

    @Override
    public void execute(TaskMessage taskMessage) {
        logger.info("[CONSUMER - FINISH]: ", taskMessage.getTaskId());
    }
}
