package tests.selenium;

import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import psb.testproject.store.log.ConsoleLogger;
import tests.UITest.BaseUITest;

import java.time.Duration;

public class TestTask extends BaseUITest {
    Duration timeout;
    WebDriver driver;
    ConsoleLogger logger = new ConsoleLogger();

    public TestTask() {
        this.driver = BaseUITest.driver;
    }

    @Test
    public void BuildTest() throws Exception {
        String expectedTitle = "Google";
        driver.get("https://www.google.com");
        Assert.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    public void HeadLessTest() throws Exception {
        String expectedTitle = "Google";
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("headless");
        driver = new ChromeDriver(chromeOptions);
        driver.get("https://www.google.com");
        Assert.assertEquals(expectedTitle, driver.getTitle());
    }

    @Test
    @Description("тест с ожиданием, гугл")
    public void TimeoutTestG1() throws Exception {
        int seconds = 100;
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

/*    @Test
    @Description("тест на таймаут, яндекс")
    public void TimeoutTestY1() throws Exception {
        int seconds = 100;
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
        } catch (TimeoutException e) {
            logger.logError("TimeoutException e");
            e.printStackTrace();
        }
    }*/
}
