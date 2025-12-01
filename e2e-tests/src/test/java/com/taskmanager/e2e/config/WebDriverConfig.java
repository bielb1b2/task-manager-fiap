package com.taskmanager.e2e.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.time.Duration;

public class WebDriverConfig {

    private static final String SELENIUM_HUB_URL =
            System.getProperty("selenium.hub.url", "http://selenium-hub:4444/wd/hub");

    private static final String BASE_URL =
            System.getProperty("base.url", "http://frontend:3000");

    public static WebDriver createDriver() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");

            WebDriver driver = new RemoteWebDriver(
                    new URL(SELENIUM_HUB_URL),
                    options
            );

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

            return driver;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create WebDriver", e);
        }
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }
}