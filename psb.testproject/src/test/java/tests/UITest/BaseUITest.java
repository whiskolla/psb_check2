package tests.UITest;

import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.chrome.ChromeDriver;
import psb.testproject.selenium.Task;

public class BaseUITest {
    protected static Task task;

    @BeforeAll
    public static void setUp() throws Exception {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        task = new Task(new ChromeDriver());
    }

    @AfterAll
    public static void tearDown() throws Exception {
        task.close();
    }
}
