package tests.selenium;

import jdk.jfr.Description;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import psb.testproject.store.log.ConsoleLogger;
import tests.UITest.BaseUITest;

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
/*            WebElement div = driver.findElement(By.className("HeaderDesktop-Actions"));
            WebElement button = div.findElement(By.xpath("//button"));
            Assert.assertTrue(button.isDisplayed());*/
            String name = driver.findElement(By.xpath("//div[@class='EntityHeader-Title EntityHeader-Title_oneLine']")).getText();
            Assert.assertEquals("Квокка", name);

        } catch (TimeoutException e) {
            logger.logError("TimeoutException e");
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            System.out.println("Кнопка голосового поиска отсутствует");
        }
    }

    @Test
    @Description("вспомогательный тест на таймаут, яндекс")
    public void TimeoutTestY() throws Exception {
        try {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
            driver.get("https://yandex.ru/search/?text=%D0%BA%D0%B2%D0%BE%D0%BA%D0%B0+%D0%B6%D0%B8%D0%B2%D0%BE%D1%82%D0%BD%D0%BE%D0%B5&search_source=dzen_desktop_safe&src=suggest_Pers&lr=213");

            String name = driver.findElement(By.xpath("//div[@class='EntityHeader-Title EntityHeader-Title_oneLine']")).getText();
            Assert.assertEquals("Квокка", name);

        } catch (TimeoutException e) {
            logger.logError("TimeoutException e");
            e.printStackTrace();
        } catch (NoSuchElementException e) {
            System.out.println("Кнопка голосового поиска отсутствует");
        }
    }

    @Test
    @Description("тест с ожиданием, гугл")
    public void TimeoutTestG1() throws Exception {
        String expectedTitleGoogle = "Google";
        try {
            driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
            driver.get("https://www.google.com");
            Assert.assertEquals(expectedTitleGoogle, driver.getTitle());
            driver.findElement(By.xpath("//*[@id='APjFqb']")).sendKeys("квока животное", Keys.RETURN);
            Assert.assertEquals("квока животное - Поиск в Google", driver.getTitle());

        } catch (TimeoutException e) {
            logger.logError("TimeoutException e");
            e.printStackTrace();
        }
    }

}
