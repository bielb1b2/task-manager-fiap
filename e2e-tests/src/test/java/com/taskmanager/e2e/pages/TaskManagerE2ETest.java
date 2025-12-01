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
        System.out.println("🚀 Iniciando testes E2E...");
        driver = WebDriverConfig.createDriver();
        homePage = new HomePage(driver);
        taskPage = new TaskPage(driver);
    }

    @Test
    @Order(1)
    @DisplayName("✅ Deve criar um novo usuário")
    public void shouldCreateNewUser() {
        System.out.println("\n📝 Teste 1: Criando usuário...");

        homePage.open();
        homePage.createUser();

        assertTrue(homePage.isUserCreated(), "Usuário não foi criado");

        System.out.println("✅ Usuário criado com sucesso!");
    }

    @Test
    @Order(2)
    @DisplayName("✅ Deve criar uma nova tarefa")
    public void shouldCreateNewTask() {
        System.out.println("\n📝 Teste 2: Criando tarefa...");

        homePage.clickNewTask();
        taskPage.fillTaskForm("E2E Test Task", "This is an automated test");
        taskPage.clickSave();

        assertTrue(taskPage.isTaskVisible("E2E Test Task"), "Tarefa não foi criada");

        System.out.println("✅ Tarefa criada com sucesso!");
    }

    @Test
    @Order(3)
    @DisplayName("✅ Deve criar múltiplas tarefas")
    public void shouldCreateMultipleTasks() {
        System.out.println("\n📝 Teste 3: Criando segunda tarefa...");

        homePage.clickNewTask();
        taskPage.fillTaskForm("Second Task", "Another test task");
        taskPage.clickSave();

        assertTrue(taskPage.isTaskVisible("Second Task"), "Segunda tarefa não foi criada");

        System.out.println("✅ Segunda tarefa criada com sucesso!");
    }

    @AfterAll
    public static void tearDown() {
        System.out.println("\n🏁 Testes finalizados!");

        // Aguarda 3 segundos para você ver o resultado
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (driver != null) {
            driver.quit();
        }
    }
}