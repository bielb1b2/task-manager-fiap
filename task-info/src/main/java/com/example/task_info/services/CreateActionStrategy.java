package com.example.task_info.services;

import com.example.task_info.entities.TaskMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service("CREATE")
public class CreateActionStrategy implements IActionStrategy {

    private final static Logger logger = LoggerFactory.getLogger(CreateActionStrategy.class);

    @Override
    public void execute(TaskMessage taskMessage) {
        logger.info("[CONSUMER - CREATE]: ", taskMessage.getTaskId());
    }
}
