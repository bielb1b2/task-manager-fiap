package com.example.task_manager.validators;

import ch.qos.logback.core.util.StringUtil;
import com.example.task_manager.exceptions.ValidateException;
import com.example.task_manager.http.inputs.UpdateTaskInput;

public class UpdateTaskValidate {

    public static void validate(
            String personId,
            String taskId,
            UpdateTaskInput updateTaskInput
    ) {
        if (StringUtil.isNullOrEmpty(updateTaskInput.getTitle())) {
            throw new ValidateException("Title cannot be empty");
        }

        if (updateTaskInput.getTitle().length() > 100) {
            throw new ValidateException("Title must be at most 100 characters long");
        }

        if (updateTaskInput.getDescription().length() > 10_000) {
            throw new ValidateException("Description must be at most 10_000 characters long");
        }

        if (StringUtil.isNullOrEmpty(personId)) {
            throw new ValidateException("User cannot be empty");
        }

        if (StringUtil.isNullOrEmpty(taskId)) {
            throw new ValidateException("Task ID cannot be empty");
        }
    }
}
