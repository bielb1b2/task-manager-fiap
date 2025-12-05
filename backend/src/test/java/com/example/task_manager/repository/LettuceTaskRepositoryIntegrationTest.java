package com.example.task_manager.repository;

import com.example.task_manager.entities.Task;
import io.lettuce.core.api.sync.RedisCommands;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LettuceTaskRepositoryIntegrationTest {

    @Autowired
    private ITaskRepository taskRepository;

    @Autowired
    private RedisCommands<String, String> redisCommands;

    private static String testPersonId;

    @BeforeAll
    static void setUpClass() {
        testPersonId = "repo-test-person-" + System.currentTimeMillis();
    }

    @BeforeEach
    void setUp() {
        // Limpar dados de teste antes de cada teste
        redisCommands.flushdb();
    }

    @Test
    @Order(1)
    @DisplayName("Deve salvar uma tarefa no Redis")
    void shouldSaveTaskInRedis() {
        // ARRANGE
        Task task = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(testPersonId)
                .title("Test Task")
                .description("Test Description")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();

        // ACT
        Task savedTask = taskRepository.save(task);

        // ASSERT
        assertNotNull(savedTask);
        assertEquals(task.getUuid(), savedTask.getUuid());
        assertEquals(task.getTitle(), savedTask.getTitle());

        // Verificar que foi salvo no Redis
        String key = "task:" + task.getUuid();
        String storedJson = redisCommands.get(key);
        assertNotNull(storedJson);
        assertTrue(storedJson.contains(task.getTitle()));
    }

    @Test
    @Order(2)
    @DisplayName("Deve buscar uma tarefa por ID")
    void shouldGetTaskById() {
        // ARRANGE
        Task task = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(testPersonId)
                .title("Test Task to Find")
                .description("Test Description")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();
        taskRepository.save(task);

        // ACT
        Optional<Task> foundTask = taskRepository.getTask(testPersonId, task.getUuid());

        // ASSERT
        assertTrue(foundTask.isPresent());
        assertEquals(task.getUuid(), foundTask.get().getUuid());
        assertEquals(task.getTitle(), foundTask.get().getTitle());
        assertEquals(task.getPersonId(), foundTask.get().getPersonId());
    }

    @Test
    @Order(3)
    @DisplayName("Deve retornar Optional.empty() para tarefa inexistente")
    void shouldReturnEmptyForNonExistentTask() {
        // ACT
        Optional<Task> foundTask = taskRepository.getTask(testPersonId, "non-existent-id");

        // ASSERT
        assertTrue(foundTask.isEmpty());
    }

    @Test
    @Order(4)
    @DisplayName("Deve buscar todas as tarefas de uma pessoa")
    void shouldGetAllTasksFromPerson() {
        // ARRANGE
        String personId = "person-with-multiple-tasks";
        Task task1 = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(personId)
                .title("Task 1")
                .description("Description 1")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();

        Task task2 = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(personId)
                .title("Task 2")
                .description("Description 2")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();

        Task task3 = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId("different-person")
                .title("Task 3")
                .description("Description 3")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();

        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        // ACT
        List<Task> tasks = taskRepository.getAllFromPersonId(personId);

        // ASSERT
        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        assertTrue(tasks.stream().allMatch(t -> t.getPersonId().equals(personId)));
    }

    @Test
    @Order(5)
    @DisplayName("Deve retornar lista vazia para pessoa sem tarefas")
    void shouldReturnEmptyListForPersonWithoutTasks() {
        // ACT
        List<Task> tasks = taskRepository.getAllFromPersonId("person-without-tasks");

        // ASSERT
        assertNotNull(tasks);
        assertTrue(tasks.isEmpty());
    }

    @Test
    @Order(6)
    @DisplayName("Deve atualizar uma tarefa existente")
    void shouldUpdateExistingTask() {
        // ARRANGE
        Task originalTask = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(testPersonId)
                .title("Original Title")
                .description("Original Description")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();
        taskRepository.save(originalTask);

        Task updatedTask = new Task.Builder()
                .uuid(originalTask.getUuid())
                .personId(originalTask.getPersonId())
                .title("Updated Title")
                .description("Updated Description")
                .finished(true)
                .createdAt(originalTask.getCreatedAt())
                .build();

        // ACT
        Optional<Task> result = taskRepository.update(updatedTask);

        // ASSERT
        assertTrue(result.isPresent());
        assertEquals("Updated Title", result.get().getTitle());
        assertEquals("Updated Description", result.get().getDescription());
        assertTrue(result.get().isFinished());

        // Verificar no Redis
        Optional<Task> fromRedis = taskRepository.getTask(testPersonId, originalTask.getUuid());
        assertTrue(fromRedis.isPresent());
        assertEquals("Updated Title", fromRedis.get().getTitle());
    }

    @Test
    @Order(7)
    @DisplayName("Deve retornar Optional.empty() ao atualizar tarefa inexistente")
    void shouldReturnEmptyWhenUpdatingNonExistentTask() {
        // ARRANGE
        Task task = new Task.Builder()
                .uuid("non-existent-id")
                .personId(testPersonId)
                .title("Title")
                .description("Description")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();

        // ACT
        Optional<Task> result = taskRepository.update(task);

        // ASSERT
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(8)
    @DisplayName("Deve remover uma tarefa")
    void shouldRemoveTask() {
        // ARRANGE
        Task task = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(testPersonId)
                .title("Task to Remove")
                .description("This will be removed")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();
        taskRepository.save(task);

        // ACT
        Optional<Task> removedTask = taskRepository.removeTask(testPersonId, task.getUuid());

        // ASSERT
        assertTrue(removedTask.isPresent());
        assertEquals(task.getUuid(), removedTask.get().getUuid());

        // Verificar que foi removido
        Optional<Task> shouldBeEmpty = taskRepository.getTask(testPersonId, task.getUuid());
        assertTrue(shouldBeEmpty.isEmpty());

        // Verificar que foi removido do índice
        List<Task> personTasks = taskRepository.getAllFromPersonId(testPersonId);
        assertFalse(personTasks.stream().anyMatch(t -> t.getUuid().equals(task.getUuid())));
    }

    @Test
    @Order(9)
    @DisplayName("Deve retornar Optional.empty() ao remover tarefa inexistente")
    void shouldReturnEmptyWhenRemovingNonExistentTask() {
        // ACT
        Optional<Task> result = taskRepository.removeTask(testPersonId, "non-existent-id");

        // ASSERT
        assertTrue(result.isEmpty());
    }

    @Test
    @Order(10)
    @DisplayName("Deve manter índice de pessoa ao salvar tarefas")
    void shouldMaintainPersonIndexWhenSavingTasks() {
        // ARRANGE
        String personId = "indexed-person";
        Task task1 = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(personId)
                .title("Task 1")
                .description("Description 1")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();

        Task task2 = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(personId)
                .title("Task 2")
                .description("Description 2")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();

        // ACT
        taskRepository.save(task1);
        taskRepository.save(task2);

        // ASSERT - Verificar índice no Redis
        String indexKey = "person:" + personId + ":tasks";
        Long setSize = redisCommands.scard(indexKey);
        assertEquals(2, setSize);

        // Verificar que os UUIDs estão no set
        assertTrue(redisCommands.sismember(indexKey, task1.getUuid()));
        assertTrue(redisCommands.sismember(indexKey, task2.getUuid()));
    }

    @Test
    @Order(11)
    @DisplayName("Deve atualizar índice ao mudar personId")
    void shouldUpdateIndexWhenChangingPersonId() {
        // ARRANGE
        String originalPersonId = "original-person";
        String newPersonId = "new-person";

        Task task = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(originalPersonId)
                .title("Task to Move")
                .description("Description")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();
        taskRepository.save(task);

        Task updatedTask = new Task.Builder()
                .uuid(task.getUuid())
                .personId(newPersonId)
                .title(task.getTitle())
                .description(task.getDescription())
                .finished(task.isFinished())
                .createdAt(task.getCreatedAt())
                .build();

        // ACT
        taskRepository.update(updatedTask);

        // ASSERT
        String originalIndexKey = "person:" + originalPersonId + ":tasks";
        String newIndexKey = "person:" + newPersonId + ":tasks";

        assertFalse(redisCommands.sismember(originalIndexKey, task.getUuid()));
        assertTrue(redisCommands.sismember(newIndexKey, task.getUuid()));
    }

    @Test
    @Order(12)
    @DisplayName("Deve validar propriedade do usuário ao buscar tarefa")
    void shouldValidateUserOwnershipWhenGettingTask() {
        // ARRANGE
        String ownerId = "task-owner";
        String otherUserId = "other-user";

        Task task = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(ownerId)
                .title("Private Task")
                .description("Description")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();
        taskRepository.save(task);

        // ACT
        Optional<Task> ownerResult = taskRepository.getTask(ownerId, task.getUuid());
        Optional<Task> otherUserResult = taskRepository.getTask(otherUserId, task.getUuid());

        // ASSERT
        assertTrue(ownerResult.isPresent());
        assertTrue(otherUserResult.isEmpty());
    }

    @Test
    @Order(13)
    @DisplayName("Deve validar propriedade do usuário ao remover tarefa")
    void shouldValidateUserOwnershipWhenRemovingTask() {
        // ARRANGE
        String ownerId = "task-owner";
        String otherUserId = "other-user";

        Task task = new Task.Builder()
                .uuid(UUID.randomUUID().toString())
                .personId(ownerId)
                .title("Task to Remove")
                .description("Description")
                .finished(false)
                .createdAt(LocalDateTime.now().toString())
                .build();
        taskRepository.save(task);

        // ACT
        Optional<Task> otherUserResult = taskRepository.removeTask(otherUserId, task.getUuid());

        // ASSERT
        assertTrue(otherUserResult.isEmpty());

        // Verificar que a tarefa ainda existe para o dono
        Optional<Task> ownerResult = taskRepository.getTask(ownerId, task.getUuid());
        assertTrue(ownerResult.isPresent());
    }
}