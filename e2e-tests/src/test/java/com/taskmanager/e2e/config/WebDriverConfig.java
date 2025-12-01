package com.taskmanager.e2e.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class WebDriverConfig {

    private static final String BASE_URL = "http://localhost:3000";

    public static WebDriver createDriver() {
        ChromeOptions options = new ChromeOptions();

        // REMOVA essa linha se quiser VER o browser abrindo
        // options.addArguments("--headless");

        options.addArguments("--start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

        WebDriver driver = new ChromeDriver(options);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

        return driver;
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}