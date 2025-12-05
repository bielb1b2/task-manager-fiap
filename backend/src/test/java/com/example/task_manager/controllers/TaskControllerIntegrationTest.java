package com.example.task_manager.controllers;

import com.example.task_manager.http.inputs.CreateTaskInput;
import com.example.task_manager.http.inputs.UpdateTaskInput;
import com.example.task_manager.http.out.TaskOut;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("local")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static String personId;
    private static String taskId;

    @BeforeAll
    static void setUpClass() {
        personId = "integration-test-person-" + System.currentTimeMillis();
    }

    @Test
    @Order(1)
    @DisplayName("Deve criar uma nova tarefa com sucesso")
    void shouldCreateNewTask() throws Exception {
        // ARRANGE
        CreateTaskInput input = new CreateTaskInput.Builder()
                .personId(personId)
                .title("Integration Test Task")
                .description("This is an integration test task")
                .build();

        // ACT & ASSERT
        MvcResult result = mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.uuid").exists())
                .andExpect(jsonPath("$.title").value("Integration Test Task"))
                .andExpect(jsonPath("$.description").value("This is an integration test task"))
                .andExpect(jsonPath("$.finished").value(false))
                .andExpect(jsonPath("$.createdAt").exists())
                .andReturn();

        // Salvar taskId para próximos testes
        String responseBody = result.getResponse().getContentAsString();
        TaskOut taskOut = objectMapper.readValue(responseBody, TaskOut.class);
        taskId = taskOut.getUuid();
    }

    @Test
    @Order(2)
    @DisplayName("Deve buscar todas as tarefas de um usuário")
    void shouldGetAllTasksFromPerson() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(get("/api/task/{personId}", personId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].uuid").exists())
                .andExpect(jsonPath("$[0].title").exists())
                .andExpect(jsonPath("$[0].description").exists())
                .andExpect(jsonPath("$[0].finished").exists());
    }

    @Test
    @Order(3)
    @DisplayName("Deve buscar uma tarefa específica")
    void shouldGetSpecificTask() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(get("/api/task/{personId}/{taskId}", personId, taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(taskId))
                .andExpect(jsonPath("$.title").value("Integration Test Task"))
                .andExpect(jsonPath("$.description").value("This is an integration test task"))
                .andExpect(jsonPath("$.finished").value(false));
    }

    @Test
    @Order(4)
    @DisplayName("Deve atualizar uma tarefa")
    void shouldUpdateTask() throws Exception {
        // ARRANGE
        UpdateTaskInput updateInput = UpdateTaskInput.builder()
                .title("Updated Integration Test Task")
                .description("This task was updated during integration test")
                .finished(false)
                .build();

        // ACT & ASSERT
        mockMvc.perform(patch("/api/task/{personId}/{taskId}", personId, taskId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateInput)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(taskId))
                .andExpect(jsonPath("$.title").value("Updated Integration Test Task"))
                .andExpect(jsonPath("$.description").value("This task was updated during integration test"))
                .andExpect(jsonPath("$.finished").value(false));
    }

    @Test
    @Order(5)
    @DisplayName("Deve finalizar uma tarefa")
    void shouldFinishTask() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(post("/api/task/{personId}/{taskId}/finish", personId, taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.uuid").value(taskId))
                .andExpect(jsonPath("$.finished").value(true));
    }

    @Test
    @Order(6)
    @DisplayName("Deve retornar 404 ao buscar tarefa inexistente")
    void shouldReturn404ForNonExistentTask() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(get("/api/task/{personId}/{taskId}", personId, "non-existent-id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Task not found"));
    }

    @Test
    @Order(7)
    @DisplayName("Deve retornar 400 ao criar tarefa sem título")
    void shouldReturn400WhenCreatingTaskWithoutTitle() throws Exception {
        // ARRANGE
        CreateTaskInput input = new CreateTaskInput.Builder()
                .personId(personId)
                .title("")
                .description("Test description")
                .build();

        // ACT & ASSERT
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Title cannot be empty"));
    }

    @Test
    @Order(8)
    @DisplayName("Deve retornar 400 ao criar tarefa com título muito longo")
    void shouldReturn400WhenCreatingTaskWithLongTitle() throws Exception {
        // ARRANGE
        String longTitle = "a".repeat(101);
        CreateTaskInput input = new CreateTaskInput.Builder()
                .personId(personId)
                .title(longTitle)
                .description("Test description")
                .build();

        // ACT & ASSERT
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Title must be at most 100 characters long"));
    }

    @Test
    @Order(9)
    @DisplayName("Deve retornar 400 ao criar tarefa sem personId")
    void shouldReturn400WhenCreatingTaskWithoutPersonId() throws Exception {
        // ARRANGE
        CreateTaskInput input = new CreateTaskInput.Builder()
                .personId("")
                .title("Test Task")
                .description("Test description")
                .build();

        // ACT & ASSERT
        mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("User cannot be empty"));
    }

    @Test
    @Order(10)
    @DisplayName("Deve deletar uma tarefa com sucesso")
    void shouldDeleteTask() throws Exception {
        // ACT & ASSERT - Delete
        mockMvc.perform(delete("/api/task/{personId}/{taskId}", personId, taskId))
                .andExpect(status().isNoContent());

        // Verificar que a tarefa foi deletada
        mockMvc.perform(get("/api/task/{personId}/{taskId}", personId, taskId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(11)
    @DisplayName("Deve retornar 404 ao deletar tarefa inexistente")
    void shouldReturn404WhenDeletingNonExistentTask() throws Exception {
        // ACT & ASSERT
        mockMvc.perform(delete("/api/task/{personId}/{taskId}", personId, "non-existent-id"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Task not found"));
    }

    @Test
    @Order(12)
    @DisplayName("Deve criar múltiplas tarefas e buscar todas")
    void shouldCreateMultipleTasksAndGetAll() throws Exception {
        // ARRANGE - Criar 3 tarefas
        for (int i = 1; i <= 3; i++) {
            CreateTaskInput input = new CreateTaskInput.Builder()
                    .personId(personId)
                    .title("Task " + i)
                    .description("Description " + i)
                    .build();

            mockMvc.perform(post("/api/task")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(input)))
                    .andExpect(status().isCreated());
        }

        // ACT & ASSERT - Buscar todas as tarefas
        mockMvc.perform(get("/api/task/{personId}", personId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(3))));
    }

    @Test
    @Order(13)
    @DisplayName("Deve retornar lista vazia para usuário sem tarefas")
    void shouldReturnEmptyListForUserWithoutTasks() throws Exception {
        // ACT & ASSERT
        String newPersonId = "person-without-tasks-" + System.currentTimeMillis();
        mockMvc.perform(get("/api/task/{personId}", newPersonId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Order(14)
    @DisplayName("Deve validar que tarefa finalizada não muda de estado ao finalizar novamente")
    void shouldNotChangeFinishedTaskWhenFinishingAgain() throws Exception {
        // ARRANGE - Criar e finalizar uma tarefa
        CreateTaskInput input = new CreateTaskInput.Builder()
                .personId(personId)
                .title("Task to finish twice")
                .description("Test description")
                .build();

        MvcResult createResult = mockMvc.perform(post("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andReturn();

        TaskOut createdTask = objectMapper.readValue(
                createResult.getResponse().getContentAsString(),
                TaskOut.class
        );

        // Finalizar pela primeira vez
        mockMvc.perform(post("/api/task/{personId}/{taskId}/finish", personId, createdTask.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finished").value(true));

        // ACT & ASSERT - Finalizar novamente
        mockMvc.perform(post("/api/task/{personId}/{taskId}/finish", personId, createdTask.getUuid()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finished").value(true));
    }
}