package ru.yandex.praktikum.driver;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverInitialization {

    @Step("Создание драйвера")
    public void startBrowser() {
        // Устанавливаем яндекс браузер по умолчанию
        String pathToYandexDriver = "src/main/resources/yandexdriver.exe";
        String pathToChromeDriver = "src/main/resources/chromedriver.exe";
        System.setProperty("webdriver.chrome.driver", pathToYandexDriver);
        //Настройки браузера
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        // options.addArguments("--headless");
        //Запуск кастомного драйвера
        WebDriver driver = new ChromeDriver(options);
        WebDriverRunner.setWebDriver(driver);
    }
}
