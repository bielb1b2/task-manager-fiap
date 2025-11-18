package com.example.task_manager.validators;

import ch.qos.logback.core.util.StringUtil;
import com.example.task_manager.exceptions.ValidateException;
import com.example.task_manager.http.inputs.CreateTaskInput;

public class CreateTaskValidate {

    public static void validate(CreateTaskInput createTaskInput) {
        if (StringUtil.isNullOrEmpty(createTaskInput.getTitle())) {
            throw new ValidateException("Title cannot be empty");
        }

        if (createTaskInput.getTitle().length() > 100) {
            throw new ValidateException("Title must be at most 100 characters long");
        }

        if (createTaskInput.getDescription().length() > 10_000) {
            throw new ValidateException("Description must be at most 10_000 characters long");
        }

        if (StringUtil.isNullOrEmpty(createTaskInput.getPersonId())) {
            throw new ValidateException("User cannot be empty");
        }
    }
}
