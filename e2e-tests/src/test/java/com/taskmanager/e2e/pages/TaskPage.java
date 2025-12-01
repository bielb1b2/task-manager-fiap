package com.taskmanager.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TaskPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By titleInput = By.id("title");
    private final By descriptionInput = By.id("description");
    private final By saveButton = By.xpath("//button[contains(text(), 'Save changes')]");
    private final By taskCards = By.xpath("//span[contains(@class, 'group flex')]");

    public TaskPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillTaskForm(String title, String description) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(titleInput))
                .sendKeys(title);

        driver.findElement(descriptionInput).sendKeys(description);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();

        // Aguarda modal fechar
        wait.until(ExpectedConditions.invisibilityOfElementLocated(saveButton));
    }

    public boolean isTaskVisible(String taskTitle) {
        try {
            By taskLocator = By.xpath("//span[contains(text(), '" + taskTitle + "')]");
            wait.until(ExpectedConditions.visibilityOfElementLocated(taskLocator));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getTaskCount() {
        List<WebElement> tasks = driver.findElements(taskCards);
        return tasks.size();
    }

    public void deleteFirstTask() {
        WebElement firstTask = driver.findElement(taskCards);
        firstTask.findElement(By.xpath(".//button[contains(@class, 'opacity-0')]")).click();
    }
}