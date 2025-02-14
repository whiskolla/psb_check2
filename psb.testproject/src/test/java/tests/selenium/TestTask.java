package tests.selenium;

import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import psb.testproject.store.log.ConsoleLogger;
import tests.UITest.BaseUITest;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestTask extends BaseUITest {
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
        String expectedTitleGoogle = "Google";
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("https://www.google.com");
            Assert.assertEquals(expectedTitleGoogle, driver.getTitle());
            driver.findElement(By.xpath("//*[@id='APjFqb']")).sendKeys("квока животное", Keys.RETURN);
            Assert.assertEquals("квока животное - Поиск в Google", driver.getTitle());

        } catch (TimeoutException e) {
            logger.logError("TimeoutException e");
            e.printStackTrace();
        }
    }

    @Test
    @Description("тест на таймаут, яндекс")
    public void TimeoutTestY1() throws Exception {
        String expectedTitleYandex = "Дзен — платформа для просмотра и создания контента. Вы всегда найдёте здесь то, что подходит именно вам: сотни тысяч авторов ежедневно делятся постами, статьями, видео и короткими роликами";
        String expectedString = "квока животное";
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("https://yandex.ru");
            Assert.assertEquals(expectedTitleYandex, driver.getTitle());

            driver.switchTo().frame(driver.findElement(By.id("ya-search-iframe-283dku")));
            driver.findElement(By.xpath("//input[@name = 'text']")).sendKeys(expectedString);
            driver.findElement(By.xpath("//button[@type = 'submit']")).click();

            WebElement voiceSearchButton = driver.findElement(By.xpath("//button[starts-with(@id, 'fdqaimg-')]/div[1]/header/div[1]/button[1]"));
            Assert.assertTrue(voiceSearchButton.isDisplayed());

        } catch (TimeoutException e) {
            logger.logError("TimeoutException e");
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            System.out.println("Кнопка голосового поиска отсутствует");
        }
    }
}
