package com.taskmanager.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Locators
    private final By createUserButton = By.xpath("//button[contains(text(), 'Create TaskManager')]");
    private final By newTaskButton = By.xpath("//button[contains(., 'New Task')]");
    private final By userIdDisplay = By.xpath("//span[contains(text(), 'UserID:')]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("http://frontend:3000");
    }

    public void createUser() {
        wait.until(ExpectedConditions.elementToBeClickable(createUserButton)).click();
    }

    public boolean isUserCreated() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(userIdDisplay));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void clickNewTask() {
        wait.until(ExpectedConditions.elementToBeClickable(newTaskButton)).click();
    }

    public String getUserId() {
        WebElement userElement = driver.findElement(
                By.xpath("//span[@class='text-zinc-500 underline underline-offset-4 cursor-pointer group-hover:text-zinc-300 transition-colors']")
        );
        return userElement.getText();
    }
}