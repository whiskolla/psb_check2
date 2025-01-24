package psb.testproject.selenium;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import psb.testproject.store.log.ConsoleLogger;

import java.time.Duration;

public class Task implements AutoCloseable {
    boolean isDisposed = false;
    Duration timeout;
    WebDriver driver;
    ConsoleLogger logger;

    public Task(WebDriver driver) {
        this.driver = driver;
        this.logger = new ConsoleLogger();
    }

    //1
    public void build() {
        driver.get("https://www.google.com");
        System.out.println(driver.getTitle());
    }

    public void build(ChromeOptions chromeOptions) {
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.google.com");
        System.out.println(driver.getTitle());
    }

    //2
    @Override
    public void close() throws Exception {
        if (!isDisposed) {
            driver.quit();
            isDisposed = true;
            logger.logInfo("WebDriver has been disposed");
        } else {
            logger.logInfo("WebDriver has already been disposed");
        }
    }

    //3
    public void doHeadLessBuild() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless");
        build(chromeOptions);
    }

    //4
    public void buildWithTimeoutY(int seconds) {
        String expectedTitleYandex = "Дзен — платформа для просмотра и создания контента. Вы всегда найдёте здесь то, что подходит именно вам: сотни тысяч авторов ежедневно делятся постами, статьями, видео и короткими роликами";
        String expectedString = "квока животное";
        this.timeout = Duration.ofSeconds(seconds);
        try {
            driver.get("https://yandex.ru");
            Assert.assertEquals(expectedTitleYandex, driver.getTitle());

            WebDriverWait wait = new WebDriverWait(driver, timeout);

            driver.switchTo().frame(driver.findElement(By.id("ya-search-iframe-283dku")));
            driver.findElement(By.xpath("//input[@name = 'text']")).sendKeys(expectedString);
            driver.findElement(By.xpath("//button[@type = 'submit']")).click();

            wait.until(ExpectedConditions.urlContains(""));

            //String actual = driver.findElement(By.xpath(""));;
            //Assert.assertEquals(expectedString, actual);
        } catch (TimeoutException e) {
            logger.logError("TimeoutException e");
            e.printStackTrace();
        }
    }

    public void buildWithTimeoutG(int seconds) {
        String expectedTitleGoogle = "Google";
        this.timeout = Duration.ofSeconds(seconds);
        try {
            driver.get("https://www.google.com");
            Assert.assertEquals(expectedTitleGoogle, driver.getTitle());

            WebDriverWait wait = new WebDriverWait(driver, timeout);

            driver.findElement(By.xpath("//*[@id='APjFqb']")).sendKeys("квока животное", Keys.RETURN);
            Boolean searchResults = wait.until(ExpectedConditions.titleIs("квока животное - Поиск в Google"));
            Assert.assertTrue(searchResults);

        } catch (TimeoutException e) {
            logger.logError("TimeoutException e");
            e.printStackTrace();
        }
    }
}

