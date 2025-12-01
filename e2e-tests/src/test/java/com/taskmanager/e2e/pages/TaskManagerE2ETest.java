package com.taskmanager.e2e.pages;

import com.taskmanager.e2e.config.WebDriverConfig;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskManagerE2ETest {

    private static WebDriver driver;
    private static HomePage homePage;
    private static TaskPage taskPage;

    @BeforeAll
    public static void setUp() {
        driver = WebDriverConfig.createDriver();
        homePage = new HomePage(driver);
        taskPage = new TaskPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("Deve criar um novo usuário")
    public void shouldCreateNewUser() {
        homePage.open();
        homePage.createUser();

        assertTrue(homePage.isUserCreated(), "Usuário deveria ter sido criado");
        assertNotNull(homePage.getUserId(), "UserID deveria estar visível");
    }

    @Test
    @Order(2)
    @DisplayName("Deve criar uma nova tarefa")
    public void shouldCreateNewTask() {
        homePage.clickNewTask();

        taskPage.fillTaskForm("E2E Test Task", "This is an automated test");
        taskPage.clickSave();

        assertTrue(taskPage.isTaskVisible("E2E Test Task"),
                "Tarefa criada deveria estar visível");
    }

    @Test
    @Order(3)
    @DisplayName("Deve criar múltiplas tarefas")
    public void shouldCreateMultipleTasks() {
        int initialCount = taskPage.getTaskCount();

        homePage.clickNewTask();
        taskPage.fillTaskForm("Second Task", "Another test task");
        taskPage.clickSave();

        int finalCount = taskPage.getTaskCount();

        assertEquals(initialCount + 1, finalCount,
                "Deveria ter uma tarefa a mais");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}