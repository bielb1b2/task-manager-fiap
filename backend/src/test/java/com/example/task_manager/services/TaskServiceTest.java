package com.example.task_manager.services;

import com.example.task_manager.exceptions.NotFoundException;
import com.example.task_manager.exceptions.ValidateException;
import com.example.task_manager.http.inputs.CreateTaskInput;
import com.example.task_manager.http.inputs.UpdateTaskInput;
import com.example.task_manager.http.out.TaskOut;
import com.example.task_manager.services.helpers.InMemoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;


public class TaskServiceTest {

    @Mock
    ITaskMessageService taskMessageService;
    InMemoryRepository taskRepository;
    private ITaskService taskService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskRepository = new InMemoryRepository();
        taskService = new TaskService(taskRepository, taskMessageService);
    }

    @Test
    @DisplayName("Deve criar uma nova tarefa")
    void shouldCreateNewTask() {
        // ARRANGE
        CreateTaskInput createTaskInput = new CreateTaskInput.Builder()
                .personId("person-id")
                .title("new task")
                .description("new description")
                .build();

        // ACT
        TaskOut sut = taskService.createTask(createTaskInput);

        // ASSERT
        Assertions.assertNotNull(sut);
        Assertions.assertEquals("new task", sut.getTitle());
        Assertions.assertEquals("new description", sut.getDescription());
    }

    @Test
    @DisplayName("Deve Retornar todas as tarefas de um unico usuario")
    void shouldCreateReturnAllTasks() {
        // ARRANGE
        String personId = "123456";
        CreateTaskInput createTaskInput1 = new CreateTaskInput.Builder()
                .personId(personId)
                .title("new task1")
                .description("new description")
                .build();
        CreateTaskInput createTaskInput2 = new CreateTaskInput.Builder()
                .personId(personId)
                .title("new task2")
                .description("new description")
                .build();

        taskService.createTask(createTaskInput1);
        taskService.createTask(createTaskInput2);

        // ACT
        List<TaskOut> sut = taskService.getAllTasksFromPersonId(personId);

        // ASSERT
        Assertions.assertNotNull(sut);
        Assertions.assertEquals(2, sut.size());
        Assertions.assertEquals("new task1", sut.getFirst().getTitle());
        Assertions.assertEquals("new task2", sut.get(1).getTitle());
    }

    @Test
    @DisplayName("Deve retornar uma exception quando personId é nulo")
    void shouldThrowAnExceptionWhenPersonIdIsNull() {
        // ARRANGE
        CreateTaskInput createTaskInput = new CreateTaskInput.Builder()
                .personId("personId")
                .title("new task1")
                .description("new description")
                .build();

        taskService.createTask(createTaskInput);

        // ACT && ASSERT
        ValidateException sut = Assertions.assertThrows(ValidateException.class, () -> {
            taskService.getAllTasksFromPersonId(null);
        });

        Assertions.assertEquals("Person ID cannot be null/empty", sut.getMessage());
    }

    @Test
    @DisplayName("Deve retornar apenas uma tarefa do usuário")
    void shouldReturnOneTaskFromTheUser() {
        // ARRANGE
        String personId = "123456";
        CreateTaskInput createTaskInput = new CreateTaskInput.Builder()
                .personId(personId)
                .title("new task")
                .description("new description")
                .build();

        TaskOut taskOut = taskService.createTask(createTaskInput);
        // ACT
        TaskOut sut = taskService.getOneTaskFromThePerson(
                personId,
                taskOut.getUuid()
        );

        // ASSERT
        Assertions.assertNotNull(sut);
        Assertions.assertEquals(taskOut.getUuid(), sut.getUuid());
        Assertions.assertEquals("new task", sut.getTitle());
        Assertions.assertEquals("new description", sut.getDescription());
    }

    @Test
    @DisplayName("Remove uma tarefa criada")
    void shouldRemoveTask() {
        // ARRANGE
        String personId = "123456";
        CreateTaskInput createTaskInput = new CreateTaskInput.Builder()
                .personId(personId)
                .title("new task")
                .description("new description")
                .build();

        TaskOut taskOut = taskService.createTask(createTaskInput);
        // ACT
        taskService.removeOneTaskFromThePerson(
                personId,
                taskOut.getUuid()
        );

        // ASSERT
        Assertions.assertEquals(Collections.emptyList(), taskRepository.tasks);
    }

    @Test
    @DisplayName("Retorna um erro ao tentar remover uma tarefa que não existe")
    void shouldThrowAnExceptionWhenTheTaskNotExist() {
        // ARRANGE
        String personId = "123456";
        CreateTaskInput createTaskInput = new CreateTaskInput.Builder()
                .personId(personId)
                .title("new task")
                .description("new description")
                .build();

        TaskOut taskOut = taskService.createTask(createTaskInput);
        // ACT
        NotFoundException sut = Assertions.assertThrows(NotFoundException.class, () -> {
            taskService.removeOneTaskFromThePerson(
                    createTaskInput.getPersonId(),
                    "1234"
            );
        });

        // ASSERT
        Assertions.assertEquals("Task not found", sut.getMessage());
    }

    @Test
    @DisplayName("Atualiza uma tarefa criada")
    void shouldUpdateTask() {
        // ARRANGE
        String personId = "123456";
        CreateTaskInput createTaskInput = new CreateTaskInput.Builder()
                .personId(personId)
                .title("new task")
                .description("new description")
                .build();

        TaskOut taskOut = taskService.createTask(createTaskInput);
        String taskId = taskOut.getUuid();

        UpdateTaskInput updateTaskInput = UpdateTaskInput.builder()
                .title("updated task")
                .description("updated description")
                .finished(false)
                .build();
        // ACT
        TaskOut sut = taskService.updateTask(
                personId,
                taskId,
                updateTaskInput
        );

        // ASSERT
        Assertions.assertNotNull(sut);
        Assertions.assertEquals(updateTaskInput.getTitle(), sut.getTitle());
        Assertions.assertEquals(updateTaskInput.getDescription(), sut.getDescription());
        Assertions.assertEquals(updateTaskInput.isFinished(), sut.isFinished());
    }

    @Test
    @DisplayName("Finaliza uma tarefa criada")
    void shouldFinishATask() {
        // ARRANGE
        String personId = "123456";
        CreateTaskInput createTaskInput = new CreateTaskInput.Builder()
                .personId(personId)
                .title("new task")
                .description("new description")
                .build();

        TaskOut taskOut = taskService.createTask(createTaskInput);
        // ACT
        TaskOut sut = taskService.finishTask(
                personId,
                taskOut.getUuid()
        );

        // ASSERT
        Assertions.assertNotNull(sut);
        Assertions.assertFalse(taskOut.isFinished());
        Assertions.assertTrue(sut.isFinished());
    }
}
