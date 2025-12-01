package com.taskmanager.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TaskPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public TaskPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillTaskForm(String title, String description) {
        WebElement titleInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("title")));
        titleInput.clear();
        titleInput.sendKeys(title);

        WebElement descInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("description")));
        descInput.clear();
        descInput.sendKeys(description);
    }

    public void clickSave() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Save changes')]")
        )).click();

        sleep(1000); // Aguarda modal fechar
    }

    public boolean isTaskVisible(String taskTitle) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[contains(text(), '" + taskTitle + "')]")
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}