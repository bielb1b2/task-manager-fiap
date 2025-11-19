package com.example.task_manager.repository;

import com.example.task_manager.entities.Task;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class LettuceTaskRepository implements ITaskRepository {
    private final RedisCommands<String, String> commands;
    private final ObjectMapper objectMapper;

    private static final String TASK_KEY = "task:%s";
    private static final String PERSON_INDEX = "person:%s:tasks";

    public LettuceTaskRepository(RedisCommands<String, String> commands,
                                 ObjectMapper objectMapper) {
        this.commands = commands;
        this.objectMapper = objectMapper;
    }

    @Override
    public Task save(Task task) {
        try {
            String key = keyFor(task.getUuid());
            String json = objectMapper.writeValueAsString(task);
            commands.set(key, json);
            commands.sadd(personIndexFor(task.getPersonId()), task.getUuid());
            return task;
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    @Override
    public List<Task> getAllFromPersonId(String personId) {
        String setKey = personIndexFor(personId);
        Set<String> uuids = commands.smembers(setKey);
        List<Task> result = new ArrayList<>();
        if (uuids == null || uuids.isEmpty()) {
            return result;
        }

        for (String uuid : uuids) {
            String json = commands.get(keyFor(uuid));
            if (json == null) continue;
            try {
                Task t = objectMapper.readValue(json, Task.class);
                result.add(t);
            } catch (JsonProcessingException e) {
                return null;
            }
        }
        return result;
    }

    @Override
    public Optional<Task> getTask(String personId, String taskId) {
        String key = keyFor(taskId);
        String json = commands.get(key);
        if (json == null) return Optional.empty();

        try {
            Task t = objectMapper.readValue(json, Task.class);
            if (personId != null && !personId.equals(t.getPersonId())) {
                return Optional.empty();
            }
            return Optional.of(t);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Task> removeTask(String personId, String taskId) {
        String key = keyFor(taskId);
        String json = commands.get(key);
        if (json == null) return Optional.empty();

        try {
            Task existing = objectMapper.readValue(json, Task.class);

            if (personId != null && !personId.equals(existing.getPersonId())) {
                return Optional.empty();
            }

            Long deleted = commands.del(key);
            commands.srem(personIndexFor(existing.getPersonId()), taskId);

            if (deleted != null && deleted > 0) {
                return Optional.of(existing);
            } else {
                return Optional.empty();
            }
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Task> update(Task task) {
        String key = keyFor(task.getUuid());
        String existingJson = commands.get(key);
        if (existingJson == null) {
            return Optional.empty();
        }

        try {
            Task existing = objectMapper.readValue(existingJson, Task.class);

            String oldPersonId = existing.getPersonId();
            String newPersonId = task.getPersonId();
            if (oldPersonId != null && !oldPersonId.equals(newPersonId)) {
                commands.srem(personIndexFor(oldPersonId), task.getUuid());
                commands.sadd(personIndexFor(newPersonId), task.getUuid());
            }

            String newJson = objectMapper.writeValueAsString(task);
            commands.set(key, newJson);

            return Optional.of(task);
        } catch (JsonProcessingException e) {
            return Optional.empty();
        }
    }

    // helpers
    private static String keyFor(String uuid) {
        return String.format(TASK_KEY, uuid);
    }

    private static String personIndexFor(String personId) {
        return String.format(PERSON_INDEX, personId);
    }
}
