package tests.UITest;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import psb.testproject.store.log.ConsoleLogger;

public class BaseUITest {
    protected static WebDriver driver;
    ConsoleLogger logger = new ConsoleLogger();

    @BeforeAll
    public static void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void tearDown() throws Exception {
        driver.quit();
    }
}
