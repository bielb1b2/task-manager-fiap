package com.example.task_manager.exceptions.core;

public record ErrorResponse(
        String errorMessage,
        String path,
        String timestamp
) { }
