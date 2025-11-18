package com.example.task_manager.validators;

import ch.qos.logback.core.util.StringUtil;
import com.example.task_manager.exceptions.ValidateException;

public class OneTaskValidate {

    public static void validate(String personId, String taskId) {
        if(StringUtil.isNullOrEmpty(personId)) {
            throw new ValidateException("Person ID cannot be null/empty");
        }

        if(StringUtil.isNullOrEmpty(taskId)) {
            throw new ValidateException("Task ID cannot be null/empty");
        }
    }
}
