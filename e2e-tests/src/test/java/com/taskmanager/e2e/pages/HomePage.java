package com.taskmanager.e2e.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("http://localhost:3000");
    }

    public void createUser() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Create TaskManager')]")
        )).click();
    }

    public void clickNewTask() {
        sleep(1000); // Aguarda modal anterior fechar
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(., 'New Task')]")
        )).click();
    }

    public boolean isUserCreated() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//span[contains(text(), 'UserID:')]")
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